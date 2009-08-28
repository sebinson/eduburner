package eduburner.crawler;

import java.util.List;

import eduburner.crawler.frontier.IFrontier;
import eduburner.crawler.processor.IProcessor;

public interface ICrawler {
	public void initTasks();

	/**
	 * Operator requested crawl begin
	 */
	public void requestCrawlStart();

	/**
	 * Operator requested for crawl to stop.
	 */
	public void requestCrawlStop();

	/**
	 * Stop the crawl temporarly.
	 */
	public void requestCrawlPause();

	public void releaseContinuePermission();

	public void acquireContinuePermission();

	public List<IProcessor> getProcessors();

	public IFrontier getFrontier();

}
