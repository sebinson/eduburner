package eduburner.crawler.processor;

import eduburner.crawler.ProcessResult;

/**
 * Tagging interface for post processors.  Needed so that ToeThreads can
 * properly handle {@link ProcessResult#FINISH}. 
 * 
 * @author pjack
 */
public interface IPostProcessor {
	
}
