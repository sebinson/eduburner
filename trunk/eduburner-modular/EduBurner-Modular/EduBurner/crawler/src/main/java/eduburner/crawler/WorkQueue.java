package eduburner.crawler;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eduburner.crawler.model.CrawlUri;

/**
 * A single queue of related URIs to visit, grouped by a classKey (typically
 * "hostname:port" or similar)
 * 
 */
public class WorkQueue {

	private static final Logger logger = LoggerFactory
			.getLogger(WorkQueue.class);

	private String classKey;
	private long nextCrawlTime;
	private CrawlUri nextUri;
	private long wakeTime = 0L;

	private Queue<CrawlUri> uriQueue;

	public WorkQueue(String classKey) {
		this.classKey = classKey;
		this.uriQueue = new LinkedBlockingQueue<CrawlUri>();
		this.nextCrawlTime = System.currentTimeMillis();
	}
	
	public long getWakeTime() {
		return wakeTime;
	}
	public void setWakeTime(long wakeTime) {
		this.wakeTime = wakeTime;
	}

	public String getClassKey() {
		return classKey;
	}
}
