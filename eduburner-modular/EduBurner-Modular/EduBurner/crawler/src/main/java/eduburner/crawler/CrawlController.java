package eduburner.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CrawlController implements ICrawlController {

	public static final int DEFAULT_MAX_TOE_THREAD_SIZE = 10;

	private ExecutorService toeThreadPool;
	private int maxToeThreadSize;

	public CrawlController() {
		maxToeThreadSize = DEFAULT_MAX_TOE_THREAD_SIZE;
		toeThreadPool = Executors
				.newFixedThreadPool(DEFAULT_MAX_TOE_THREAD_SIZE);
		init();
	}

	private void init() {
		for (int i = 0; i < maxToeThreadSize; i++) {
			toeThreadPool.execute(new ToeThread(this));
		}
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
	public void initTasks() {
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
