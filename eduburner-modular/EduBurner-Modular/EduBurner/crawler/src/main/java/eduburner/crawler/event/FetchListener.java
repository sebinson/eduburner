package eduburner.crawler.event;

import java.util.EventListener;

public interface FetchListener extends EventListener {

	/**
	 * <p>Called when a fetcher event occurs</p>
	 * 
	 * @param event the event that fired
	 */
	public void onFetchEvent(FetchEvent event);
	
}
