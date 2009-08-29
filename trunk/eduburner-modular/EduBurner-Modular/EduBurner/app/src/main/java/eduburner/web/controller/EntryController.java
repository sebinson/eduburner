package eduburner.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eduburner.entity.Entry;
import eduburner.entity.user.UserData;
import eduburner.persistence.Page;
import eduburner.service.user.IUserManager;

@Controller
public class EntryController extends BaseController {
	
	private static final String ENTRIES_FRAG = "fragments/entriesFrag";
	
	@Autowired
	@Qualifier("userManager")
	private IUserManager userManager;
	
	@RequestMapping(value="/users/{userId}/entries")
	public void userEntries(@PathVariable("userId") String userId, Model model){
		UserData user = userManager.getUserDataByUserId(userId);
		//TODO:
	}
	
	@RequestMapping(value="/entries/", method = RequestMethod.POST)
	public String create(Model model, @ModelAttribute Entry entry){
		UserData user = getRemoteUserDataObj();
		userManager.createEntry(user, entry);
		Page<Entry> page = userManager.getUserEntriesPage(user, 1);
		model.addAttribute("page", page);
		return ENTRIES_FRAG;
	}
	
}
