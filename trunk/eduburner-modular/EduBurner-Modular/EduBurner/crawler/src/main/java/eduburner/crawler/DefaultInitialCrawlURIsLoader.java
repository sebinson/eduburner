package eduburner.crawler;

import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.testng.v6.Lists;

import eduburner.crawler.model.CrawlURI;

@Component("crawlURILoader")
public class DefaultInitialCrawlURIsLoader implements InitialCrawlURIsLoader {

	@Override
	public List<CrawlURI> loadCrawlURIs() {
		List<CrawlURI> uris = Lists.newArrayList();
		
		for(int i=0; i<10; i++){
			CrawlURI uri = new CrawlURI("http://" 
					+ RandomStringUtils.random(5, new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g'}) 
					+ ".com/");
			uris.add(uri);
		}
		
		return uris;
	}

}
