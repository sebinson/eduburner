package eduburner.crawler;

import java.util.Iterator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eduburner.crawler.enumerations.FetchStatusCodes;
import eduburner.crawler.model.CrawlURI;
import eduburner.crawler.processor.IPostProcessor;
import eduburner.crawler.processor.IProcessor;

public class ToeThread extends Thread {

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

	private long atStepSince;
	private long lastStartTime;
	private long lastFinishTime;

	private CrawlURI currentCrawlUri;
	private ICrawler crawlController;

	// indicator that a thread is now surplus based on current desired
	// count; it should wrap up cleanly
	private volatile boolean shouldRetire = false;

	public ToeThread(ICrawler crawlController) {
		this.crawlController = crawlController;
		lastFinishTime = System.currentTimeMillis();
	}

	@Override
	public void run() {
		logger.debug("toe thread started.");

		try {
			while (true) {
				continueCheck();

				setStep(STEP_ABOUT_TO_GET_URI);

				CrawlURI cUri = crawlController.getFrontier().next();

				synchronized (this) {
					continueCheck();
					setCurrentCrawlUri(cUri);
				}

				processCrawlUri();

				setStep(STEP_ABOUT_TO_RETURN_URI);
				continueCheck();

				synchronized (this) {
					crawlController.getFrontier().finished(currentCrawlUri);
					setCurrentCrawlUri(null);
				}

				setStep(STEP_FINISHING_PROCESS);
				lastFinishTime = System.currentTimeMillis();

				if (shouldRetire) {
					break; // from while(true)
				}
			}
		} catch (InterruptedException e) {
			logger.warn("toe thread ended with interrupted exception");
		} catch (Exception e) {
			logger.warn("Fatal exception in : " + getName());
		}

		logger.debug("toe thread: " + getName() + " finished finished. ");
		setCurrentCrawlUri(null);
		setStep(STEP_FINISHED);
	}

	private void processCrawlUri() throws InterruptedException {
		lastStartTime = System.currentTimeMillis();
		List<IProcessor> processors = crawlController.getProcessors();
		Iterator<IProcessor> iterator = processors.iterator();
		IProcessor curProc = iterator.hasNext() ? iterator.next() : null;
		try {
			while (curProc != null) {
				setStep(STEP_ABOUT_TO_BEGIN_PROCESSOR);
				continueCheck();
				ProcessResult result = curProc.process(currentCrawlUri);
				switch (result.getProcessStatus()) {
				case PROCEED:
					curProc = iterator.hasNext() ? iterator.next() : null;
					break;
				case FINISH:
					curProc = advanceToPostProcessing(iterator);
					break;
				}
			}
		} catch (RuntimeException ex) {
			recoverableProblem(ex);
		}
		setStep(STEP_DONE_WITH_PROCESSORS);
	}

	private IProcessor advanceToPostProcessing(Iterator<IProcessor> iter) {
		while (iter.hasNext()) {
			IProcessor me = iter.next();
			if (me instanceof IPostProcessor) {
				return me;
			}
		}
		return null;
	}

	private void continueCheck() throws InterruptedException {
		if (Thread.interrupted()) {
			throw new InterruptedException("die request detected");
		}
	}

	/**
	 * Set currentCuri, updating thread name as appropriate
	 * 
	 * @param curi
	 */
	private void setCurrentCrawlUri(CrawlURI curi) {
		currentCrawlUri = curi;
	}

	private void recoverableProblem(Throwable e) {
		Object previousStep = step;
		setStep(STEP_HANDLING_RUNTIME_EXCEPTION);
		e.printStackTrace(System.err);
		currentCrawlUri.setFetchStatus(FetchStatusCodes.S_RUNTIME_EXCEPTION);
		// store exception temporarily for logging
		String message = "Problem " + e + " occured when trying to process '"
				+ currentCrawlUri.toString() + "' at step " + previousStep
				+ "\n";
		logger.error(message.toString(), e);
	}

	private void setStep(String s) {
		step = s;
		atStepSince = System.currentTimeMillis();
	}

	public boolean isShouldRetire() {
		return shouldRetire;
	}

	public void setShouldRetire(boolean shouldRetire) {
		this.shouldRetire = shouldRetire;
	}

	public void retire() {
		shouldRetire = true;
	}
}
