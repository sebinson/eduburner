package eduburner.feed.fetcher.impl;

import eduburner.feed.domain.Feed;
import eduburner.feed.fetcher.IFeedCrawler;

public class FeedCrawler implements IFeedCrawler{
	
	@Override
	public synchronized void crawle(Feed feed){
		
	}
	
	/**
	 * 负责抓取的线程
	 */
	private class FetchThread implements Runnable{
		private volatile boolean stop = false;
		@Override
		public void run() {
			while(!Thread.interrupted() && !stop){
				
			}
		}
	}
	
}
