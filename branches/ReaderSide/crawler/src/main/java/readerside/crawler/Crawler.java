package readerside.crawler;

import java.util.List;

import org.springframework.context.ApplicationEvent;
import readerside.crawler.enumerations.CrawlStatus;
import readerside.crawler.frontier.Frontier;
import readerside.crawler.model.CrawlURI;
import readerside.crawler.processor.IProcessor;

public interface Crawler {

	public void start();

	public void stop();
	
	public void stop(CrawlStatus message);

	public Frontier getFrontier();

	public void schedule(CrawlURI uri);

	public List<IProcessor> getProcessors();

    public void publishEvent(ApplicationEvent event);
}
