package eduburner.crawler.frontier;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import eduburner.crawler.ICrawlController;
import eduburner.crawler.model.CrawlURI;

public abstract class AbstractFrontier implements IFrontier, Serializable {

	private static final long serialVersionUID = -8091508743170194678L;
	
	protected AtomicLong queuedUriCount = new AtomicLong(0);
	protected AtomicLong succeededFetchCount = new AtomicLong(0);
	protected AtomicLong failedFetchCount = new AtomicLong(0);
	
	/**
     * Distinguished frontier manager thread which handles all juggling
     * of URI queues and queues/maps of queues for proper ordering/delay of
     * URI processing. 
     */
    transient protected Thread managerThread;
	/** last Frontier.State reached; used to suppress duplicate notifications */
    State lastReachedState = null;
    /** Frontier.state that manager thread should seek to reach */
    State targetState = State.PAUSE;
    
    /** inbound updates: URIs to be scheduled, finished; requested state changes */
    transient protected ArrayBlockingQueue<InEvent> inbound;
    /** outbound URIs */ 
    transient protected ArrayBlockingQueue<CrawlURI> outbound;
    /** Capacity of the inbound queue. */
    private int inboundCapacity;
    /** Capacity of the outbound queue. */
    private int outboundCapacity;
    /** 
     * lock to allow holding all worker ToeThreads from taking URIs already
     * on the outbound queue; they acquire read permission before take()ing;
     * frontier can acquire write permission to hold threads */
    protected ReentrantReadWriteLock outboundLock = 
        new ReentrantReadWriteLock(true);
    
    protected ICrawlController controller;
    
    @Override
	public void initTasks() {
    	startManagerThread();
	}
    
    protected void startManagerThread() {
		managerThread = new Thread(){
			public void run(){
				AbstractFrontier.this.managementTasks();
			}
		};
		Executors.newSingleThreadExecutor().execute(managerThread);
	}
    
    /**
	 * Wake any queues sitting in the snoozed queue whose time has come.
	 */
	protected void managementTasks() {
		assert Thread.currentThread() == managerThread;
		
		while(true){
			switch(targetState){
			case RUN:
				reachedState(State.RUN);
				if(isEmpty()){
					targetState = State.PAUSE;
				}
				break;
			case PAUSE:
				break;
			case FINISH:
				break;
			}
		}
		/*DelayedWorkQueue waked = null;
		while(true){
			waked = snoozeQueue.poll();
			if(waked != null){
				WorkQueue queue = waked.getWorkQueue();
				queue.setWakeTime(0L);
				addToReadyQueue(queue);
			}
		}
		logger.info("ending frontier manager thread.");*/
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEmpty() {
		return queuedUriCount.get() == 0L;
	}

	@Override
	public CrawlURI next() {
		return null;
	}

	
	 /**
     * Fill the outbound queue with eligible CrawlURIs, to capacity
     * or as much as possible. 
     * 
     * @throws InterruptedException
     */
    protected void fillOutbound() throws InterruptedException {
        while (outbound.remainingCapacity() > 0) {
            CrawlURI crawlable = findEligibleURI();
            if (crawlable != null) {
                outbound.put(crawlable);
            } else {
                break;
            }
        }
    }
    
    /**
     * Arrange for the given CrawlURI to be visited, if it is not
     * already scheduled/completed.
     *
     * @see org.archive.crawler.framework.Frontier#schedule(org.archive.crawler.datamodel.CrawlURI)
     */
    public void schedule(CrawlURI caUri) {
        enqueueOrDo(new ScheduleIfUnique(caUri));
    }

    /**
     * Accept the given CrawlURI for scheduling, as it has
     * passed the alreadyIncluded filter. 
     * 
     * Choose a per-classKey queue and enqueue it. If this
     * item has made an unready queue ready, place that 
     * queue on the readyClassQueues queue. 
     * @param caUri CrawlURI.
     */
    public void receive(CrawlURI caUri) {
        // prefer doing asap if already in manager thread
        doOrEnqueue(new ScheduleAlways(caUri));
    }
    
    /**
     * Note that the previously emitted CrawlURI has completed
     * its processing (for now).
     *
     * The CrawlURI may be scheduled to retry, if appropriate,
     * and other related URIs may become eligible for release
     * via the next next() call, as a result of finished().
     *
     *  (non-Javadoc)
     * @see org.archive.crawler.framework.Frontier#finished(org.archive.crawler.datamodel.CrawlURI)
     */
    public void finished(CrawlURI curi) {
        enqueueOrDo(new Finish(curi));
    }
    
    /**
     * Arrange for the given InEvent to be done by the managerThread, via
     * enqueueing with other events if possible, but directly if not possible
     * and this is the managerThread. 
     * @param ev InEvent to be done
     */
    protected void enqueueOrDo(InEvent ev) {
        if(!inbound.offer(ev)) {
            // if can't defer, 
            if(Thread.currentThread()==managerThread) {
                // if can't enqueue, ok to just do
				ev.process();
                return; 
            } else {
                try {
                    inbound.put(ev);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    
    /**
     * Arrange for the given InEvent to be done by the managerThread, 
     * immediately if this is the managerThread, of via enqueueing with
     * other inbound events otherwise.  
     * @param ev InEvent to be done
     */
    protected void doOrEnqueue(InEvent ev) {
        if (Thread.currentThread() == managerThread) {
            // if can't enqueue, ok to just do
            ev.process();
            return;
        } else {
            try {
                inbound.put(ev);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    /**
     * Drain the inbound queue of update events, or at the very least
     * wait until some additional delayed-queue URI becomes available. 
     * 
     * @throws InterruptedException
     */
    protected void drainInbound() throws InterruptedException {
        int batch = inbound.size();
        for(int i = 0; i < batch; i++) {
            inbound.take().process();
        }
        if(batch==0) {
            // always do at least one timed try
            InEvent toProcess = inbound.poll(getMaxInWait(),
                    TimeUnit.MILLISECONDS);
            if (toProcess != null) {
                toProcess.process();
            }
        }
    }
    
    public void requestState(State target) {
        enqueueOrDo(new SetTargetState(target));
    }
	
	protected void reachedState(State justReached) {
        if(justReached != lastReachedState) {
            controller.noteFrontierState(justReached);
            lastReachedState = justReached;
        }
    }

	/**
     * Actually set a new target Frontier.State. Should only be called in
     * managerThread, as by an InEvent. 
     */
    protected void processSetTargetState(State target) {
        assert Thread.currentThread() == managerThread;
        targetState = target;
    }
    
	/**
     * Find a CrawlURI eligible to be put on the outbound queue for 
     * processing. If none, return null. 
     * @return the eligible URI, or null
     */
    abstract protected CrawlURI findEligibleURI();
    
    
    /**
     * Schedule the given CrawlURI regardless of its already-seen status. Only
     * to be called inside the managerThread, as by an InEvent. 
     * 
     * @param caUri CrawlURI to schedule
     */
    abstract protected void processScheduleAlways(CrawlURI caUri);
    
    /**
     * Schedule the given CrawlURI if not already-seen. Only
     * to be called inside the managerThread, as by an InEvent. 
     * 
     * @param caUri CrawlURI to schedule
     */
    abstract protected void processScheduleIfUnique(CrawlURI caUri);
    
    /**
     * Handle the given CrawlURI as having finished a worker ToeThread 
     * processing attempt. May result in the URI being rescheduled or
     * logged as successful or failed. Only to be called inside the 
     * managerThread, as by an InEvent. 
     * 
     * @param caUri CrawlURI to finish
     */
    abstract protected void processFinish(CrawlURI caUri);
    
    /**
     * The number of CrawlURIs 'in process' (passed to the outbound
     * queue and not yet finished by returning through the inbound
     * queue.)
     * 
     * @return number of in-process CrawlURIs
     */
    abstract protected int getInProcessCount();
    
    /**
     * Maximum amount of time to wait for an inbound update event before 
     * giving up and rechecking on the ability to further fill the outbound
     * queue. If any queues are waiting out politeness/retry delays ('snoozed'),
     * the maximum wait should be no longer than the shortest sch delay. 
     * @return maximum time to wait, in milliseconds
     */
    abstract protected long getMaxInWait();
    
	 /**
     * An event/update for the managerThread to process from the inbound queue.
     */
    public abstract class InEvent {
        abstract public void process();
    }
    
    /**
     * A CrawlURI to be scheduled by the managerThread without regard to 
     * whether the CrawlURI was already-seen. 
     */
    public class ScheduleAlways extends InEvent {
        CrawlURI caUri;
        public ScheduleAlways(CrawlURI c) {
            this.caUri = c;
        }
        public void process() {
            processScheduleAlways(caUri);
        } 
    }
    
    /**
     * A CrawlURI to be scheduled by the managerThread if it has not been
     * already-seen. (That is, if it passes the UriUniqFilter.)
     */
    public class ScheduleIfUnique extends InEvent {
        CrawlURI caUri;
        public ScheduleIfUnique(CrawlURI c) {
            this.caUri = c;
        }
        public void process() {
            processScheduleIfUnique(caUri);
        }   
    }
    
    /**
     * A CrawlURI, previously issued via the outbound queue,  that has finished 
     * its processing chain with update implications for the frontier state.
     */
    public class Finish extends InEvent {
        CrawlURI caUri;
        public Finish(CrawlURI c) {
            this.caUri = c;
        }
        public void process() {
            processFinish(caUri);
        }   
    }
    
    /**
     * An request that the frontier enter a new Frontier.State. 
     */
    public class SetTargetState extends InEvent {
        State target;
        public SetTargetState(State target) {
            this.target = target;
        }
        @Override
        public void process() {
            processSetTargetState(target);
            // TODO: perhaps null reachedState, because until new state is 
            // reached it's misleading?
        }
    }
}
