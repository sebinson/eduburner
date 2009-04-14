package eduburner.crawler.batch;

import org.slf4j.LoggerFactory;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import eduburner.crawler.model.CrawlUri;

@Component("crawUriQueue")
public class CrawlUriProducer implements ICrawlUriProducer {

	private static final Logger logger = LoggerFactory.getLogger(CrawlUriProducer.class);
	
	private long crawlInterval;
	
	/**
	 * 每隔一段时间产生一个新的crawUri, 时间不到就等待，使用future pattern
	 */
	@Override
	public CrawlUri next() {
		logger.debug("get next uri");
		return new CrawlUri();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

}
