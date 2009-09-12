package eduburner.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eduburner.entity.Entry;
import eduburner.persistence.Page;

@Controller
public class IndexController extends BaseController {

	private static final String INDEX_PAGE = "index";

	@RequestMapping(value = "/")
	public String index(Model model, HttpServletRequest request) {
		String p = request.getParameter("p");
		int pageNo = 1;
		if(p != null){
			pageNo = Integer.parseInt(p);
		}
		Page<Entry> page = userManager.getHomePageEntriesForUser(getRemoteUserDataObj(), pageNo);
		model.addAttribute("page", page);
		return INDEX_PAGE;
	}
}
