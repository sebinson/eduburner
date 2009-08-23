package eduburner.web.controller.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import eduburner.entity.course.Course;
import eduburner.entity.user.UserData;
import eduburner.enumerations.Message;
import eduburner.service.course.ICourseManager;
import eduburner.validation.CourseValidator;
import eduburner.web.controller.BaseController;

@Controller
public class CourseController extends BaseController {

	@Autowired
	@Qualifier("courseManager")
	private ICourseManager courseManager;

	private static final String COURSE_FORM = "fragments/course-form";
	private static final String COURSE_LIST = "course-list";
	private static final String COURSE_VIEW = "course";

	@RequestMapping(value = "/courses/", method = RequestMethod.GET)
	public String list(Model model) {
		List<Course> courses = courseManager.getAllCourses();
		model.addAttribute("courses", courses);
		return COURSE_LIST;
	}

	@RequestMapping(value = "/courses/{courseId}")
	public String show(@PathVariable("courseId") String courseId, Model model) {
		Course course = courseManager.getCourseById(courseId);
		model.addAttribute("course", course);
		return COURSE_VIEW;
	}

	@RequestMapping(value = "/courses/new")
	public String editNew(Model model) {
		Course course = new Course();
		model.addAttribute("course", course);
		return COURSE_FORM;
	}

	@RequestMapping(value = "/courses/{courseId}/edit")
	public String edit(@PathVariable("courseId") String courseId, Model model) {
		Course course = courseManager.getCourseById(courseId);
		model.addAttribute("course", course);
		return COURSE_FORM;
	}

	@RequestMapping(value = "/courses.*", method = RequestMethod.POST)
	public String create(@ModelAttribute("course") Course course,
			BindingResult br, Model model) {
		logger.debug("entering create method...");
		// TODO: check permission
		new CourseValidator().validate(course, br);
		if (br.hasErrors()) {
			setReturnMsg(model, Message.ERROR);
			return JSON_VIEW;
		} else {
			logger.debug("begin to create course");
			UserData userData = getRemoteUserDataObj();
			courseManager.createCourse(course, userData, true);
			setReturnMsg(model, Message.OK);
			return JSON_VIEW;
		}
	}

	@RequestMapping(value = "/courses/", method = RequestMethod.PUT)
	public String update(@ModelAttribute Course course, Model model) {
		courseManager.updateCourse(course);
		setReturnMsg(model, Message.OK);
		return JSON_VIEW;
	}

	@RequestMapping(value = "/courses/{courseId}", method = RequestMethod.DELETE)
	public String destroy(@PathVariable("courseId") String courseId, Model model) {
		courseManager.removeCourse(courseId);
		setReturnMsg(model, Message.OK);
		return JSON_VIEW;
	}

	@RequestMapping(value = "/courses/{courseId}/users", method = RequestMethod.POST)
	public String addMember(@PathVariable("courseId") String courseId,
			@RequestParam("username") String username, Model model) {
		UserData user = userManager.getUserDataByUsername(username);
		Course course = courseManager.getCourseById(courseId);
		user.addCourse(course);
		courseManager.updateCourse(course);
		userManager.updateUserData(user);
		model.addAttribute("course", course);
		return JSON_VIEW;
	}

	@RequestMapping(value = "/courses/{courseId}/users/{userId}", method = RequestMethod.DELETE)
	public String removeMember(@PathVariable("courseId") String courseId,
			@PathVariable("userId") String userId, Model model) {
		UserData user = userManager.getUserDataByUserId(userId);
		Course course = courseManager.getCourseById(courseId);
		return JSON_VIEW;
	}

	@RequestMapping(value = "/courses/{courseId}/entries", method = RequestMethod.POST)
	public void createCourseEntry(@PathVariable("courseId") String courseId,
			Model model) {
		
	}

	@RequestMapping(value = "/courses/{courseId}/entries")
	public void courseEntries(@PathVariable("courseId") String courseId,
			Model model) {

	}

}
