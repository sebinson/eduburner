package eduburner.crawler;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhangyf@gmail.com
 */
public class FeedInfo {

	protected String url = StringUtils.EMPTY;
	protected String charset = "UTF-8";
	
	public FeedInfo(int ip, String url){
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
}
