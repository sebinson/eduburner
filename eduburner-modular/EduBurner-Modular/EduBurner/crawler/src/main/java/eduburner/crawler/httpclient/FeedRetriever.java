package eduburner.crawler.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import eduburner.crawler.model.CrawlUri;

public class FeedRetriever {
	
	@Autowired
	@Qualifier("httpClient")
	private HttpClient client;
	
	public String getFeedContent(CrawlUri uri){
		String url = uri.getUri();
		HttpGet httpget = new HttpGet(url);
		try {
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			StatusLine statusLine = response.getStatusLine();
			int code = statusLine.getStatusCode();
			return EntityUtils.toString(entity);
		}catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
