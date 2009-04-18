package eduburner.crawler;

import java.io.Serializable;
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
public class WorkQueue implements Serializable {

	private static final long serialVersionUID = 2941833658867158238L;

	private static final Logger logger = LoggerFactory
			.getLogger(WorkQueue.class);

	private String classKey;
	private long wakeTime;

	private Queue<CrawlUri> uriQueue;

	public WorkQueue(String classKey) {
		this.classKey = classKey;
		this.uriQueue = new LinkedBlockingQueue<CrawlUri>();
		this.wakeTime = System.currentTimeMillis();
	}

	public void addUri(CrawlUri uri) {
		if(this.classKey.equals(uri.getClassKey())){
			logger.debug("add uri to work queue");
			uriQueue.add(uri);
		}else{
			logger.debug("uri do not belong to this queue.");
		}
	}

	public CrawlUri nextUri() {
		return uriQueue.poll();
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
