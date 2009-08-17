package eduburner.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;

import eduburner.entity.course.Course;
import eduburner.entity.course.CourseTag;
import eduburner.entity.user.User;
import eduburner.entity.user.UserData;
import eduburner.service.course.ICourseManager;
import eduburner.service.user.IUserManager;

public class CourseServiceTest extends BaseServiceTestSupport {
	
	@Autowired
	@Qualifier("courseManager")
	private ICourseManager courseManager;
	
	@Autowired
	@Qualifier("userManager")
	private IUserManager userManager;
	
	@Test
	public void testCreateCourse(){
		
		Course course = new Course();
		course.setTitle("new course");
		course.setDescription("desc");
		
		User user = userManager.getUserByUsername("rockmaple");
		UserData ud = userManager.getUserData(user);
		ud.addCourse(course);
		
		courseManager.createCourse(course);
		userManager.updateUserDate(ud);
		
		List<Course> courses = courseManager.getAllCourses();
		
		logger.debug("courses length: " + courses.size());
		
		int count = getCount("SELECT count(*) FROM course");
		int udCount = getCount("SELECT count(*) FROM user_data");
		
	}
	
	@Test
	public void testCourseTag(){
		CourseTag ct = courseManager.getOrInsertCourseTag("tagname");
		Assert.assertNotNull(ct.getId());
	}
}
