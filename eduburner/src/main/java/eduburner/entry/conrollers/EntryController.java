package eduburner.entry.conrollers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eduburner.web.controllers.BaseController;

@Controller
@RequestMapping("/entries")
public class EntryController extends BaseController{

	@RequestMapping(method = RequestMethod.GET)
	public String index(){
		return null;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String editNew(){
		return null;
	}
	
	public String create(){
		return null;
	}
	
	public String update(){
		return null;
	}
	
	public String destroy(){
		return null;
	}
}
