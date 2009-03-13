package eduburner.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eduburner.controller.BaseController;
import eduburner.entity.user.User;

@Controller
@RequestMapping("/profile")
public class EditProfileController extends BaseController {

	private static final String PAGES_PROFILE = "pages/profile";

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		User user = getRemoteUserObj();
		// UserData userData = userManager.getUserData(user.getId());
		model.addAttribute("user", user);
		return PAGES_PROFILE;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@ModelAttribute("user") User user, BindingResult result) {
		return null;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public String update() {
		return null;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public String destroy() {
		return null;
	}

}
