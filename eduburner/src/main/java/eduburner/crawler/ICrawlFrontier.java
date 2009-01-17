package eduburner.crawler;

import java.io.IOException;

import eduburner.crawler.model.CrawlUri;


public interface ICrawlFrontier {
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
     * Load URIs from a file, for scheduling and/or considered-included 
     * status (if from a recovery log). 
     *
     * <p> The 'params' Map describes the source file to use and options
     * in effect regarding its format and handling. Significant keys 
     * are:
     * 
     * <p>"path": full path to source file. If the path ends '.gz', it 
     * will be considered to be GZIP compressed.
     * <p>"format": one of "onePer", "crawlLog", or "recoveryLog"
     * <p>"forceRevisit": if non-null, URIs will be force-scheduled even
     * if already considered included
     * <p>"scopeSchedules": if non-null, any URI imported be checked
     * against the frontier's configured scope before scheduling 
     * 
     * <p>If the "format" is "recoveryLog", 7 more keys are significant:
     * 
     * <p>"includeSuccesses": if non-null, success lines ("Fs") in the log
     * will be considered-included. (Usually, this is the aim of
     * a recovery-log import.)
     * <p>"includeFailures": if non-null, failure lines ("Ff") in the log
     * will be considered-included. (Sometimes, this is desired.)
     * <p>"includeScheduleds": If non-null, scheduled lines ("F+") in the 
     * log will be considered-included. (Atypical, but an option for 
     * completeness.)
     * <p>"scopeIncludes": if non-null, any of the above will be checked
     * against the frontier's configured scope before consideration
     *
     * <p>"scheduleSuccesses": if non-null, success lines ("Fs") in the log
     * will be schedule-attempted. (Atypical, as all successes
     * are preceded by "F+" lines.)
     * <p>"scheduleFailures": if non-null, failure lines ("Ff") in the log
     * will be schedule-attempted. (Atypical, as all failures
     * are preceded by "F+" lines.)
     * <p>"scheduleScheduleds": if non-null, scheduled lines ("F+") in the 
     * log will be considered-included. (Usually, this is the aim of a
     * recovery-log import.)
     * 
     * TODO: add parameter for auto-unpause-at-good-time
     * 
     * @param params Map describing source file and options as above
     * @throws IOException If problems occur reading file.
     * @throws JSONException 
     */
    public void importUris(
            String params)
			throws IOException;
    
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
