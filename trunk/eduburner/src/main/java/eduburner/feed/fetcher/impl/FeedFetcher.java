package eduburner.feed.fetcher.impl;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;

import eduburner.feed.fetcher.FetcherEvent;
import eduburner.feed.fetcher.FetcherException;
import eduburner.feed.fetcher.IFeedFetcher;
import eduburner.feed.fetcher.IFetcherListener;

public class FeedFetcher implements IFeedFetcher {
	
	private String userAgent;
	private boolean usingDeltaEncoding;
	
	@Autowired
	@Qualifier("fetcherProperties")
	private Properties fetcherProperties;
	
	private final Set<IFetcherListener> fetcherEventListeners;
	
	public FeedFetcher() {
		fetcherEventListeners = new CopyOnWriteArraySet<IFetcherListener>();
		setUserAgent(DEFAULT_USER_AGENT + " Ver: " + fetcherProperties.getProperty("rome.fetcher.version", "UNKNOWN"));
	}
	
	@Override
	public SyndFeed retrieveFeed(URL feedUrl) throws IllegalArgumentException,
			IOException, FeedException, FetcherException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the User-Agent currently being sent to servers
	 */
	public synchronized String getUserAgent() {
		return userAgent;
	}

	/**
	 * @param string The User-Agent to sent to servers
	 */
	public synchronized void setUserAgent(String string) {
		userAgent = string;
	}

	/**
	 * @param eventType The event type to fire
	 * @param connection the current connection
	 */
	protected void fireEvent(String eventType, URLConnection connection) {	    
		fireEvent(eventType, connection.getURL().toExternalForm(), null);
	}
	
	
	/**
	 * @param eventType The event type to fire
	 * @param connection the current connection
	 * @param feed The feed to pass to the event
	 */
	protected void fireEvent(String eventType, URLConnection connection, SyndFeed feed) {	    
		fireEvent(eventType, connection.getURL().toExternalForm(), feed);
	}

	/**
	 * @param eventType The event type to fire
	 * @param urlStr the current url as a string
	 */
	protected void fireEvent(String eventType, String urlStr) {
	    fireEvent(eventType, urlStr, null);
	}	
	
	/**
	 * @param eventType The event type to fire
	 * @param urlStr the current url as a string
	 * @param feed The feed to pass to the event
	 */
	protected void fireEvent(String eventType, String urlStr, SyndFeed feed) {
		FetcherEvent fetcherEvent = new FetcherEvent(this, urlStr, eventType, feed);
		synchronized(fetcherEventListeners) {
			for(IFetcherListener fetcherEventListener : fetcherEventListeners){
				fetcherEventListener.fetcherEvent(fetcherEvent);	
			}
		}
	}	
	
	/**
	 * @see com.sun.syndication.fetcher.FeedFetcher#addFetcherEventListener(com.sun.syndication.fetcher.FetcherListener)
	 */
	public void addFetcherEventListener(IFetcherListener listener) {
		if (listener != null) {
			fetcherEventListeners.add(listener);		
		}	
		
	}

	/**
	 * @see com.sun.syndication.fetcher.FeedFetcher#removeFetcherEventListener(com.sun.syndication.fetcher.FetcherListener)
	 */
	public void removeFetcherEventListener(IFetcherListener listener) {
		if (listener != null) {
			fetcherEventListeners.remove(listener);		
		}		
	}

    /**
     * @return Returns the useDeltaEncoding.
     */
    public synchronized boolean isUsingDeltaEncoding() {
        return usingDeltaEncoding;
    }
    /**
     * @param useDeltaEncoding The useDeltaEncoding to set.
     */
    public synchronized void setUsingDeltaEncoding(boolean useDeltaEncoding) {
        this.usingDeltaEncoding = useDeltaEncoding;
    }		
	
	/**
	 * <p>Handles HTTP error codes.</p>
	 *
	 * @param responseCode the HTTP response code
	 * @throws FetcherException if response code is in the range 400 to 599 inclusive
	 */
	protected void handleErrorCodes(int responseCode) throws FetcherException {
		// Handle 2xx codes as OK, so ignore them here
		// 3xx codes are handled by the HttpURLConnection class
	    if (responseCode == 403) {
	        // Authentication is required
	        throwAuthenticationError(responseCode);
	    } else if (responseCode >= 400 && responseCode < 500) {
			throw4XXError(responseCode);
		} else if (responseCode >= 500 && responseCode < 600) {
			throw new FetcherException(responseCode, "The server encounted an error. HTTP Response code was:" + responseCode);
		}
	}
	
	protected void throw4XXError(int responseCode) throws FetcherException {
		throw new FetcherException(responseCode, "The requested resource could not be found. HTTP Response code was:" + responseCode);
	}

	protected void throwAuthenticationError(int responseCode) throws FetcherException {
		throw new FetcherException(responseCode, "Authentication required for that resource. HTTP Response code was:" + responseCode);
	}
	
	/**
	 * <p>Combine the entries in two feeds into a single feed.</p>
	 * 
	 * <p>The returned feed will have the same data as the newFeed parameter, with 
	 * the entries from originalFeed appended to the end of its entries.</p>
	 * 
	 * @param originalFeed
	 * @param newFeed
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static SyndFeed combineFeeds(SyndFeed originalFeed, SyndFeed newFeed) {
	    SyndFeed result;
        try {
            result = (SyndFeed) newFeed.clone();
            
            result.getEntries().addAll(result.getEntries().size(), originalFeed.getEntries());
            
            return result;
        } catch (CloneNotSupportedException e) {
            IllegalArgumentException iae = new IllegalArgumentException("Cannot clone feed");
            iae.initCause(e);
            throw iae;
        }        
	}
	
}
