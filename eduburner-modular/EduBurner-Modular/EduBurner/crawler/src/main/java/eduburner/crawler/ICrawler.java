package eduburner.crawler;

import java.util.List;

import eduburner.crawler.enumerations.CrawlStatus;
import eduburner.crawler.frontier.IFrontier;
import eduburner.crawler.model.CrawlURI;
import eduburner.crawler.processor.IProcessor;

public interface ICrawler {

	/**
	 * Operator requested crawl begin
	 */
	public void requestCrawlStart();

	/**
	 * Operator requested for crawl to stop.
	 */
	public void requestCrawlStop();

	public List<IProcessor> getProcessors();

	public IFrontier getFrontier();

	public void requestCrawlStop(CrawlStatus message);
	
	public void schedule(CrawlURI uri);

}
