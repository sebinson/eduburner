package eduburner.crawler.impl;

import java.io.Serializable;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import eduburner.crawler.ICrawlController;
import eduburner.crawler.IFrontier;

public abstract class Frontier implements IFrontier, Serializable{

	private static final long serialVersionUID = 5723257498212526250L;
	private ICrawlController crawlController;
	
	 /** 
     * lock to allow holding all worker ToeThreads from taking URIs already
     * on the outbound queue; they acquire read permission before take()ing;
     * frontier can acquire write permission to hold threads */
    protected ReentrantReadWriteLock outboundLock = 
        new ReentrantReadWriteLock(true);
    
    
}
