package readerside.crawler;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import readerside.crawler.enumerations.CrawlStatus;
import readerside.crawler.event.CrawlStateEvent;
import readerside.crawler.event.UrlScheduleEvent;
import readerside.crawler.frontier.Frontier;
import readerside.crawler.model.CrawlURI;
import readerside.crawler.processor.IProcessor;
import readerside.crawler.service.ICrawlerService;
import readerside.model.GrUser;

import com.google.common.collect.Lists;

public class DefaultCrawler implements Crawler, ApplicationContextAware, ApplicationListener<UrlScheduleEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCrawler.class);
    public static final int DEFAULT_MAX_TOE_THREAD_SIZE = 10;

    private ExecutorService toeThreadPool;

    private int maxWorkerThreadSize = DEFAULT_MAX_TOE_THREAD_SIZE;

    private ApplicationContext appCtx;
    private List<IProcessor> processors = Lists.newArrayList();
    private List<String> seedUserIds = Lists.newArrayList();
    private Set<String> fetchedUserIds = Sets.newHashSet();
    private List<String> notPermittedIds = Lists.newArrayList();

    @Autowired
    @Qualifier("frontier")
    private Frontier crawlFrontier;
    @Autowired
    @Qualifier("crawlerService")
    private ICrawlerService crawlerService;

    private transient CrawlStatus sExit = CrawlStatus.CREATED;


    public static enum State {
        NASCENT, RUNNING, PAUSED, STOPPING, FINISHED
    }

    transient private State state = State.NASCENT;

    public void init() {
        logger.debug("init crawler");
        toeThreadPool = Executors
                .newFixedThreadPool(maxWorkerThreadSize);
    }

    @Override
    public void start() {
        logger.info("crawler start.");
        loadSeedUris();
        setUpToePool();
        state = State.RUNNING;
        crawlFrontier.requestState(Frontier.State.RUN);
        sendCrawlStateChangeEvent(this.state, CrawlStatus.RUNNING);
    }

    /**
     * 加载初始的url
     */
    private void loadSeedUris() {
    	List<GrUser> grUsers = crawlerService.getAllCnUsers();
    	for(GrUser u : grUsers){
    		CrawlURI curi = new CrawlURI(u.getGrUserId());
    		if(u!=null && u.getLastFetchTime() == null){
    			crawlFrontier.schedule(curi);
    		}
    	}
        for (String userId : seedUserIds) {
            CrawlURI curi = new CrawlURI(userId);
            crawlFrontier.schedule(curi);
        }
    }

    @Override
    public void stop() {
        stop(CrawlStatus.ABORTED);
    }

    @Override
    public void stop(CrawlStatus message) {
        if (state == State.NASCENT) {
            this.state = State.FINISHED;
        }
        if (state == State.STOPPING || state == State.FINISHED) {
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

    @Override
    public void schedule(CrawlURI uri) {
        crawlFrontier.schedule(uri);
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
        for (int i = 0; i < maxWorkerThreadSize; i++) {
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

    @Override
    public List<IProcessor> getProcessors() {
        return processors;
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        appCtx.publishEvent(event);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        appCtx = applicationContext;
    }

    public void setMaxWorkerThreadSize(int maxWorkerThreadSize) {
        this.maxWorkerThreadSize = maxWorkerThreadSize;
    }

    public void setSeedUserIds(List<String> seedUserIds) {
        this.seedUserIds = seedUserIds;
    }

    public void setNotPermittedIds(List<String> notPermittedIds) {
        this.notPermittedIds = notPermittedIds;
    }

    @Override
    public void onApplicationEvent(UrlScheduleEvent event) {

        Set<String> fUserIds = event.getFetchedUserIds();
        Set<String> nUserIds = event.getNewUserIds();

        if(fetchedUserIds.size() < 100000){
            Set<String> s = Sets.newHashSet();
            fetchedUserIds = Sets.union(fetchedUserIds, fUserIds).copyInto(s);
            nUserIds = Sets.difference(nUserIds, fetchedUserIds);

            for(String nUserId: nUserIds){
                logger.debug("schedule id: " + nUserId);
                if(!notPermittedIds.contains(nUserId)){
                    schedule(new CrawlURI(nUserId));
                }
            }
        }
    }

}