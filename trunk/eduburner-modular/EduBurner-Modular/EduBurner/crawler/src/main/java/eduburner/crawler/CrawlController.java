package eduburner.crawler;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Lists;

import eduburner.crawler.enumerations.CrawlStatus;
import eduburner.crawler.event.CrawlStatusListener;
import eduburner.crawler.processor.IProcessor;

/**
 * 
 * @author zhangyf@gmail.com
 * 
 */
public class CrawlController implements ICrawlController {

	public static final int DEFAULT_MAX_TOE_THREAD_SIZE = 10;

	private ExecutorService toeThreadPool;
	private int maxToeThreadSize;

	public static enum State {
		NASCENT, RUNNING, PAUSED, PAUSING, CHECKPOINTING, STOPPING, FINISHED, STARTED, PREPARING
	}

	transient private State state = State.NASCENT;

	private List<CrawlStatusListener> crawlStatusListeners = Lists
			.newArrayList();

	private List<IProcessor> processors = Lists.newArrayList();

	private ICrawlFrontier crawlFrontier;

	public CrawlController() {
		maxToeThreadSize = DEFAULT_MAX_TOE_THREAD_SIZE;
		toeThreadPool = Executors
				.newFixedThreadPool(DEFAULT_MAX_TOE_THREAD_SIZE);
	}

	public List<IProcessor> getProcessors() {
		return processors;
	}

	@Override
	public void acquireContinuePermission() {
		// TODO Auto-generated method stub

	}

	@Override
	public ICrawlFrontier getFrontier() {
		return crawlFrontier;
	}

	@Override
	public void initTasks() {
		// TODO Auto-generated method stub

	}

	@Override
	public void releaseContinuePermission() {
		// TODO Auto-generated method stub

	}

	
	@Override
	public void requestCrawlStart() {
		fireCrawlStateChangeEvent(State.PREPARING, CrawlStatus.PREPARING);
		
		setUpToePool();
		
		fireCrawlStateChangeEvent(State.STARTED, CrawlStatus.PENDING);
		
        state = State.RUNNING;
        
        fireCrawlStateChangeEvent(this.state, CrawlStatus.RUNNING);

	}

	@Override
	public void requestCrawlPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestCrawlResume() {
		if(toeThreadPool.isShutdown()){
			setUpToePool();
		}
		if (state != State.PAUSING && state != State.PAUSED && state != State.CHECKPOINTING) {
            // Can't resume if not been told to pause or if we're in middle of
            // a checkpoint.
            return;
        }
		
		crawlFrontier.resume();
		
		fireCrawlStateChangeEvent(State.RUNNING, CrawlStatus.RUNNING);
	}
	
	@Override
	public void requestCrawlCheckpoint() throws IllegalStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestCrawlStop() {
		synchronized (this) {
			
		}
	}
	
	private void setUpToePool() {
		for (int i = 0; i < maxToeThreadSize; i++) {
			toeThreadPool.execute(new ToeThread(this));
		}
	}

	/**
	 * Send crawl change event to all listeners.
	 * 
	 * @param newState
	 *            State change we're to tell listeners' about.
	 * @param message
	 *            Message on state change.
	 * @see #sendCheckpointEvent(File) for special case event sending telling
	 *      listeners to checkpoint.
	 */
	protected void fireCrawlStateChangeEvent(State newState, CrawlStatus status) {
		this.state = newState;
		for (CrawlStatusListener listener : crawlStatusListeners) {
			switch (newState) {
			case PAUSED:
				listener.crawlPaused(status.getDescription());
				break;
			case RUNNING:
				listener.crawlResuming(status.getDescription());
				break;
			case PAUSING:
				listener.crawlPausing(status.getDescription());
				break;
			case STARTED:
				listener.crawlStarted(status.getDescription());
				break;
			case STOPPING:
				listener.crawlEnding(status.getDescription());
				break;
			case FINISHED:
				listener.crawlEnded(status.getDescription());
				break;
			case PREPARING:
				listener.crawlResuming(status.getDescription());
				break;
			default:
				throw new RuntimeException("Unknown state: " + newState);
			}
		}
	}
	
	//injected by spring
	public void setCrawlStatusListeners(
			List<CrawlStatusListener> crawlStatusListeners) {
		this.crawlStatusListeners = crawlStatusListeners;
	}

	public void setProcessors(List<IProcessor> processors) {
		this.processors = processors;
	}

}
