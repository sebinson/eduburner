package eduburner.crawler.model;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import eduburner.crawler.enumerations.FetchStatusCodes;
import eduburner.crawler.util.UrlUtils;

public class CrawlURI {

	private String url = StringUtils.EMPTY;
	private URI uri;
	private String classKey = StringUtils.EMPTY;
	
	private int fetchStatus = 0;    // default to unattempted
	private int fetchAttempts = 0; // the number of fetch attempts that have been made
	// User agent to masquerade as when crawling this URI. If null, globals
	// should be used
	private String userAgent = null;

	public CrawlURI(String url) {
		this.url = url;
		try {
			this.uri = new URI(url);
		} catch (URISyntaxException e) {
			this.uri = null;
		}
		this.classKey = DigestUtils.md5Hex(UrlUtils.getHostFromUrl(this.url));
	}

	public long getMinCrawlInterval() {
		return 0L;
	}
	
	public void clearUp(){
		fetchStatus = FetchStatusCodes.S_UNATTEMPTED;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getClassKey() {
		return classKey;
	}

	public void setClassKey(String classKey) {
		this.classKey = classKey;
	}

	public int getFetchStatus() {
		return fetchStatus;
	}

	public void setFetchStatus(int fetchStatus) {
		this.fetchStatus = fetchStatus;
	}

	public int getFetchAttempts() {
		return fetchAttempts;
	}

	public void setFetchAttempts(int fetchAttempts) {
		this.fetchAttempts = fetchAttempts;
	}
}
