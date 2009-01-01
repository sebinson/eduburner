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
	public void requestCrawlStart();

	/**
	 * Request a checkpoint. Sets a checkpointing thread running.
	 * 
	 * @throws IllegalStateException
	 *             Thrown if crawl is not in paused state (Crawl must be first
	 *             paused before checkpointing).
	 */
	public void requestCrawlCheckpoint() throws IllegalStateException;

	/**
	 * Operator requested for crawl to stop.
	 */
	public void requestCrawlStop();

	/**
	 * Stop the crawl temporarly.
	 */
	public void requestCrawlPause();
	
	public IFrontier getFrontier();
	
	public void releaseContinuePermission();

	/**
	 * Resume crawl from paused state
	 */
	public void requestCrawlResume();

	public String getCrawlStatusString();

	public String getToeThreadReport();

	public String getToeThreadReportShort();

	public String getFrontierReport();

	public String getFrontierReportShort();

	public String getProcessorsReport();
	
	public void acquireContinuePermission();

	public void killThread(int threadNumber, boolean replace);
}
