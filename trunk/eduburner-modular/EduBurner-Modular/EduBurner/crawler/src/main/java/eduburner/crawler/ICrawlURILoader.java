package eduburner.crawler;

import java.util.List;

import eduburner.crawler.model.CrawlURI;

public interface ICrawlURILoader {
	
	public List<CrawlURI> loadCrawlURIs();
}
