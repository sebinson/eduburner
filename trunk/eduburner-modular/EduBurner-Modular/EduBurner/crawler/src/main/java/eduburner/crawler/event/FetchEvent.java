package eduburner.crawler.event;

import java.util.EventObject;

import org.apache.abdera.model.Feed;


public class FetchEvent extends EventObject {

	private static final long serialVersionUID = 3985600601904140103L;

	public static final String EVENT_TYPE_FEED_POLLED = "FEED_POLLED";
	public static final String EVENT_TYPE_FEED_RETRIEVED = "FEED_RETRIEVED";
	public static final String EVENT_TYPE_FEED_UNCHANGED = "FEED_UNCHANGED";
	
	private String eventType;
	private String urlString;
	private Feed feed;
    
	public FetchEvent(Object source) {
		super(source);
	}

	public FetchEvent(Object source, String urlStr, String eventType) {
		this(source);
		setUrlString(urlStr);
		setEventType(eventType);
	}	

	public FetchEvent(Object source, String urlStr, String eventType, Feed feed) {
		this(source, urlStr, eventType);
		setFeed(feed);
	}	
	
	/**
	 * @return Returns the eventType.
	 */
	public String getEventType() {
		return eventType;
	}
	/**
	 * @param eventType The eventType to set.
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	/**
	 * @return Returns the urlString.
	 */
	public String getUrlString() {
		return urlString;
	}
	/**
	 * @param urlString The urlString to set.
	 */
	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}


	public Feed getFeed() {
		return feed;
	}


	public void setFeed(Feed feed) {
		this.feed = feed;
	}
}
