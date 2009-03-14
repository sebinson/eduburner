package eduburner.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;
import org.testng.annotations.Test;

import eduburner.entity.course.Course;
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
		course.setDescription("desc");
		courseManager.createCourse(course);
		
		User user = userManager.getUserByUsername("rockmaple");
		UserData ud = userManager.getUserData(user.getId());
		course.addMemeber(ud);
		
		courseManager.updateCourse(course);
		
		int count = getCount("SELECT count(*) FROM course");
		
		int udCount = getCount("SELECT count(*) FROM user_data");
		
		Assert.assertEquals(1, count);
		Assert.assertEquals(1, udCount);
	}
	
}
