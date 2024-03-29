package eduburner.crawler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.google.common.collect.Lists;

import eduburner.crawler.enumerations.CrawlStatus;
import eduburner.crawler.event.CrawlStateEvent;
import eduburner.crawler.frontier.Frontier;
import eduburner.crawler.model.CrawlURI;
import eduburner.crawler.processor.IProcessor;

public class DefaultCrawler implements Crawler, ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(DefaultCrawler.class);
	
	public static final int DEFAULT_MAX_TOE_THREAD_SIZE = 10;

	private ExecutorService toeThreadPool;
	private int maxToeThreadSize;

	private ApplicationContext appCtx;
	private List<IProcessor> processors = Lists.newArrayList();
	
	@Autowired
	@Qualifier("frontier")
	private Frontier crawlFrontier;
	
	@Autowired
	@Qualifier("crawlURILoader")
	private InitialCrawlURIsLoader crawlURILoader;

	/**
     * Crawl exit status.
     */
    private transient CrawlStatus sExit = CrawlStatus.CREATED;
    
	public static enum State {
		NASCENT, RUNNING, PAUSED, STOPPING, FINISHED
	}

	transient private State state = State.NASCENT;

	public void init() {
		logger.debug("init crawler");
		maxToeThreadSize = DEFAULT_MAX_TOE_THREAD_SIZE;
		toeThreadPool = Executors
				.newFixedThreadPool(DEFAULT_MAX_TOE_THREAD_SIZE);
	}

	@Override
	public void requestCrawlStart() {
		crawlFrontier.loadCrawlURIs(crawlURILoader);
		setUpToePool();
		state = State.RUNNING;
		crawlFrontier.requestState(Frontier.State.RUN);
		sendCrawlStateChangeEvent(this.state, CrawlStatus.RUNNING);
	}

	@Override
	public void requestCrawlStop() {
		requestCrawlStop(CrawlStatus.ABORTED);
	}
	
	@Override
	public void requestCrawlStop(CrawlStatus message){
		if (state == State.NASCENT) {
            this.state = State.FINISHED;
        }
        if (state == State.STOPPING || state == State.FINISHED ) {
            return;
        }
        if (message == null) {
            throw new IllegalArgumentException("Message cannot be null.");
        }
        beginCrawlStop();
	}
	
	private void beginCrawlStop() {
        logger.debug("Started.");
        sendCrawlStateChangeEvent(State.STOPPING, this.sExit);
        Frontier frontier = getFrontier();
        if (frontier != null) {
            frontier.terminate();
        }
        logger.debug("Finished."); 
    }

	protected void sendCrawlStateChangeEvent(State newState, CrawlStatus status) {
		if (this.state == newState) {
			// suppress duplicate state-reports
			return;
		}
		this.state = newState;
		CrawlStateEvent event = new CrawlStateEvent(this, newState, status
				.getDescription());
		appCtx.publishEvent(event);
	}

	private void setUpToePool() {
		for (int i = 0; i < maxToeThreadSize; i++) {
			toeThreadPool.execute(new CrawlWorker(this));
		}
	}

	@Override
	public Frontier getFrontier() {
		return crawlFrontier;
	}

	public void setProcessors(List<IProcessor> processors) {
		this.processors = processors;
	}
	
	public List<IProcessor> getProcessors() {
		return processors;
	}

	public void setMaxToeThreadSize(int maxToeThreadSize) {
		this.maxToeThreadSize = maxToeThreadSize;
	}
	
	@Override
	public void schedule(CrawlURI uri) {
		crawlFrontier.schedule(uri);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appCtx = applicationContext;
	}

}
