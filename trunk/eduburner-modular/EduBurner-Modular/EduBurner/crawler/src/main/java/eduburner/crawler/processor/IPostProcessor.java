package eduburner.crawler.processor;

import eduburner.crawler.ProcessResult;
import eduburner.crawler.model.CrawlURI;

public interface IPostProcessor {
	public ProcessResult process(CrawlURI curi);
}
