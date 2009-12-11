package readerside.crawler.processor;

import readerside.crawler.ProcessResult;
import readerside.crawler.model.CrawlURI;

public interface IProcessor {
	public ProcessResult process(CrawlURI curi);
}
