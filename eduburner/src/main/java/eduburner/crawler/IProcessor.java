package eduburner.crawler;


public interface IProcessor {
	
	/**
     * Processes the given URI.  First checks {@link #ENABLED} and
     * {@link #DECIDE_RULES}.  If ENABLED is false, then nothing happens.
     * If the DECIDE_RULES indicate REJECT, then the 
     * {@link #innerRejectProcess(ProcessorURI)} method is invoked, and
     * the process method returns.
     * 
     * <p>Next, the {@link #shouldProcess(ProcessorURI)} method is 
     * consulted to see if this Processor knows how to handle the given
     * URI.  If it returns false, then nothing futher occurs.
     * 
     * <p>FIXME: Should innerRejectProcess be called when ENABLED is false,
     * or when shouldProcess returns false?  The previous Processor 
     * implementation didn't handle it that way.
     * 
     * <p>Otherwise, the URI is considered valid.  This processor's count
     * of handled URIs is incremented, and the 
     * {@link #innerProcess(ProcessorURI)} method is invoked to actually
     * perform the process.
     * 
     * @param uri  The URI to process
     * @throws  InterruptedException   if the thread is interrupted
     */
    public ProcessResult process(CrawlUri uri);
}
