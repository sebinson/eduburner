package eduburner.crawler.frontier;

import java.io.IOException;
import java.util.concurrent.Delayed;

import eduburner.crawler.model.CrawlURI;

public class MemoryWorkQueue extends WorkQueue{

	private static final long serialVersionUID = 7710296270822836264L;

	public MemoryWorkQueue(String classKey) {
		super(classKey);
	}

	@Override
	protected void deleteItem(WorkQueueFrontier frontier, CrawlURI item)
			throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void insertItem(WorkQueueFrontier frontier, CrawlURI curi,
			boolean overwriteIfPresent) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected CrawlURI peekItem(WorkQueueFrontier frontier) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Delayed o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
