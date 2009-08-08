package eduburner.crawler.frontier;

import java.io.Serializable;
import java.lang.ref.SoftReference;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.MapMaker;

import eduburner.crawler.ICrawlController;
import eduburner.crawler.model.CrawlURI;

/**
 * A common Frontier base using several queues to hold pending URIs.
 * 
 * Uses in-memory map of all known 'queues' inside a single database.
 * Round-robins between all queues.
 */
public class WorkQueueFrontier extends AbstractFrontier {

	private static final long serialVersionUID = 5723257498212526250L;

	private static final Logger logger = LoggerFactory
			.getLogger(WorkQueueFrontier.class);

	

	private Map<String, WorkQueue> workQueueMap;
	private BlockingQueue<WorkQueue> readyQueue;
	private DelayQueue<DelayedWorkQueue> snoozeQueue;
	
	private ICrawlController controller;

	public WorkQueueFrontier() {
		workQueueMap = new MapMaker().makeMap();
		readyQueue = new LinkedBlockingDeque<WorkQueue>();
		snoozeQueue = new DelayQueue<DelayedWorkQueue>();
	}

	@Override
	public void initTasks() {
	}

	protected void startManagerThread() {
		managerThread = new Thread(){
			public void run(){
				WorkQueueFrontier.this.managementTasks();
			}
		};
		Executors.newSingleThreadExecutor().execute(managerThread);
	}

	@Override
	public void schedule(CrawlURI uri) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void terminate() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void finished(CrawlURI uri) {
		
	}

	@Override
	public boolean isEmpty() {
		return queuedUriCount.get() == 0L;
	}

	@Override
	public CrawlURI next() {
		return null;
	}

	@Override
	public long queuedUriCount() {
		return queuedUriCount.get();
	}

	@Override
	public long failedFetchCount() {
		return failedFetchCount.get();
	}

	@Override
	public long succeededFetchCount() {
		return succeededFetchCount.get();
	}

	protected WorkQueue getQueueForClassKey(String classKey) {
		return workQueueMap.get(classKey);
	}

	/**
	 * finisehd, add work queue current uri belongs to snooze queue
	 * @param uri
	 */
	protected void processFinished(CrawlURI uri) {
		long now = System.currentTimeMillis();
		uri.clearUp();
		long delay = uri.getMinCrawlInterval();
		WorkQueue wq = workQueueMap.get(uri.getClassKey());
		if (wq != null) {
			addToSnoozeQueue(wq, now, delay);
		} else {
			logger.warn("failed to get workqueue for url: " + uri.getUrl());
		}
	}
	
	/**
	 * Wake any queues sitting in the snoozed queue whose time has come.
	 */
	protected void managementTasks() {
		assert Thread.currentThread() == managerThread;
		
		while(true){
			switch(targetState){
			case RUN:
				reachedState(State.RUN);
				if(isEmpty()){
					targetState = State.PAUSE;
				}
				break;
			case PAUSE:
				
				break;
			case FINISH:
				break;
			}
		}
		
		/*DelayedWorkQueue waked = null;
		while(true){
			waked = snoozeQueue.poll();
			if(waked != null){
				WorkQueue queue = waked.getWorkQueue();
				queue.setWakeTime(0L);
				addToReadyQueue(queue);
			}
		}
		
		logger.info("ending frontier manager thread.");*/
	}
	
	protected void reachedState(State justReached) {
        if(justReached != lastReachedState) {
            controller.noteFrontierState(justReached);
            lastReachedState = justReached;
        }
    }

	private void addToSnoozeQueue(WorkQueue wq, long now, long delay) {
		long nextTime = now + delay;
		wq.setWakeTime(nextTime);
		snoozeQueue.add(new DelayedWorkQueue(wq));
	}

	private void addToReadyQueue(WorkQueue qu) {
		readyQueue.add(qu);
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

	@Override
	protected CrawlURI findEligibleURI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getInProcessCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void processFinish(CrawlURI caUri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processScheduleAlways(CrawlURI caUri) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void processScheduleIfUnique(CrawlURI caUri) {
		// TODO Auto-generated method stub
		
	}
}
