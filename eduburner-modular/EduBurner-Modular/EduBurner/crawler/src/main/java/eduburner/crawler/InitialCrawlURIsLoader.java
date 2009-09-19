package eduburner.crawler;

import java.util.List;

import eduburner.crawler.model.CrawlURI;

public interface InitialCrawlURIsLoader {
	
	public List<CrawlURI> loadCrawlURIs();
}
