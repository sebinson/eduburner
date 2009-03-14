package eduburner.util;

import javax.servlet.http.HttpServletRequest;

public class RequestUtil {
	/**
	 * Retrieves the current request servlet path. Deals with differences
	 * between servlet specs (2.2 vs 2.3+)
	 * 
	 * @param request
	 *            the request
	 * @return the servlet path
	 */
	public static String getServletPath(HttpServletRequest request) {
		String servletPath = request.getServletPath();

		if (null != servletPath && !"".equals(servletPath)) {
			return servletPath;
		}

		String requestUri = request.getRequestURI();
		int startIndex = request.getContextPath().equals("") ? 0 : request
				.getContextPath().length();
		int endIndex = request.getPathInfo() == null ? requestUri.length()
				: requestUri.lastIndexOf(request.getPathInfo());

		if (startIndex > endIndex) { // this should not happen
			endIndex = startIndex;
		}

		return requestUri.substring(startIndex, endIndex);
	}

	public static String getResourceBase(HttpServletRequest req) {
		String path = getServletPath(req);
		if (path == null || "".equals(path)) {
			return "";
		}

		return path.substring(0, path.lastIndexOf('/'));
	}
}
