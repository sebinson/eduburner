package eduburner.feed.fetcher.impl;

import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to help deal with HTTP responses
 *
 */
public class ResponseHandler {
	public static final String defaultCharacterEncoding = "ISO-8859-1";
	
	private final static Pattern characterEncodingPattern = Pattern.compile("charset=([.[^; ]]*)");
	
	public static String getCharacterEncoding(URLConnection connection) {
		return getCharacterEncoding(connection.getContentType());
	}
	
	/**
	 * 
	 * <p>Gets the character encoding of a response. (Note that this is different to
	 * the content-encoding)</p>
	 * 
	 * @param contentTypeHeader the value of the content-type HTTP header eg: text/html; charset=ISO-8859-4
	 * @return the character encoding, eg: ISO-8859-4
	 */
	public static String getCharacterEncoding(String contentTypeHeader) {
		if (contentTypeHeader == null) {
			return defaultCharacterEncoding;
		}
		
		Matcher m = characterEncodingPattern.matcher(contentTypeHeader);
		//if (!m.matches()) {
		if (!m.find()) {
			return defaultCharacterEncoding;
		} else {
			return m.group(1);
		}
	}
}
