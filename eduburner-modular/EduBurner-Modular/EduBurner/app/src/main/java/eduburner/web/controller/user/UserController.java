package eduburner.web.controller.user;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eduburner.entity.user.User;
import eduburner.persistence.EntityExistsException;
import eduburner.web.controller.BaseController;

@Controller
public class UserController extends BaseController {
	
	private static final String USER_VIEW = "fragments/user-view";
	private static final String USER_FORM = "fragments/user-form";

	@RequestMapping(value="/users", method=RequestMethod.GET)
	public String list(Model model) {
		List<User> users = userManager.getAllUsers();
		model.addAttribute("users", users);
		return JSON_VIEW;
	}
	
	@RequestMapping(value="/users/new", method=RequestMethod.GET)
	public String editNew(Model model){
		User user = new User();
		model.addAttribute("user", user);
		return USER_FORM;
	}
	
	@RequestMapping(value="users/{username}", method=RequestMethod.GET)
	public String show(@PathVariable String username, Model model){
		User user = userManager.getUserByUsername(username);
		model.addAttribute("user", user);
		return USER_VIEW;
	}
	
	@RequestMapping(value="/users", method=RequestMethod.POST)
	public String create(@ModelAttribute("user") User user, BindingResult br, Model model){
		try {
			userManager.createUser(user);
			return JSON_VIEW;
		} catch (EntityExistsException e) {
			logger.warn("user already exists. ");
			return JSON_VIEW;
		}
	}
	
	@RequestMapping(value="/users", method=RequestMethod.PUT)
	public String update(@ModelAttribute("user") User user, BindingResult br, Model model){
		userManager.updateUser(user);
		return JSON_VIEW;
	}
	
}
