package eduburner.crawler.http;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

public class CommonsClientHttpResponse implements ClientHttpResponse {

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public HttpStatus getStatusCode() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatusText() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InputStream getBody() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpHeaders getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

}
