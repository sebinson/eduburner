package eduburner.feed.crawler.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;

import eduburner.feed.crawler.FetcherEvent;
import eduburner.feed.crawler.FetcherException;
import eduburner.feed.crawler.IFeedFetcherCache;
import eduburner.feed.crawler.SyndFeedInfo;

/**
 * @author Nick Lothian
 */

public class HttpClientFeedFetcher extends FeedFetcher {

	private IFeedFetcherCache feedInfoCache;

	private HttpClient client;

	public HttpClientFeedFetcher() {
		super();
	}

	/**
	 * @param cache
	 */
	public HttpClientFeedFetcher(IFeedFetcherCache cache) {
		this();
		setFeedInfoCache(cache);
	}

	@PostConstruct
	public void init() {
		HttpParams params = new BasicHttpParams();
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		ConnManagerParams.setMaxTotalConnections(params, 100);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(
				params, schemeRegistry);
		client = new DefaultHttpClient(connectionManager, params);
	}

	/**
	 * @return the feedInfoCache.
	 */
	public synchronized IFeedFetcherCache getFeedInfoCache() {
		return feedInfoCache;
	}

	/**
	 * @param feedInfoCache
	 *            the feedInfoCache to set
	 */
	public synchronized void setFeedInfoCache(IFeedFetcherCache feedInfoCache) {
		this.feedInfoCache = feedInfoCache;
	}

	@Override
	public SyndFeed retrieveFeed(URL feedUrl) throws IllegalArgumentException,
			IOException, FeedException, FetcherException {
		if (feedUrl == null) {
			throw new IllegalArgumentException("null is not a valid URL");
		}

		System.setProperty("httpclient.useragent", getUserAgent());
		String urlStr = feedUrl.toString();
		IFeedFetcherCache cache = getFeedInfoCache();

		if (cache != null) {
			HttpGet httpGet = new HttpGet(urlStr);
			httpGet.addHeader("Accept-Encoding", "gzip");

			if (isUsingDeltaEncoding()) {
				httpGet.addHeader("A-IM", "feed");
			}

			// get the feed info from the cache
			// Note that syndFeedInfo will be null if it is not in the cache
			SyndFeedInfo syndFeedInfo = cache.getFeedInfo(feedUrl);
			if (syndFeedInfo != null) {
				httpGet.addHeader("If-None-Match", syndFeedInfo.getETag());

				if (syndFeedInfo.getLastModified() instanceof String) {
					httpGet.addHeader("If-Modified-Since",
							(String) syndFeedInfo.getLastModified());
				}
			}

			HttpResponse response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();

			fireEvent(FetcherEvent.EVENT_TYPE_FEED_POLLED, urlStr);
			handleErrorCodes(statusCode);

		} else {

		}

		return null;
	}

	private SyndFeedInfo buildSyndFeedInfo(URL feedUrl, String urlStr,
			HttpResponse response, SyndFeed feed, int statusCode)
			throws MalformedURLException {
		SyndFeedInfo syndFeedInfo;
		syndFeedInfo = new SyndFeedInfo();

		// this may be different to feedURL because of 3XX redirects
		syndFeedInfo.setUrl(new URL(urlStr));
		syndFeedInfo.setId(feedUrl.toString());

		Header imHeader = response.getFirstHeader("IM");
		if (imHeader != null && imHeader.getValue().indexOf("feed") >= 0
				&& isUsingDeltaEncoding()) {
			IFeedFetcherCache cache = getFeedInfoCache();
			if (cache != null && statusCode == 226) {
				// client is setup to use http delta encoding and the server
				// supports it and has returned a delta encoded response
				// This response only includes new items
				SyndFeedInfo cachedInfo = cache.getFeedInfo(feedUrl);
				if (cachedInfo != null) {
					SyndFeed cachedFeed = cachedInfo.getSyndFeed();

					// set the new feed to be the orginal feed plus the new
					// items
					feed = combineFeeds(cachedFeed, feed);
				}
			}
		}

		Header lastModifiedHeader = response.getFirstHeader("Last-Modified");
		if (lastModifiedHeader != null) {
			syndFeedInfo.setLastModified(lastModifiedHeader.getValue());
		}

		Header eTagHeader = response.getFirstHeader("ETag");
		if (eTagHeader != null) {
			syndFeedInfo.setETag(eTagHeader.getValue());
		}

		syndFeedInfo.setSyndFeed(feed);
		
		return syndFeedInfo;
	}

}
