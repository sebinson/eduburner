package eduburner.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexPageController {
	
	private static final String INDEX_PAGE = "index";
	
	@RequestMapping(value="/index")
	public String index(){
		return INDEX_PAGE;
	}
}
