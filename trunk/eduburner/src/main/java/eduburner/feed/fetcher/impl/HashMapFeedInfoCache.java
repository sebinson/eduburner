package eduburner.feed.fetcher.impl;

import java.io.Serializable;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import eduburner.feed.fetcher.IFeedFetcherCache;


/**
 * <p>A very simple implementation of the {@link eduburner.feed.fetcher.IFeedFetcherCache.impl.FeedFetcherCache} interface.</p>
 * 
 * <p>This implementation uses a HashMap to cache retrieved feeds. This implementation is
 * most suitible for sort term (client aggregator?) use, as the memory usage will increase
 * over time as the number of feeds in the cache increases.</p>
 * 
 * @author Nick Lothian
 *
 */
public class HashMapFeedInfoCache implements IFeedFetcherCache, Serializable {
	private static final long serialVersionUID = -1594665619950916222L;

	static HashMapFeedInfoCache _instance;
	private Map infoCache;
	
	/**
	 * <p>Constructor for HashMapFeedInfoCache</p>
	 * 
	 * <p>Only use this if you want multiple instances of the cache. 
	 * Usually getInstance() is more appropriate.</p>
	 *
	 */
	public HashMapFeedInfoCache() {
		setInfoCache(createInfoCache());
	}

	/**
	 * Get the global instance of the cache
	 * @return an implementation of FeedFetcherCache
	 */
	public static synchronized IFeedFetcherCache getInstance() {
		if (_instance == null) {
			_instance = new HashMapFeedInfoCache();			
		}
		return _instance;
	}

	protected Map createInfoCache() {
 		return (Collections.synchronizedMap(new HashMap()));
 	}

	
	protected Object get(Object key) {
		return getInfoCache().get(key);
	}

	/**
	 * @see IFeedFetcherCache.io.FeedFetcherCache#getFeedInfo(java.net.URL)
	 */
	public SyndFeedInfo getFeedInfo(URL feedUrl) {
		return (SyndFeedInfo) get(feedUrl);
	}

	protected void put(Object key, Object value) {
		getInfoCache().put(key, value);
	}

	/**
	 * @see IFeedFetcherCache.io.FeedFetcherCache#setFeedInfo(java.net.URL, extensions.io.SyndFeedInfo)
	 */
	public void setFeedInfo(URL feedUrl, SyndFeedInfo syndFeedInfo) {
		put(feedUrl, syndFeedInfo);		
	}

	protected synchronized final Map getInfoCache() {
		return infoCache;
	}

	/**
	 * The API of this class indicates that map must thread safe. In other
	 * words, be sure to wrap it in a synchronized map unless you know
	 * what you are doing.
	 * 
	 * @param map the map to use as the info cache.
	 */
	protected synchronized final void setInfoCache(Map map) {
		infoCache = map;
	}

}
