package eduburner.test;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.support.GenericXmlContextLoader;

import eduburner.crawler.ICrawler;

public class CrawlTest {

	public static void main(String... args) throws Exception{
		ContextLoader loader = new GenericXmlContextLoader();
		ApplicationContext ctx = loader.loadContext(getConfigLocations());
		
		ICrawler crawler = (ICrawler)ctx.getBean("crawler");
		
		crawler.requestCrawlStart();
		
		Thread.currentThread().join();
	}
	
	private static String[] getConfigLocations(){
		return new String[]{
			"classpath:applicationContext-crawler.xml"	
		};
	}
}
