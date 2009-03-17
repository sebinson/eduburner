package eduburner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class EntryController extends BaseController {

	@RequestMapping(value="/courses/{courseId}/entries", method=RequestMethod.GET)
	public void courseEntries(@PathVariable("courseId") long courseId, Model model){
		
	}
	
	@RequestMapping(value="/users/{userId}/entries")
	public void userEntries(@PathVariable("userId") long userId, Model model){
		
	}
	
	@RequestMapping(value="/courses/{courseId}/entries", method=RequestMethod.POST)
	public void createCourseEntry(@PathVariable("courseId") long courseId, Model model){
		
	}
}
