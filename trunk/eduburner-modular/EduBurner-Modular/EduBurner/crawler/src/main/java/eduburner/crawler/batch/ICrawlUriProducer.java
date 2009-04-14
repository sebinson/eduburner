package eduburner.crawler.batch;

import eduburner.crawler.model.CrawlUri;

public interface ICrawlUriProducer {

	public CrawlUri next();
	
	public boolean isEmpty();
}
