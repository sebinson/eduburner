package eduburner.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eduburner.entity.Entry;
import eduburner.persistence.Page;

@Controller
public class IndexController extends BaseController {

	private static final String INDEX_PAGE = "index";

	@RequestMapping(value = "/")
	public String index(Model model) {
		Page<Entry> page = userManager.getUserEntriesPage(getRemoteUserDataObj(), 1);
		model.addAttribute("page", page);
		return INDEX_PAGE;
	}
}
