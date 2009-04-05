package eduburner.crawler;

import eduburner.crawler.model.CrawlUri;

public interface IProcessor {
	
    public ProcessResult process(CrawlUri uri);
}
