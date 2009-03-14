package eduburner.controller.course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CourseController {
	
	@RequestMapping(value="/courses")
	public void index(){
		
	}
	
	@RequestMapping(value="/courses/{courseId}")
	public void show(){
		
	}
	
	@RequestMapping(value="/courses/new")
	public void editNew(){
		
	}
	
	@RequestMapping(value="/courses/{courseId}/edit")
	public void edit(){
		
	}
	
	@RequestMapping(value="/courses", method=RequestMethod.POST)
	public void create(){
		
	}
	
	@RequestMapping(value="/courses/{courseId}", method=RequestMethod.PUT)
	public void update(){
		
	}
	
	@RequestMapping(value="/courses/{courseId}", method=RequestMethod.DELETE)
	public void destroy(){
		
	}
}
