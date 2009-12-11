package readerside.crawler.model;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import readerside.crawler.enumerations.FetchStatusCodes;
import readerside.crawler.frontier.WorkQueue;
import readerside.crawler.util.UrlUtils;

public class CrawlURI implements Serializable{
	private static final long serialVersionUID = 968032112154104528L;

    private static final Logger logger = LoggerFactory.getLogger(CrawlURI.class);

    private URI uri;
	private String url = StringUtils.EMPTY;
	private String classKey = StringUtils.EMPTY;
    private String userId;
	
	private int fetchStatus = 0;    // default to unattempted
	private int fetchAttempts = 0; // the number of fetch attempts that have been made
	
	private WorkQueue holder;

	public CrawlURI(String userId) {
        this.userId = userId;
		this.url = UrlUtils.getSharedUrlById(userId);
		this.uri = URI.create(url);
		//this.classKey = DigestUtils.md5Hex(UrlUtils.getHostFromUrl(this.url));
        //only one queue
        this.classKey = "greader";
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

	public String getClassKey() {
		return classKey;
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
	
	public WorkQueue getHolder() {
		return holder;
	}

	public void setHolder(WorkQueue holder) {
		this.holder = holder;
	}

    public String getUserId() {
        return userId;
    }

    @Override
	public boolean equals(Object o){
		CrawlURI c = (CrawlURI)o;
		if(this == c){
			return true;
		}
		if(this.classKey.equals(c.classKey) && this.url.equals(c.url)){
			return true;
		}
		return false;
	}

    @Override
    public String toString() {
        return "CrawlURI{" +
                "url='" + url + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
