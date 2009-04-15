package eduburner.crawler;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.collections.Bag;
import org.apache.commons.collections.BagUtils;
import org.apache.commons.collections.bag.HashBag;

import eduburner.crawler.model.CrawlUri;

/**
 * A common Frontier base using several queues to hold pending URIs.
 * 
 * Uses in-memory map of all known 'queues' inside a single database.
 * Round-robins between all queues.
 */
public class WorkQueueFrontier implements ICrawlFrontier, Serializable {

	private static final long serialVersionUID = 5723257498212526250L;

	@Override
	public long failedFetchCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void finished(CrawlUri uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadUris(List<CrawlUri> uris) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CrawlUri next() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long queuedUriCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void schedule(CrawlUri uri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long succeededFetchCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
