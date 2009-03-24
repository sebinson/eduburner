package eduburner.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;

import eduburner.entity.user.User;
import eduburner.entity.user.UserData;
import eduburner.enumerations.Message;
import eduburner.service.user.IRoleManager;
import eduburner.service.user.IUserManager;

public class BaseController {

	//private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static final long serialVersionUID = -1474235451943654164L;

	protected static final Logger logger = LoggerFactory
			.getLogger(BaseController.class);

	public static String JSON_VIEW = "jsonView";

	@Autowired
	@Qualifier("userManager")
	protected IUserManager userManager;

	@Autowired
	@Qualifier("roleManager")
	protected IRoleManager roleManager = null;

	/**
	 * Set up a custom property editor for converting form inputs to real
	 * objects
	 */
	@InitBinder
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.registerCustomEditor(Integer.class, null,
				new CustomNumberEditor(Integer.class, null, true));
		binder.registerCustomEditor(Long.class, null, new CustomNumberEditor(
				Long.class, null, true));
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, null, new CustomDateEditor(
				dateFormat, true));
		doInitBinder(request, binder);
	}

	/**
	 * template method
	 * 
	 * @param request
	 * @param binder
	 */
	protected void doInitBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
	}

	public int getRandom() {
		return (int) (Math.random() * 2147483647D);
	}

	public User getRemoteUserObj() {
		// retrieve user from security context
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth != null && auth.isAuthenticated()) {
			if (auth.getPrincipal() instanceof User) {
				return (User) auth.getPrincipal();
			} else {
				return getUser(auth.getPrincipal().toString());
			}
		}
		return null;
	}
	
	public UserData getRemoteUserDataObj(){
		User user =  getRemoteUserObj();
		if(user == null){
			return null;
		}
		return userManager.getUserData(user);
	}

	protected Long getRemoteUserId() {
		return getRemoteUserObj().getId();
	}

	private User getUser(String username) {
		return userManager.getUserByUsername(username);
	}
	
	protected void setReturnMsg(Model model, Message msg){
		model.addAttribute("msg", msg);
	}
}
