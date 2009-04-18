package eduburner.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eduburner.crawler.model.CrawlUri;

public class ToeThread implements Runnable{

	private static final Logger logger = LoggerFactory
			.getLogger(ToeThread.class);

	private static final String STEP_NASCENT = "NASCENT";
	private static final String STEP_ABOUT_TO_GET_URI = "ABOUT_TO_GET_URI";
	private static final String STEP_FINISHED = "FINISHED";
	private static final String STEP_ABOUT_TO_BEGIN_PROCESSOR = "ABOUT_TO_BEGIN_PROCESSOR";
	private static final String STEP_DONE_WITH_PROCESSORS = "DONE_WITH_PROCESSORS";
	private static final String STEP_HANDLING_RUNTIME_EXCEPTION = "HANDLING_RUNTIME_EXCEPTION";
	private static final String STEP_ABOUT_TO_RETURN_URI = "ABOUT_TO_RETURN_URI";
	private static final String STEP_FINISHING_PROCESS = "FINISHING_PROCESS";
	
	// activity monitoring, debugging, and problem detection
    private String step = STEP_NASCENT;
    
    private CrawlUri currentCrawUri;
    private long atStepSince;
    private long lastStartTime;
    private long lastFinishTime;
    
    private ICrawlController crawlController;
    
    // indicator that a thread is now surplus based on current desired
    // count; it should wrap up cleanly
    private volatile boolean shouldRetire = false;

	@Override
	public void run() {
		logger.debug("toe thread started.");

		try {
			while (true) {
				continueCheck();
				
				setStep(STEP_ABOUT_TO_GET_URI);
				
				CrawlUri cUri = crawlController.getFrontier().next();
				
				synchronized(this) {
                    continueCheck();
                    setCurrentCuri(cUri);
                }
				
				processCrawlUri();
                
                setStep(STEP_ABOUT_TO_RETURN_URI);
                continueCheck();

                synchronized(this) {
                    crawlController.getFrontier().finished(currentCrawUri);
                    setCurrentCuri(null);
                }
                
                setStep(STEP_FINISHING_PROCESS);
                lastFinishTime = System.currentTimeMillis();
                crawlController.releaseContinuePermission();
                
                if(shouldRetire) {
                    break; // from while(true)
                }
			}
		} catch (InterruptedException e) {
			logger.warn("toe thread ended with interrupted exception");
		}
	}

	/**
	 * Perform checks as to whether normal execution should proceed.
	 * 
	 * If an external interrupt is detected, throw an interrupted exception.
	 * Used before anything that should not be attempted by a 'zombie' thread
	 * that the Frontier/Crawl has given up on.
	 * 
	 * Otherwise, if the controller's memoryGate has been closed, hold until it
	 * is opened. (Provides a better chance of being able to complete some tasks
	 * after an OutOfMemoryError.)
	 * 
	 * @throws InterruptedException
	 */
	private void continueCheck() throws InterruptedException {
		if (Thread.interrupted()) {
			throw new InterruptedException("die request detected");
		}
		crawlController.acquireContinuePermission();
	}
	
	/**
     * Set currentCuri, updating thread name as appropriate
     * @param curi
     */
    private void setCurrentCuri(CrawlUri curi) {
        currentCrawUri = curi;
    }
    
    private void processCrawlUri() throws InterruptedException {
    	lastStartTime = System.currentTimeMillis();
    	
    	
    }
	
	/**
     * @param s
     */
    private void setStep(String s) {
        step=s;
        atStepSince = System.currentTimeMillis();
    }

	public boolean isShouldRetire() {
		return shouldRetire;
	}

	public void setShouldRetire(boolean shouldRetire) {
		this.shouldRetire = shouldRetire;
	}

}
