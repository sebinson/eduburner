package eduburner.feed.crawler.impl;

import java.net.URL;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import eduburner.feed.crawler.IFeedFetcherCache;
import eduburner.feed.crawler.SyndFeedInfo;

@Component("feedFetcherCache")
public class FeedFetcherCache implements IFeedFetcherCache {

	@Autowired
	@Qualifier("cache")
	private Ehcache fetcherCache;

	@Override
	public SyndFeedInfo getFeedInfo(URL feedUrl) {

		Element el = fetcherCache.get(feedUrl);

		if (el == null) {
			return null;
		} else {
			return (SyndFeedInfo) el.getValue();
		}

	}

	@Override
	public void setFeedInfo(URL feedUrl, SyndFeedInfo syndFeedInfo) {
		Element el = new Element(feedUrl, syndFeedInfo);
		fetcherCache.put(el);
	}

}
