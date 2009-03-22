package eduburner.web.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eduburner.entity.user.Role;
import eduburner.entity.user.User;
import eduburner.persistence.EntityExistsException;
import eduburner.web.controller.BaseController;

@Controller
@RequestMapping("/account/signup")
public class SignupController extends BaseController {

	private static final String SIGNUP_PAGE = "signup";
	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	@RequestMapping(method = RequestMethod.GET)
	public String index(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return SIGNUP_PAGE;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String create(@ModelAttribute("user") User user, BindingResult result) {
		// 先验证绑定结果
		// new UserValidator().validate(user, result);

		if (result.hasErrors()) {
			return SIGNUP_PAGE;
		}

		// salt source is not conigurated in applicationContext-security.xml, so
		// salt is null
		user.setPassword(passwordEncoder.encodePassword(user.getPassword(),
				null));

		user.setEnabled(true);
		// Set the default user role on this new user
		user.addRole(roleManager.getRoleByName(Role.DEFAULT_ROLE_NAME));

		try {
			userManager.createUser(user);
		} catch (EntityExistsException e) {
			result.reject("该用户已存在");
			return SIGNUP_PAGE;
		}
		
		// log user in automatically
		UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getConfirmPassword(), user.getAuthorities());
        auth.setDetails(user);
        SecurityContextHolder.getContext().setAuthentication(auth);

		// status.setComplete();
		// redirect after post
		return "redirect:/";
	}

}
