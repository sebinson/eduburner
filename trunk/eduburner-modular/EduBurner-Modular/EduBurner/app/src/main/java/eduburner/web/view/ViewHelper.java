package eduburner.web.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import eduburner.entity.user.User;
import eduburner.entity.user.UserData;
import eduburner.service.user.IUserManager;

@Component("viewHelper")
public class ViewHelper {

	@Autowired
	@Qualifier("userManager")
	private IUserManager userManager;
	
	private static Logger logger = LoggerFactory
			.getLogger(ViewHelper.class);

	public User getPrincipal() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();

		if (null == auth) {
			return null;
		}
		
		// don't support spring security's default "roleAnonymous" authentication
		Object principal = auth.getPrincipal();
		if (principal instanceof User && auth.isAuthenticated()) {
			return (User)principal;
		} else {
			return null;
		}
	}
	
	public UserData getUser(){
		User user = getPrincipal();
		if(user == null){
			return null;
		}else{
			return userManager.getUserData(user);
		}
	}

	public String[] getPermissions() {
		return null;
	}

}
