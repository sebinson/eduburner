package eduburner.crawler.frontier;

import eduburner.crawler.model.CrawlURI;

public class AbstractFrontier implements ICrawlFrontier {

	@Override
	public long failedFetchCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void finished(CrawlURI uri) {
		// TODO Auto-generated method stub

	}

	@Override
	public void initTasks() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CrawlURI next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public long queuedUriCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void schedule(CrawlURI uri) {
		// TODO Auto-generated method stub

	}

	@Override
	public long succeededFetchCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub

	}

}
