package eduburner.crawler.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import eduburner.crawler.ICrawlController;
import eduburner.crawler.ICrawlFrontier;

@Component("crawlController")
public class CrawlController implements ICrawlController {
	
	private ICrawlFrontier frontier;
	
	@PostConstruct
	public void initTasks(){
		
	}

	@Override
	public void acquireContinuePermission() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ICrawlFrontier getFrontier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void killThread(int threadNumber, boolean replace) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void releaseContinuePermission() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestCrawlCheckpoint() throws IllegalStateException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestCrawlPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestCrawlResume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestCrawlStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestCrawlStop() {
		// TODO Auto-generated method stub
		
	}

}