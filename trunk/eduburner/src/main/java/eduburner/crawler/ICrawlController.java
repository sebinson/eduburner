package eduburner.crawler;

/**
 * Interface for controlling a crawl job. The implementation is
 * {@link CrawlControllerImpl}, which is an open MBean.
 * 
 * @author pjack
 */
public interface ICrawlController {

	/**
	 * Operator requested crawl begin
	 */
	void requestCrawlStart();

	/**
	 * Request a checkpoint. Sets a checkpointing thread running.
	 * 
	 * @throws IllegalStateException
	 *             Thrown if crawl is not in paused state (Crawl must be first
	 *             paused before checkpointing).
	 */
	void requestCrawlCheckpoint() throws IllegalStateException;

	/**
	 * Operator requested for crawl to stop.
	 */
	void requestCrawlStop();

	/**
	 * Stop the crawl temporarly.
	 */
	void requestCrawlPause();

	/**
	 * Resume crawl from paused state
	 */
	void requestCrawlResume();

	String getCrawlStatusString();

	String getToeThreadReport();

	String getToeThreadReportShort();

	String getFrontierReport();

	String getFrontierReportShort();

	String getProcessorsReport();

	void killThread(int threadNumber, boolean replace);
}
