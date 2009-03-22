package eduburner.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {

	private static final String INDEX_PAGE = "index";

	@RequestMapping(value = "/")
	public String index() {
		return INDEX_PAGE;
	}
}
