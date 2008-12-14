package eduburner.feed.fetcher.impl;

import eduburner.feed.fetcher.FeedEntry;

public class FeedCrawler {
	

	public synchronized void crawle(FeedEntry feedEntry){
		
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
