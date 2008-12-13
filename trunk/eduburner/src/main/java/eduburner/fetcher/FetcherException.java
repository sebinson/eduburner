package eduburner.fetcher;

/**
 * @author Nick Lothian
 *
 */
public class FetcherException extends Exception {
	private static final long serialVersionUID = -7479645796948092380L;

	int responseCode;
	
	public FetcherException(Throwable cause) {
		super();
		initCause(cause);
	}
	
	public FetcherException(String message, Throwable cause) {
		super(message);
		initCause(cause);
	}
	
	public FetcherException(String message) {
		super(message);
	}
	
	public FetcherException(int responseCode, String message) {
		this(message);		
		this.responseCode = responseCode;
	}

	public int getResponseCode() {
		return responseCode;
	}

}
