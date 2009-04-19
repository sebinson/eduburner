package eduburner.crawler.http;

import java.io.IOException;

import org.apache.abdera.model.Feed;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

public class FeedResponseExtractor implements ResponseExtractor<Feed> {

	@Override
	public Feed extractData(ClientHttpResponse response) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
