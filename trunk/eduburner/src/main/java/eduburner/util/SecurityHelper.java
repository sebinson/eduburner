package eduburner.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;

public class SecurityHelper {
	
	private static Logger logger = LoggerFactory.getLogger(SecurityHelper.class);
	
	public String getPrincipal() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		if (null == auth) {
			return null;
		}
		
		//logger.debug("principal is: " + ((UserDetails) auth.getPrincipal()).getUsername());

		return auth.getName();
	}
	
	public String[] getPermissions(){
		return null;
	}

}
