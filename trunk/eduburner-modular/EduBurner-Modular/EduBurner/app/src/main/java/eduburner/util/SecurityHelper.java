package eduburner.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

import eduburner.entity.user.User;

public class SecurityHelper {

	private static Logger logger = LoggerFactory
			.getLogger(SecurityHelper.class);
	
	private SecurityHelper(){}

	private static class SingletonHolder {
		static SecurityHelper instance = new SecurityHelper();
	}

	public static SecurityHelper getInstance() {
		return SingletonHolder.instance;
	}

	public String getPrincipal() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		if (null == auth) {
			return null;
		}
		
		// don't support spring security's default "roleAnonymous" authentication
		if (auth.getPrincipal() instanceof User && auth.isAuthenticated()) {
			return auth.getName();
		} else {
			return null;
		}
	}

	public String[] getPermissions() {
		return null;
	}

}
