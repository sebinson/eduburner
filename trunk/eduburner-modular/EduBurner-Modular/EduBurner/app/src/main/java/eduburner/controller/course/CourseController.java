package eduburner.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eduburner.controller.BaseController;

import eduburner.entity.course.Course;
import eduburner.service.course.ICourseManager;

@Controller
public class CourseController extends BaseController{
	
	@Autowired
	@Qualifier("courseManager")
	private ICourseManager courseManager;

	private static String COURSE_FORM = "/course-form";
	private static String COURSE_LIST = "/course-list";

	@RequestMapping(value = "/courses")
	public void allCourses() {
		
	}

	@RequestMapping(value = "/users/{userId}/courses")
	public void userCourses() {

	}

	@RequestMapping(value = "/courses/{courseId}/entries")
	public void courseEntries(@PathVariable("courseId") long courseId,
			Model model) {
		
	}

	@RequestMapping(value = "/courses/{courseId}")
	public void show() {

	}

	@RequestMapping(value = "/courses/new")
	public String editNew(Model model) {
		Course course = new Course();
		model.addAttribute("course", course);
		return COURSE_FORM;
	}

	@RequestMapping(value = "/courses/{courseId}/edit")
	public void edit(@PathVariable("courseId") long courseId) {
		
	}

	@RequestMapping(value = "/courses", method = RequestMethod.POST)
	public void create(@ModelAttribute("course") Course course) {
		
	}

	@RequestMapping(value = "/courses/{courseId}/entries", method = RequestMethod.POST)
	public void createCourseEntry(@PathVariable("courseId") long courseId,
			Model model) {

	}

	@RequestMapping(value = "/courses/{courseId}", method = RequestMethod.PUT)
	public void update() {

	}

	@RequestMapping(value = "/courses/{courseId}", method = RequestMethod.DELETE)
	public void destroy() {

	}
}
