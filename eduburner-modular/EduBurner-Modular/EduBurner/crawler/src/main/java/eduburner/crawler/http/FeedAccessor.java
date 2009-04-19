package eduburner.crawler.http;

import java.io.IOException;
import java.net.URI;

import org.apache.abdera.model.Feed;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpAccessor;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.ResponseExtractor;

public class FeedAccessor extends HttpAccessor {

	private ResponseErrorHandler errorHandler = new DefaultResponseErrorHandler();
	
	public FeedAccessor(){}
	
	public FeedAccessor(ClientHttpRequestFactory requestFactory){
		setRequestFactory(requestFactory);
	}
	
	public Feed getFeedContent(URI uri, 
								 RequestCallback requestCallback, 
								 ResponseExtractor<Feed> responseExtractor){
		ClientHttpResponse response = null;
		try {
			ClientHttpRequest request = createRequest(uri, HttpMethod.GET);
			if (requestCallback != null) {
				requestCallback.doWithRequest(request);
			}
			response = request.execute();
			if (getErrorHandler().hasError(response)) {
				getErrorHandler().handleError(response);
			}
			if (responseExtractor != null) {
				return responseExtractor.extractData(response);
			}
			else {
				return null;
			}
		}
		catch (IOException ex) {
			throw new ResourceAccessException("I/O error: " + ex.getMessage(), ex);
		}
		finally {
			if (response != null) {
				response.close();
			}
		}
	}
	
	public ResponseErrorHandler getErrorHandler() {
		return this.errorHandler;
	}
}
