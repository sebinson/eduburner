package eduburner.crawler;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import eduburner.crawler.model.CrawlUri;

/**
 * A common Frontier base using several queues to hold pending URIs.
 * 
 * Uses in-memory map of all known 'queues' inside a single database.
 * Round-robins between all queues.
 */

@Component("crawlFrontier")
public class WorkQueueFrontier implements ICrawlFrontier, Serializable {

	private static final long serialVersionUID = 5723257498212526250L;

	private Map<String, WorkQueue> workQueueMap = new ConcurrentHashMap<String, WorkQueue>();
	private BlockingQueue<String> readyQueue;
	private Queue<String> inProgressQueue = new ConcurrentLinkedQueue<String>();
	private DelayQueue<DelayedWorkQueue> snoozeQueue;
	
	protected void startManagerThread(){
		
	}

	@Override
	public void loadUris(List<CrawlUri> uris) {
		// TODO Auto-generated method stub

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

	protected WorkQueue getQueueFor(String classKey) {
		return null;
	}

	class DelayedWorkQueue implements Delayed, Serializable {

		private static final long serialVersionUID = -6415390806576526923L;
		private static final long SNOOZE_LONG_MS = 5L * 60L * 1000L;
		public String classKey;
		public long wakeTime;

		/**
		 * Something can become a WorkQueue instance. This can be three things:
		 * 
		 * <ol>
		 * <li>null, if this DelayedWorkQueue instance was recently
		 * deserialized;
		 * <li>A SoftReference&lt;WorkQueue&gt;, if the WorkQueue's waitTime
		 * exceeded SNOOZE_LONG_MS
		 * <li>A hard WorkQueue reference, if the WorkQueue's waitTime did not
		 * exceed SNOOZE_LONG_MS. Idea here is that we thought we needed the
		 * WorkQueue soon and didn't want to risk losing the instance.
		 * </ol>
		 * 
		 * The {@link #getWorkQueue()} method figures out what to return in all
		 * three of the above cases.
		 */
		private transient Object workQueue;

		public DelayedWorkQueue(WorkQueue queue) {
			this.classKey = queue.getClassKey();
			this.wakeTime = queue.getWakeTime();

			this.workQueue = queue;
		}

		private void setWorkQueue(WorkQueue queue) {
			long wakeTime = queue.getWakeTime();
			long delay = wakeTime - System.currentTimeMillis();
			if (delay > SNOOZE_LONG_MS) {
				this.workQueue = new SoftReference<WorkQueue>(queue);
			} else {
				this.workQueue = queue;
			}
		}

		public WorkQueue getWorkQueue() {
			if (workQueue == null) {
				// This is a recently deserialized DelayedWorkQueue instance
				WorkQueue result = getQueueFor(classKey);
				setWorkQueue(result);
				return result;
			}
			if (workQueue instanceof SoftReference) {
				@SuppressWarnings("unchecked")
				SoftReference<WorkQueue> ref = (SoftReference) workQueue;
				WorkQueue result = ref.get();
				if (result == null) {
					result = getQueueFor(classKey);
				}
				setWorkQueue(result);
				return result;
			}
			return (WorkQueue) workQueue;
		}

		public long getDelay(TimeUnit unit) {
			return unit.convert(wakeTime - System.currentTimeMillis(),
					TimeUnit.MILLISECONDS);
		}

		public String getClassKey() {
			return classKey;
		}

		public long getWakeTime() {
			return wakeTime;
		}

		public void setWakeTime(long time) {
			this.wakeTime = time;
		}

		public int compareTo(Delayed obj) {
			if (this == obj) {
				return 0; // for exact identity only
			}
			DelayedWorkQueue other = (DelayedWorkQueue) obj;
			if (wakeTime > other.getWakeTime()) {
				return 1;
			}
			if (wakeTime < other.getWakeTime()) {
				return -1;
			}
			// at this point, the ordering is arbitrary, but still
			// must be consistent/stable over time
			return this.classKey.compareTo(other.getClassKey());
		}
	}
}
