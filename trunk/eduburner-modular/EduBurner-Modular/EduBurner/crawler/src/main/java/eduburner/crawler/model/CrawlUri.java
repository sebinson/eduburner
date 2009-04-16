package eduburner.crawler.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import eduburner.crawler.util.UrlUtils;

public class CrawlUri {
	
	private String uri = StringUtils.EMPTY;

	// default to unattempted
	private int fetchStatus = 0;
	// the number of fetch attempts that have been made
	private int fetchAttempts = 0;
	// User agent to masquerade as when crawling this URI. If null, globals
	// should be used
	private String userAgent = null;

	public long getMinCrawlInterval() {
		return 0L;
	}
	
	public String getClassKey(){
		return DigestUtils.md5Hex(UrlUtils.getHostFromUrl(this.uri));
	}
}
