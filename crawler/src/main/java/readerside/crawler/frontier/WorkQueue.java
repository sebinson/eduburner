package readerside.crawler.frontier;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import readerside.crawler.model.CrawlURI;

/**
 * A single queue of related URIs to visit, grouped by a classKey (typically
 * "hostname:port" or similar)
 * 
 */
public abstract class WorkQueue implements Serializable, Comparable<Delayed>,
		Delayed {
	private static final long serialVersionUID = 2941833658867158238L;
	private static final Logger logger = LoggerFactory
			.getLogger(WorkQueue.class);

	protected String classKey;
	protected long wakeTime;

	/** whether queue is active (ready/in-process/snoozed) or on a waiting queue */
	private boolean active = true;
	/** Total number of stored items */
	private long count = 0;
	/** Total number of items ever enqueued */
	private long enqueueCount = 0;
	/** The next item to be returned */
	protected CrawlURI peekItem = null;
	/** Last URI enqueued */
	private String lastQueued;
	/** Last URI peeked */
	private String lastPeeked;
	/** time of last dequeue (disposition of some URI) **/
	private long lastDequeueTime;
	/** count of errors encountered */
	private long errorCount = 0;
	
	/** Whether queue is already in lifecycle stage */
    private boolean isHeld = false;

	public WorkQueue(final String classKey) {
		this.classKey = classKey;
	}

	/**
	 * Add the given CrawlURI, noting its addition in running count. (It should
	 * not already be present.)
	 * 
	 * @param frontier
	 *            Work queues manager.
	 * @param curi
	 *            CrawlURI to insert.
	 */
	protected void enqueue(final WorkQueueFrontier frontier, CrawlURI curi) {
		try {
			insert(frontier, curi, false);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		count++;
		enqueueCount++;
	}

	public CrawlURI peek(final WorkQueueFrontier frontier) {
		if (peekItem == null && count > 0) {
			try {
				peekItem = peekItem(frontier);
			} catch (IOException e) {
				logger.warn("peek failure", e);
				e.printStackTrace();
			}
			if (peekItem != null) {
				lastPeeked = peekItem.toString();
			}
		}
		return peekItem;
	}
	
	 /**
     * Update the given CrawlURI, which should already be present. (This
     * is not checked.) Equivalent to an enqueue without affecting the count.
     * 
     * @param frontier Work queues manager.
     * @param curi CrawlURI to update.
     */
    public void update(final WorkQueueFrontier frontier, CrawlURI curi) {
        try {
            insert(frontier, curi, true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

	/**
	 * Remove the peekItem from the queue and adjusts the count.
	 * 
	 * @param frontier
	 *            Work queues manager.
	 */
	protected void dequeue(final WorkQueueFrontier frontier, CrawlURI expected) {
		try {
			deleteItem(frontier, peekItem);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		unpeek(expected);
		count--;
		lastDequeueTime = System.currentTimeMillis();
	}

	/**
	 * Forgive the peek, allowing a subsequent peek to return a different item.
	 * 
	 */
	public void unpeek(CrawlURI expected) {
		assert expected == peekItem : "unexpected peekItem";
		peekItem = null;
	}

	public long getDelay(TimeUnit unit) {
		return unit.convert(getWakeTime() - System.currentTimeMillis(),
				TimeUnit.MILLISECONDS);
	}
	
	public void setActive(final WorkQueueFrontier frontier, final boolean b) {
        if(active != b) {
            active = b;
            logger.debug((active ? "queue set active: " : "queue unset active: ") + 
                    this.getClassKey());
            try {
                if(active) {
                    resume(frontier);
                } else {
                    suspend(frontier);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
	}

	public String getClassKey() {
		return classKey;
	}

	public long getWakeTime() {
		return wakeTime;
	}

	public void setWakeTime(long wakeTime) {
		this.wakeTime = wakeTime;
	}

	public long getCount() {
		return count;
	}

	/**
	 * Insert the given curi, whether it is already present or not.
	 * 
	 * @param frontier
	 *            WorkQueueFrontier.
	 * @param curi
	 *            CrawlURI to insert.
	 * @throws IOException
	 */
	private void insert(final WorkQueueFrontier frontier, CrawlURI curi,
			boolean overwriteIfPresent) throws IOException {
		insertItem(frontier, curi, overwriteIfPresent);
		lastQueued = curi.toString();
	}

	/**
	 * Insert the given curi, whether it is already present or not. Hook for
	 * subclasses.
	 * 
	 * @param frontier
	 *            WorkQueueFrontier.
	 * @param curi
	 *            CrawlURI to insert.
	 * @throws IOException
	 *             if there was a problem while inserting the item
	 */
	protected abstract void insertItem(final WorkQueueFrontier frontier,
			CrawlURI curi, boolean overwriteIfPresent) throws IOException;

	/**
	 * Returns first item from queue (does not delete)
	 * 
	 * @return The peeked item, or null
	 * @throws IOException
	 *             if there was a problem while peeking
	 */
	protected abstract CrawlURI peekItem(final WorkQueueFrontier frontier)
			throws IOException;

	/**
	 * Removes the given item from the queue.
	 * 
	 * This is only used to remove the first item in the queue, so it is not
	 * necessary to implement a random-access queue.
	 * 
	 * @param frontier
	 *            Work queues manager.
	 * @throws IOException
	 *             if there was a problem while deleting the item
	 */
	protected abstract void deleteItem(final WorkQueueFrontier frontier,
			final CrawlURI item) throws IOException;
	
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
    
    /**
     * Clear isHeld to false
     */
    public void clearHeld() {
		isHeld = false;
		logger.info("queue unheld: " + getClassKey());
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
		logger.info("queue held: " + getClassKey());
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

}
