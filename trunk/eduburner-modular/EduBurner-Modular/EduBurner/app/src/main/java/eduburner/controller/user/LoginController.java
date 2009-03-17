package eduburner.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/account/login")
public class LoginController {

	private static final String LOGIN_PAGE = "/login";

	/**
	 * go to login page
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return LOGIN_PAGE;
	}
}
