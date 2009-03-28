package eduburner.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController extends BaseController {

	private static final String ADMIN_PAGE = "/admin";
	
	@RequestMapping("/admin")
	public String index(){
		return ADMIN_PAGE;
	}
}
