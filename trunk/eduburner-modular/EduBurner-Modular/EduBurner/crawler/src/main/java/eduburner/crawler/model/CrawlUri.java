package eduburner.crawler.model;


public class CrawlUri {

	private int fetchStatus = 0; // default to unattempted
	private int fetchAttempts = 0; // the number of fetch attempts that have been made

	// User agent to masquerade as when crawling this URI. If null, globals
	// should be used
	private String userAgent = null;

}
