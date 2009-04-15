package eduburner.crawler;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import eduburner.crawler.model.CrawlUri;

/**
 * A single queue of related URIs to visit, grouped by a classKey
 * (typically "hostname:port" or similar) 
 *
 */
public abstract class WorkQueue {
	
}
