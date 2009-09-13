package eduburner.crawler.frontier;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.common.collect.Iterables;

import eduburner.crawler.model.CrawlURI;

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

	//XXX
	@Override
	protected void insertItem(WorkQueueFrontier frontier, CrawlURI curi,
			boolean overwriteIfPresent) throws IOException {
		boolean present = Iterables.contains(uris, curi);
		
		if(!present){
			uris.add(curi);
		}
	}

	@Override
	protected CrawlURI peekItem(WorkQueueFrontier frontier) throws IOException {
		return uris.peek();
	}

}
