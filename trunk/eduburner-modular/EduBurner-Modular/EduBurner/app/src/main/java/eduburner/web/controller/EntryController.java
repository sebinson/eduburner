package eduburner.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import eduburner.entity.user.UserData;

@Controller
public class EntryController extends BaseController {
	
	@RequestMapping(value="/users/{userId}/entries")
	public void userEntries(@PathVariable("userId") String userId, Model model){
		UserData user = userManager.getUserDataByUserId(userId);
	}
	
}
