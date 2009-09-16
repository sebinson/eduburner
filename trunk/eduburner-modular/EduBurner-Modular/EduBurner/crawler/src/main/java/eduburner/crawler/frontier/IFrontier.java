package eduburner.crawler.frontier;

import eduburner.crawler.ICrawlURILoader;
import eduburner.crawler.model.CrawlURI;

public interface IFrontier {

	public void init();
	
	//加载需要抓取的url
	public void loadCrawlURIs(ICrawlURILoader loader);
	
	public CrawlURI next() throws InterruptedException;
	
	public boolean isEmpty();

	/**
	 * Schedules a CrawlURI.
	 * 
	 * <p>
	 * This method accepts one URI and schedules it immediately. This has
	 * nothing to do with the priority of the URI being scheduled. Only that it
	 * will be placed in it's respective queue at once. For priority scheduling
	 * see {@link CrawlURI#setSchedulingDirective(int)}
	 * 
	 * <p>
	 * This method should be synchronized in all implementing classes.
	 * 
	 * @param caURI
	 *            The URI to schedule.
	 * 
	 * @see CrawlURI#setSchedulingDirective(int)
	 */
	public void schedule(CrawlURI uri);

	public void finished(CrawlURI uri);

	public long queuedUriCount();

	public long succeededFetchCount();

	public long failedFetchCount();
	
    public void terminate();
    
    public void pause();
    
    void requestState(State target);

	public enum State {
		RUN, // juggle/prioritize/emit; usual state
		PAUSE, // enter a stable state where no URIs are in-progress; unlike
		// HOLD requires all in-process URIs to complete
		FINISH
	}; // end and cleanup; may not return to any other state after
	// this state is requested/reached
}
