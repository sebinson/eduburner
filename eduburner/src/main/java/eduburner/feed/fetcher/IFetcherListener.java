package eduburner.feed.fetcher;

import java.util.EventListener;


public interface IFetcherListener extends EventListener {

	/**
	 * <p>Called when a fetcher event occurs</p>
	 * 
	 * @param event the event that fired
	 */
	public void fetcherEvent(FetcherEvent event);
	
}
