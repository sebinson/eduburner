package eduburner.feed.fetcher.impl;

import java.io.IOException;
import java.net.URL;

import javax.annotation.PostConstruct;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
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

import eduburner.feed.fetcher.IFeedFetcherCache;
import eduburner.feed.fetcher.FetcherException;

/**
 * @author Nick Lothian
 */
public class HttpClientFeedFetcher extends AbstractFeedFetcher {

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
	public void init(){
		HttpParams params = new BasicHttpParams();
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		ConnManagerParams.setMaxTotalConnections(params, 100);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));
		ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(params,
				schemeRegistry);
		client = new DefaultHttpClient(connectionManager, params);
	}

	/**
	 * @return the feedInfoCache.
	 */
	public synchronized IFeedFetcherCache getFeedInfoCache() {
		return feedInfoCache;
	}
	
    /**
	 * @param feedInfoCache the feedInfoCache to set
	 */
	public synchronized void setFeedInfoCache(IFeedFetcherCache feedInfoCache) {
		this.feedInfoCache = feedInfoCache;
	}
	
	@Override
	public SyndFeed retrieveFeed(URL feedUrl) throws IllegalArgumentException, IOException, FeedException, FetcherException {
		if (feedUrl == null) {
			throw new IllegalArgumentException("null is not a valid URL");
		}
		return null;
	}


}
