package readerside.crawler.frontier;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import readerside.crawler.model.CrawlURI;

import com.google.common.collect.Iterables;

public class MemoryWorkQueue extends WorkQueue{

	private static final long serialVersionUID = 7710296270822836264L;
	
	private Queue<CrawlURI> uris;

	public MemoryWorkQueue(String classKey) {
		super(classKey);
		uris = new LinkedBlockingQueue<CrawlURI>();
	}

	@Override
	protected void deleteItem(WorkQueueFrontier frontier, CrawlURI item)
			throws IOException {
		uris.remove(item);
	}

	@Override
	protected void insertItem(WorkQueueFrontier frontier, CrawlURI curi,
			boolean overwriteIfPresent) throws IOException {
		if(!uris.contains(curi)){
			uris.offer(curi);
		}
	}

	@Override
	protected CrawlURI peekItem(WorkQueueFrontier frontier) throws IOException {
		return uris.peek();
	}

}
