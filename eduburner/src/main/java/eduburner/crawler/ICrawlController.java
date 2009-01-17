package eduburner.crawler;


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
	
	public ICrawlFrontier getFrontier();
	
	public void releaseContinuePermission();

	/**
	 * Resume crawl from paused state
	 */
	public void requestCrawlResume();
	
	public void acquireContinuePermission();

	public void killThread(int threadNumber, boolean replace);
}
