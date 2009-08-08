package eduburner.crawler.frontier;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import eduburner.crawler.ICrawlController;
import eduburner.crawler.frontier.IFrontier.State;
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
    transient Thread managerThread;
	/** last Frontier.State reached; used to suppress duplicate notifications */
    State lastReachedState = null;
    /** Frontier.state that manager thread should seek to reach */
    State targetState = State.PAUSE;
    
    protected ICrawlController controller;

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
