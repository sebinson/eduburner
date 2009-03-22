package eduburner.web.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String index(HttpServletRequest request, Model model) {
		String error = request.getParameter("error");
		if(error != null){
			model.addAttribute("error", true);
		}
		return LOGIN_PAGE;
	}
}
