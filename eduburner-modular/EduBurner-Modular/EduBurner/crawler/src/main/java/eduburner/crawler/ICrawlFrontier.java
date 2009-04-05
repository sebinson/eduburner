package eduburner.crawler;

import java.util.List;

import eduburner.crawler.model.CrawlUri;


public interface ICrawlFrontier {
	
	public void loadUris(List<CrawlUri> uris);
	/**
     * Get the next URI that should be processed. If no URI becomes availible
     * during the time specified null will be returned.
     *
     * @return the next URI that should be processed.
     * @throws InterruptedException
     * @throws EndedException 
     */
    CrawlUri next();

    /**
     * Returns true if the frontier contains no more URIs to crawl.
     *
     * <p>That is to say that there are no more URIs either currently availible
     * (ready to be emitted), URIs belonging to deferred hosts or pending URIs
     * in the Frontier. Thus this method may return false even if there is no
     * currently availible URI.
     *
     * @return true if the frontier contains no more URIs to crawl.
     */
    boolean isEmpty();

    /**
     * Schedules a CrawlURI.
     *
     * <p>This method accepts one URI and schedules it immediately. This has
     * nothing to do with the priority of the URI being scheduled. Only that
     * it will be placed in it's respective queue at once. For priority
     * scheduling see {@link CrawlURI#setSchedulingDirective(int)}
     *
     * <p>This method should be synchronized in all implementing classes.
     *
     * @param caURI The URI to schedule.
     *
     * @see CrawlURI#setSchedulingDirective(int)
     */
    public void schedule(CrawlUri uri);

    /**
     * Report a URI being processed as having finished processing.
     *
     * <p>ToeThreads will invoke this method once they have completed work on
     * their assigned URI.
     *
     * <p>This method is synchronized.
     *
     * @param cURI The URI that has finished processing.
     */
    public void finished(CrawlUri uri);


    /**
     * Number of URIs <i>queued</i> up and waiting for processing.
     *
     * <p>This includes any URIs that failed but will be retried. Basically this
     * is any <i>discovered</i> URI that has not either been processed or is
     * being processed. The same discovered URI can be queued multiple times.
     *
     * @return Number of queued URIs.
     */
    public long queuedUriCount();
    
    /**
     * Number of <i>successfully</i> processed URIs.
     *
     * <p>Any URI that was processed successfully. This includes URIs that
     * returned 404s and other error codes that do not originate within the
     * crawler.
     *
     * @return Number of <i>successfully</i> processed URIs.
     */
    public long succeededFetchCount();

    /**
     * Number of URIs that <i>failed</i> to process.
     *
     * <p>URIs that could not be processed because of some error or failure in
     * the processing chain. Can include failure to acquire prerequisites, to
     * establish a connection with the host and any number of other problems.
     * Does not count those that will be retried, only those that have
     * permenantly failed.
     *
     * @return Number of URIs that failed to process.
     */
    public long failedFetchCount();
    
    /**
     * Enumeration of possible target states. 
     */
    public enum State { 
        RUN,  // juggle/prioritize/emit; usual state
        HOLD, // enter a consistent, stable, checkpointable state ASAP
        PAUSE, // enter a stable state where no URIs are in-progress; unlike
               // HOLD requires all in-process URIs to complete
        FINISH }; // end and cleanup; may not return to any other state after
                  // this state is requested/reached

}
