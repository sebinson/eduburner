package eduburner.crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import eduburner.crawler.model.CrawlUri;

/**
 * A single queue of related URIs to visit, grouped by a classKey
 * (typically "hostname:port" or similar) 
 *
 */
public abstract class WorkQueue {
	/** The classKey */
    protected final String classKey;

    /** whether queue is active (ready/in-process/snoozed) or on a waiting queue */
    private boolean active = true;

    /** Total number of stored items */
    private long count = 0;

    /** Total number of items ever enqueued */
    private long enqueueCount = 0;
    
    /** Whether queue is already in lifecycle stage */
    private boolean isHeld = false;

    /** Time to wake, if snoozed */
    private long wakeTime = 0;
    
    /** set of by-precedence inactive-queues on which WorkQueue is waiting */
    private Set<Integer> onInactiveQueues = new HashSet<Integer>();
    
    /** Running 'budget' indicating whether queue should stay active */
    private int sessionBalance = 0;

    /** Cost of the last item to be charged against queue */
    private int lastCost = 0;

    /** Total number of items charged against queue; with totalExpenditure
     * can be used to calculate 'average cost'. */
    private long costCount = 0;

    /** Running tally of total expenditures on this queue */
    private long totalExpenditure = 0;

    /** Total to spend on this queue over its lifetime */
    private long totalBudget = 0;

    /** The next item to be returned */
    protected CrawlUri peekItem = null;

    /** Last URI enqueued */
    private String lastQueued;

    /** Last URI peeked */
    private String lastPeeked;

    /** time of last dequeue (disposition of some URI) **/ 
    private long lastDequeueTime;
    
    /** count of errors encountered */
    private long errorCount = 0;

    private boolean retired;
    
    public WorkQueue(final String pClassKey) {
        this.classKey = pClassKey;
    }
    
    /**
     * Delete URIs matching the given pattern from this queue. 
     * @param frontier
     * @param match
     * @return count of deleted URIs
     */
    public long deleteMatching(final WorkQueueFrontier frontier, String match) {
        try {
            final long deleteCount = deleteMatchingFromQueue(frontier, match);
            this.count -= deleteCount;
            return deleteCount;
        } catch (IOException e) {
            // FIXME better exception handling
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Add the given CrawlURI, noting its addition in running count. (It
     * should not already be present.)
     * 
     * @param frontier Work queues manager.
     * @param curi CrawlURI to insert.
     */
    protected void enqueue(final WorkQueueFrontier frontier,
    		CrawlUri curi) {
        try {
            insert(frontier, curi, false);
        } catch (IOException e) {
            //FIXME better exception handling
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        count++;
        enqueueCount++;
    }

    /**
     * Return the topmost queue item -- and remember it,
     * such that even later higher-priority inserts don't
     * change it. 
     * 
     * TODO: evaluate if this is really necessary
     * @param frontier Work queues manager
     * 
     * @return topmost queue item, or null
     */
    public CrawlUri peek(final WorkQueueFrontier frontier) {
        if(peekItem == null && count > 0) {
            try {
                peekItem = peekItem(frontier);
            } catch (IOException e) {
                
                e.printStackTrace();
                // throw new RuntimeException(e);
            }
            if(peekItem != null) {
                lastPeeked = peekItem.toString();
            }
        }
        return peekItem;
    }

    /**
     * Remove the peekItem from the queue and adjusts the count.
     * 
     * @param frontier  Work queues manager.
     */
    protected void dequeue(final WorkQueueFrontier frontier, CrawlUri expected) {
        try {
            deleteItem(frontier, peekItem);
        } catch (IOException e) {
            //FIXME better exception handling
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        unpeek(expected);
        count--;
        lastDequeueTime = System.currentTimeMillis();
    }

    /**
     * Set the session 'activity budget balance' to the given value
     * 
     * @param balance to use
     */
    public void setSessionBalance(int balance) {
        this.sessionBalance = balance;
    }

    /**
     * Return current session 'activity budget balance' 
     * 
     * @return session balance
     */
    public int getSessionBalance() {
        return this.sessionBalance;
    }

    /**
     * Set the total expenditure level allowable before queue is 
     * considered inherently 'over-budget'. 
     * 
     * @param budget
     */
    public void setTotalBudget(long budget) {
        this.totalBudget = budget;
    }

    /**
     * Check whether queue has temporarily or permanently exceeded
     * its budget. 
     * 
     * @return true if queue is over its set budget(s)
     */
    public boolean isOverBudget() {
        // check whether running balance is depleted 
        // or totalExpenditure exceeds totalBudget
        return this.sessionBalance <= 0
            || (this.totalBudget >= 0 && this.totalExpenditure >= this.totalBudget);
    }

    /**
     * Return the tally of all expenditures on this queue
     * 
     * @return total amount expended on this queue
     */
    public long getTotalExpenditure() {
        return totalExpenditure;
    }

    /**
     * Increase the internal running budget to be used before 
     * deactivating the queue
     * 
     * @param amount amount to increment
     * @return updated budget value
     */
    public int incrementSessionBalance(int amount) {
        this.sessionBalance = this.sessionBalance + amount;
        return this.sessionBalance;
    }

    /**
     * Decrease the internal running budget by the given amount. 
     * @param amount tp decrement
     * @return updated budget value
     */
    public int expend(int amount) {
        this.sessionBalance = this.sessionBalance - amount;
        this.totalExpenditure = this.totalExpenditure + amount;
        this.lastCost = amount;
        this.costCount++;
        return this.sessionBalance;
    }

    /**
     * A URI should not have been charged against queue (eg
     * it was disregarded); return the amount expended 
     * @param amount to return
     * @return updated budget value
     */
    public int refund(int amount) {
        this.sessionBalance = this.sessionBalance + amount;
        this.totalExpenditure = this.totalExpenditure - amount;
        this.costCount--;
        return this.sessionBalance;
    }
    
    /**
     * Note an error and assess an extra penalty. 
     * @param penalty additional amount to deduct
     */
    public void noteError(int penalty) {
        this.sessionBalance = this.sessionBalance - penalty;
        this.totalExpenditure = this.totalExpenditure + penalty;
        errorCount++;
    }
    
    /**
     * @param l
     */
    public void setWakeTime(long l) {
        wakeTime = l;
    }

    /**
     * @return wakeTime
     */
    public long getWakeTime() {
        return wakeTime;
    }

    /**
     * @return classKey, the 'identifier', for this queue.
     */
    public String getClassKey() {
        return this.classKey;
    }

    /**
     * Clear isHeld to false
     */
    public void clearHeld() {
        isHeld = false;
    }

    /**
     * Whether the queue is already in a lifecycle stage --
     * such as ready, in-progress, snoozed -- and thus should
     * not be redundantly inserted to readyClassQueues
     * 
     * @return isHeld
     */
    public boolean isHeld() {
        return isHeld;
    }

    /**
     * Set isHeld to true
     */
    public void setHeld() {
        isHeld = true;
    }

    /**
     * Forgive the peek, allowing a subsequent peek to 
     * return a different item. 
     * 
     */
    public void unpeek(CrawlUri expected) {
        assert expected == peekItem : "unexpected peekItem";
        peekItem = null;
    }

    /* (non-Javadoc)
     * @see java.util.concurrent.Delayed#getDelay(java.util.concurrent.TimeUnit)
     */
    public long getDelay(TimeUnit unit) {
        return unit.convert(
                getWakeTime()-System.currentTimeMillis(),
                TimeUnit.MILLISECONDS);
    }

    public final int compareTo(Delayed obj) {
        if(this == obj) {
            return 0; // for exact identity only
        }
        WorkQueue other = (WorkQueue) obj;
        if(getWakeTime() > other.getWakeTime()) {
            return 1;
        }
        if(getWakeTime() < other.getWakeTime()) {
            return -1;
        }
        // at this point, the ordering is arbitrary, but still
        // must be consistent/stable over time
        return this.classKey.compareTo(other.getClassKey());
    }

    /**
     * Update the given CrawlURI, which should already be present. (This
     * is not checked.) Equivalent to an enqueue without affecting the count.
     * 
     * @param frontier Work queues manager.
     * @param curi CrawlURI to update.
     */
    public void update(final WorkQueueFrontier frontier, CrawlUri curi) {
        try {
            insert(frontier, curi, true);
        } catch (IOException e) {
            //FIXME better exception handling
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * Count of URIs in this queue. Only precise if called within frontier's
     * manager thread. 
     * 
     * @return Returns the count.
     */
    public long getCount() {
        return this.count;
    }

    /**
     * Insert the given curi, whether it is already present or not. 
     * @param frontier WorkQueueFrontier.
     * @param curi CrawlURI to insert.
     * @throws IOException
     */
    private void insert(final WorkQueueFrontier frontier, CrawlUri curi,
            boolean overwriteIfPresent)
        throws IOException {
        insertItem(frontier, curi, overwriteIfPresent);
        lastQueued = curi.toString();
    }

    /**
     * Insert the given curi, whether it is already present or not.
     * Hook for subclasses. 
     * 
     * @param frontier WorkQueueFrontier.
     * @param curi CrawlURI to insert.
     * @throws IOException  if there was a problem while inserting the item
     */
    protected abstract void insertItem(final WorkQueueFrontier frontier,
        CrawlUri curi, boolean overwriteIfPresent) throws IOException;

    /**
     * Delete URIs matching the given pattern from this queue. 
     * @param frontier WorkQueues manager.
     * @param match  the pattern to match
     * @return count of deleted URIs
     * @throws IOException  if there was a problem while deleting
     */
    protected abstract long deleteMatchingFromQueue(
        final WorkQueueFrontier frontier, final String match)
        throws IOException;

    /**
     * Removes the given item from the queue.
     * 
     * This is only used to remove the first item in the queue,
     * so it is not necessary to implement a random-access queue.
     * 
     * @param frontier  Work queues manager.
     * @throws IOException  if there was a problem while deleting the item
     */
    protected abstract void deleteItem(final WorkQueueFrontier frontier,
        final CrawlUri item) throws IOException;

    /**
     * Returns first item from queue (does not delete)
     * 
     * @return The peeked item, or null
     * @throws IOException  if there was a problem while peeking
     */
    protected abstract CrawlUri peekItem(final WorkQueueFrontier frontier)
        throws IOException;

    /**
     * Suspends this WorkQueue. Closes all connections to resources etc.
     * 
     * @param frontier
     * @throws IOException
     */
    protected void suspend(final WorkQueueFrontier frontier) throws IOException {
    }

    /**
     * Resumes this WorkQueue. Eventually opens connections to resources etc.
     * 
     * @param frontier
     * @throws IOException
     */
    protected void resume(final WorkQueueFrontier frontier) throws IOException {
    }

    public void setActive(final WorkQueueFrontier frontier, final boolean b) {
        if(active != b) {
            active = b;
            try {
                if(active) {
                    resume(frontier);
                } else {
                    suspend(frontier);
                }
            } catch (IOException e) {
                //FIXME better exception handling
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Set the retired status of this queue.
     * 
     * @param b new value for retired status
     */
    public void setRetired(boolean b) {
        this.retired = b;
    }
    
    public boolean isRetired() {
        return retired;
    }

     /*
     * @return the onInactiveQueues
     */
    public Set<Integer> getOnInactiveQueues() {
        return onInactiveQueues;
    }

    public boolean isActive() {
        return active;
    }
}
