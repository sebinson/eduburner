package eduburner.web.controller.course;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eduburner.entity.course.Course;
import eduburner.enumerations.Message;
import eduburner.json.JsonHelper;
import eduburner.propertyeditor.CourseTagsPropertyEditor;
import eduburner.service.course.ICourseManager;
import eduburner.validation.CourseValidator;
import eduburner.web.controller.BaseController;

@Controller
public class CourseController extends BaseController {

	@Autowired
	@Qualifier("courseManager")
	private ICourseManager courseManager;

	private static String COURSE_FORM = "fragments/course-form";
	private static String COURSE_LIST = "fragments/course-list";

	@RequestMapping(value = "/courses/")
	public String index() {
		List<Course> courses = courseManager.getAllCourses();
		return null;
	}

	@RequestMapping(value = "/courses/{courseId}")
	public String show(@PathVariable("courseId") long courseId) {
		return null;
	}

	@RequestMapping(value = "/courses/new")
	public String editNew(Model model) {
		Course course = new Course();
		model.addAttribute("course", course);
		return COURSE_FORM;
	}

	@RequestMapping(value = "/courses/{courseId}/edit")
	public String edit(@PathVariable("courseId") long courseId, Model model) {
		Course course = courseManager.getCourseById(courseId);
		model.addAttribute("course", course);
		return COURSE_FORM;
	}

	@RequestMapping(value = "/courses", method = RequestMethod.POST)
	public void create(@ModelAttribute("course") Course course, BindingResult br, Model model, HttpServletResponse response) {
		//TODO: check permission
		new CourseValidator().validate(course, br);
		if(br.hasErrors()){
			model.addAttribute("msg", Message.ERROR);
			renderJson(response, model);
		}else{
			courseManager.createCourse(course);
			renderMsg(response, Message.OK);
		}
	}

	@RequestMapping(value = "/courses/{courseId}", method = RequestMethod.PUT)
	public void update() {

	}

	@RequestMapping(value = "/courses/{courseId}", method = RequestMethod.DELETE)
	public void destroy() {

	}

	@RequestMapping(value = "/courses/{courseId}/entries", method = RequestMethod.POST)
	public void createCourseEntry(@PathVariable("courseId") long courseId,
			Model model) {

	}

	@RequestMapping(value = "/courses/{courseId}/entries")
	public void courseEntries(@PathVariable("courseId") long courseId,
			Model model) {

	}

	@Override
	protected void doInitBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.registerCustomEditor(List.class, "tags",
				new CourseTagsPropertyEditor());
	}
}
