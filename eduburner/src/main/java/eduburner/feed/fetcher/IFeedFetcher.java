package eduburner.feed.fetcher;

import java.io.IOException;
import java.net.URL;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;

public interface IFeedFetcher {
	/**
	 * <p>The default user agent. It is not marked final so
	 * buggy java compiler will not write this string
	 * into all classes that reference it.</p>
	 * 
	 * <p>http://tinyurl.com/64t5n points to https://rome.dev.java.net/
	 * Some servers ban user agents with "Java" in the name.</p> 
	 * 
	 */
	public static String DEFAULT_USER_AGENT = "Rome Client";

	/**
	 * @return the User-Agent currently being sent to servers
	 */
	public abstract String getUserAgent();
	/**
	 * @param string The User-Agent to sent to servers
	 */
	public abstract void setUserAgent(String string);
	/**
	 * Retrieve a feed over HTTP
	 *
	 * @param feedUrl A non-null URL of a RSS/Atom feed to retrieve
	 * @return A {@link com.sun.syndication.feed.synd.SyndFeed} object
	 * @throws IllegalArgumentException if the URL is null;
	 * @throws IOException if a TCP error occurs
	 * @throws FeedException if the feed is not valid
	 * @throws FetcherException if a HTTP error occurred
	 */
	public abstract SyndFeed retrieveFeed(URL feedUrl)	throws IllegalArgumentException, IOException, FeedException, FetcherException;

	/**
	 * <p>Add a FetcherListener.</p>
	 *
	 * <p>The FetcherListener will receive an FetcherEvent when
	 * a Fetcher event (feed polled, retrieved, etc) occurs</p>
	 *
	 * @param listener The FetcherListener to recieve the event
	 */
	public abstract void addFetcherEventListener(IFetcherListener listener);

	/**
	 * <p>Remove a FetcherListener</p>
	 *
	 * @param listener The FetcherListener to remove
	 */
	public abstract void removeFetcherEventListener(IFetcherListener listener);

	/**
	 * <p>Is this fetcher using rfc3229 delta encoding?</p>
	 * 
	 * @return
	 */
    public abstract boolean isUsingDeltaEncoding();

    /**
     * <p>Turn on or off rfc3229 delta encoding</p>
     * 
     * <p>See http://www.ietf.org/rfc/rfc3229.txt and http://bobwyman.pubsub.com/main/2004/09/using_rfc3229_w.html</p>
     * 
     * <p>NOTE: This is experimental and feedback is welcome!</p>
     * 
     * @param useDeltaEncoding
     */
    public abstract void setUsingDeltaEncoding(boolean useDeltaEncoding);
}
