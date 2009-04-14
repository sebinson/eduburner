package eduburner.crawler.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import eduburner.crawler.model.CrawlUri;

public class CrawlUriItemReader implements ItemReader<CrawlUri> {
	
	private ICrawlUriProducer queue;

	@Override
	public CrawlUri read() throws Exception, UnexpectedInputException,
			ParseException {
		if(!queue.isEmpty()){
			return queue.next();
		}
		return null;
	}

}
