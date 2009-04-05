package eduburner.crawler;

import javax.annotation.PostConstruct;

public class FetchController implements ICrawlController {
	
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
