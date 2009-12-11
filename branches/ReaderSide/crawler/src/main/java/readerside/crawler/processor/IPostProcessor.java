package readerside.crawler.processor;

import readerside.crawler.ProcessResult;
import readerside.crawler.model.CrawlURI;

public interface IPostProcessor {
	public ProcessResult process(CrawlURI curi);
}
