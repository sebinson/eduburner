package eduburner.feed.fetcher;

import eduburner.feed.domain.Feed;

public interface IFeedCrawler {
	
	public boolean start();
	
	public boolean stop();
	
	public void crawle(Feed feed);
}
