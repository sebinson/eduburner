package eduburner.crawler.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import eduburner.crawler.ICrawlController;
import eduburner.crawler.IFeedFetcher;

@Component("crawlController")
public class CrawlController implements ICrawlController {
	
	private IFeedFetcher frontier;
	
	@PostConstruct
	public void initTasks(){
		
	}

	@Override
	public String getCrawlStatusString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFrontierReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFrontierReportShort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProcessorsReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToeThreadReport() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToeThreadReportShort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void killThread(int threadNumber, boolean replace) {
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

	@Override
	public void acquireContinuePermission() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IFeedFetcher getFrontier() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void releaseContinuePermission() {
		// TODO Auto-generated method stub
		
	}

}
