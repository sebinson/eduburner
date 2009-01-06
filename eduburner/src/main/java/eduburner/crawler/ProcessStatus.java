package eduburner.crawler;


/**
 * Returned by a Processor's process method to indicate the status of the
 * process.  
 * 
 * @author pjack
 */
public enum ProcessStatus {

    /**
     * The URI was processed normally, and no special action needs to be taken
     * by the framework.
     */
    PROCEED,

    /**
     * The Processor believes that the ProcessorURI is invalid, or otherwise
     * incapable of further processing at this time. The framework should not
     * send the URI to any more processors, but should instead perform any
     * necessary cleanup or post-processing on the URI.
     */
    FINISH,

    /**
     * The Processor has specified the next processor for the URI.  The 
     * framework should send the URI to that processor instead of the reguarly
     * scheduled next processor.
     */
    JUMP,
    
    /**
     * The Processor believes that futher processing of <i>any</i> ProcessorURIs is 
     * impossible at this point.  For instance, if a Processor detects that 
     * a network interface is unavailable, or that a disk is full.
     */
    STUCK

}
