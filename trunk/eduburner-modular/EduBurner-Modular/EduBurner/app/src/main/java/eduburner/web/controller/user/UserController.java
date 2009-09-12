package eduburner.web.controller.user;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eduburner.entity.user.User;
import eduburner.entity.user.UserData;
import eduburner.enumerations.Message;
import eduburner.persistence.EntityExistsException;
import eduburner.web.controller.BaseController;

@Controller
public class UserController extends BaseController {
	
	private static final String USER_FORM = "fragments/userForm";
	private static final String USER_VIEW = "user";
	private static final String FRIENDS_VIEW = "friendList";
	private static final String SEARCH_FRIENDS_VIEW = "friendSearch";

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
	
	@RequestMapping(value="/users/{username}", method=RequestMethod.GET)
	public String show(@PathVariable String username, Model model){
		UserData user = userManager.getUserDataByUsername(username);
		model.addAttribute("userToShow", user);
		return USER_VIEW;
	}
	
	@RequestMapping(value="/users.*", method=RequestMethod.POST)
	public String create(@ModelAttribute("user") User user, BindingResult br, Model model){
		try {
			userManager.createUser(user);
			return JSON_VIEW;
		} catch (EntityExistsException e) {
			logger.warn("user already exists. ");
			return JSON_VIEW;
		}
	}
	
	@RequestMapping(value="/users.*", method=RequestMethod.PUT)
	public String update(@ModelAttribute("user") User user, BindingResult br, Model model){
		userManager.updateUser(user);
		return JSON_VIEW;
	}
	
	@RequestMapping(value="/account/alterpassword", method=RequestMethod.POST)
	public String alterPassword(){
		return JSON_VIEW;
	}
	
	@RequestMapping(value="/invite.json", method=RequestMethod.POST)
	public String sendInvitation(){
		return JSON_VIEW;
	}
	
	@RequestMapping(value="/friends/add.json", method=RequestMethod.POST)
	public String addFriend(@RequestParam("requestor") String requestor,
			@RequestParam("candidate") String candidate, Model model) {
		UserData u1 = userManager.getUserDataByUsername(requestor);
		UserData u2 = userManager.getUserDataByUsername(candidate);
		userManager.addFriend(u1, u2);
		setReturnMsg(model, Message.OK);
		return JSON_VIEW;
	}
	
	@RequestMapping(value="/friends", method=RequestMethod.GET)
	public String friendList(Model model){
		List<UserData> friends = getRemoteUserDataObj().getFriends();
		model.addAttribute("friends", friends);
		return FRIENDS_VIEW;
	}
	
	@RequestMapping(value="/friends/search", method=RequestMethod.GET)
	public String searchFriendView(Model model){
		List<UserData> users = userManager.getAllUserDatas();
		model.addAttribute("users", users);
		return SEARCH_FRIENDS_VIEW;
	}
}
