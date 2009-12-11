package readerside.crawler.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CrawlStateEventListener implements ApplicationListener<CrawlStateEvent>{

	@Override
	public void onApplicationEvent(CrawlStateEvent event) {
		
	}

}
