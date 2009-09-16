package eduburner.crawler;

import java.util.List;

import eduburner.crawler.model.CrawlURI;

public interface ICrawlURIsLoader {
	
	public List<CrawlURI> loadCrawlURIs();
}
