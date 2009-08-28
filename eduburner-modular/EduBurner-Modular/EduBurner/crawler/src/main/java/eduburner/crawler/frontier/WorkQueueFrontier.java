package eduburner.crawler.frontier;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Multiset;

import eduburner.crawler.model.CrawlURI;

/**
 * A common Frontier base using several queues to hold pending URIs.
 * 
 * Uses in-memory map of all known 'queues' inside a single database.
 * Round-robins between all queues.
 */
@Component("frontier")
public class WorkQueueFrontier extends AbstractFrontier {

	private static final long serialVersionUID = 5723257498212526250L;

	private static final Logger logger = LoggerFactory
			.getLogger(WorkQueueFrontier.class);

	private Map<String, WorkQueue> allQueues;
	
	private BlockingQueue<String> readyClassKeyQueues;
	private DelayQueue<DelayedWorkQueue> snoozeQueues;
	protected Multiset<WorkQueue> inProcessQueues = HashMultiset.create();

	public WorkQueueFrontier() {
		super();
	}

	@Override
	public void initTasks() {
		super.initTasks();
	}

	protected void initInternalQueues(boolean recycle) {
		allQueues = new MapMaker().makeMap();

		readyClassKeyQueues = new LinkedBlockingDeque<String>();
		snoozeQueues = new DelayQueue<DelayedWorkQueue>();
	}

	protected WorkQueue getQueueForClassKey(String classKey) {
		return allQueues.get(classKey);
	}

	/**
	 * finisehd, add work queue current uri belongs to snooze queue
	 * 
	 * @param uri
	 */
	protected void processFinish(CrawlURI uri) {
		long now = System.currentTimeMillis();
		uri.clearUp();
		long delay = uri.getMinCrawlInterval();
		WorkQueue wq = allQueues.get(uri.getClassKey());
		if (wq != null) {
			addToSnoozeQueue(wq, now, delay);
		} else {
			logger.warn("failed to get workqueue for url: " + uri.getUrl());
		}
	}

	private void addToSnoozeQueue(WorkQueue wq, long now, long delay) {
		long nextTime = now + delay;
		wq.setWakeTime(nextTime);
		snoozeQueues.add(new DelayedWorkQueue(wq));
	}

	private void addToReadyQueue(WorkQueue queue) {
		queue.setActive(this, true);
		try {
			readyClassKeyQueues.put(queue.getClassKey());
		} catch (InterruptedException e) {
			logger.warn("failed to add queue " + queue.getClassKey() + " to ready queue");
			e.printStackTrace();
		}
	}

	 /**
     * Return the next CrawlURI eligible to be processed (and presumably
     * visited/fetched) by a a worker thread.
     *
     * Relies on the readyClassQueues having been loaded with
     * any work queues that are eligible to provide a URI. 
     *
     * @return next CrawlURI eligible to be processed, or null if none available
     *
     */
	@Override
	protected CrawlURI findEligibleURI() {
		assert Thread.currentThread() == managerThread;
		wakeQueues();
		WorkQueue readyQ = null;
		do{
			String key = readyClassKeyQueues.poll();
			if(key == null){
				break;
			}
			readyQ = allQueues.get(key);
			if(readyQ == null){
				break;
			}
			if(readyQ.getCount() == 0){
				readyQ.clearHeld();
				readyQ = null;
			}
		}while(readyQ == null);
		if(readyQ == null){
			return null;
		}
		assert !inProcessQueues.contains(readyQ) : "double activation";
		CrawlURI curi = null;
        curi = readyQ.peek(this);   
		if (curi == null) {
			logger.warn("No CrawlURI from ready non-empty queue "
					+ readyQ.classKey + "\n");
			return null;
		}
           
        inProcessQueues.add(readyQ);
        readyQ.dequeue(this, curi);
        return curi;
	}
	
	/**
     * Wake any queues sitting in the snoozed queue whose time has come.
     */
    protected void wakeQueues() {
        DelayedWorkQueue waked; 
        while((waked = snoozeQueues.poll())!=null) {
            WorkQueue queue = waked.getWorkQueue();
            queue.setWakeTime(0);
            queue.setActive(this, true);
            addToReadyQueue(queue);
        }
    }

	@Override
	protected int getInProcessCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void processScheduleAlways(CrawlURI caUri) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void processScheduleIfUnique(CrawlURI caUri) {
		// TODO Auto-generated method stub

	}

	@Override
	protected long getMaxInWait() {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	private class DelayedWorkQueue implements Delayed, Serializable {

		private static final long serialVersionUID = -6415390806576526923L;
		/**
		 * When a snooze target for a queue is longer than this amount, the
		 * queue will be "long snoozed" instead of "short snoozed". A
		 * "long snoozed" queue may be swapped to disk because it's not needed
		 * soon.
		 */
		private static final long SNOOZE_LONG_MS = 180L * 60L * 1000L;
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
				WorkQueue result = getQueueForClassKey(classKey);
				setWorkQueue(result);
				return result;
			}
			if (workQueue instanceof SoftReference) {
				SoftReference<WorkQueue> ref = (SoftReference) workQueue;
				WorkQueue result = ref.get();
				if (result == null) {
					result = getQueueForClassKey(classKey);
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
