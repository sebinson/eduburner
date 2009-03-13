package eduburner.controller.entry;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eduburner.controller.BaseController;

@Controller
public class EntryController extends BaseController {

	@RequestMapping(value="/courses/{courseId}/entries", method=RequestMethod.GET)
	public void courseEntries(@PathVariable("courseId") long courseId, Model model){
		
	}
	
	@RequestMapping(value="/courses/{courseId}/entries", method=RequestMethod.POST)
	public void createCourseEntry(@PathVariable("courseId") long courseId, Model model){
		
	}
}
