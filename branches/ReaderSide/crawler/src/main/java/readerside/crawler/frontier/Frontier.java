package readerside.crawler.frontier;

import readerside.crawler.model.CrawlURI;

public interface Frontier {

	public void init();
	
	public CrawlURI next() throws InterruptedException;
	
	public boolean isEmpty();

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
