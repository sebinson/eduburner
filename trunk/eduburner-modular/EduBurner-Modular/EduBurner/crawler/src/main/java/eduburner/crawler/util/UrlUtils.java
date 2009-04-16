package eduburner.crawler.util;

public class UrlUtils {

	public static String getHostFromUrl(String url) {
		return url.replaceFirst("^https*\\://", "")
				.replaceAll("[\\?:/].*$", "");
	}

	public static String getParameterFromUrl(String url, String key) {
		String reg = "[?&]" + key + "=";
		url = url.replaceAll("&amp;", "&");
		return url.matches(".*" + reg + ".*") ? 
				   url.replaceFirst("^.*[?&]" + key + "=", "").replaceAll("&.*$", "") : null;
	}

}
