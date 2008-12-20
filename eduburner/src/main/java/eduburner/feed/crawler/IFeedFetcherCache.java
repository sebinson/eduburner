package eduburner.feed.crawler;

import java.net.URL;


/**
 * <p>An interface to allow caching of feed details. Implementing this allows the
 * {@link com.sun.syndication.fetcher.io.HttpURLFeedFetcher} class to 
 * enable conditional gets</p> 
 * 
 * @author Nick Lothian
 *
 */
public interface IFeedFetcherCache {
	/**
	 * Get a SyndFeedInfo object from the cache.
	 * 
	 * @param feedUrl The url of the feed
	 * @return A SyndFeedInfo or null if it is not in the cache
	 */
	public SyndFeedInfo getFeedInfo(URL feedUrl);
	
	/**
	 * Add a SyndFeedInfo object to the cache
	 * 
	 * @param feedUrl  The url of the feed
	 * @param syndFeedInfo A SyndFeedInfo for the feed
	 */
	public void setFeedInfo(URL feedUrl, SyndFeedInfo syndFeedInfo);
}
