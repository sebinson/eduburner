package eduburner.crawler.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import eduburner.crawler.util.UrlUtils;

public class FeedUri1 {

	private String url = StringUtils.EMPTY;
	private String classKey = StringUtils.EMPTY;
	
	

	// User agent to masquerade as when crawling this URI. If null, globals
	// should be used
	private String userAgent = null;

	public long getMinCrawlInterval() {
		return 0L;
	}

	public String getClassKey() {
		return DigestUtils.md5Hex(UrlUtils.getHostFromUrl(this.url));
	}

	public String getUrl() {
		return url;
	}
}
