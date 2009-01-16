package eduburner.crawler.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import eduburner.crawler.CrawlUri;
import eduburner.crawler.ICrawlController;
import eduburner.crawler.IFrontier;

public class Frontier implements IFrontier, Serializable{

	private static final long serialVersionUID = 5723257498212526250L;
	private ICrawlController crawlController;
	
	 /** 
     * lock to allow holding all worker ToeThreads from taking URIs already
     * on the outbound queue; they acquire read permission before take()ing;
     * frontier can acquire write permission to hold threads */
    protected ReentrantReadWriteLock outboundLock = 
        new ReentrantReadWriteLock(true);

	@Override
	public long discoveredUriCount() {
		// TODO Auto-generated method stub
		return 0;
	}

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
	public void importUris(String params) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
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