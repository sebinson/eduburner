package eduburner.test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.Assert;

import eduburner.entity.course.Course;
import eduburner.entity.course.CourseResource;
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
	
	//@Test
	public void testCreateCourse(){
		
		Course course = new Course();
		course.setTitle("new course");
		course.setDescription("desc");
		course.setTagsAsString("tag1,tag2");
		
		User user = userManager.getUserByUsername("rockmaple");
		UserData ud = userManager.getUserData(user);
		
		courseManager.createCourse(course, ud, true);
		
		Course course1 = courseManager.getCourseById(course.getId());
		
		Assert.assertEquals(course1.getTags().size(), 2);
		Assert.assertEquals(course1.getCreator().getUsername(), "rockmaple");
		Assert.assertEquals(course1.getMembers().size(), 1);
		Assert.assertEquals(ud.getCourses().size(), 1);
		
	}
	
	//测试一下一对多情况下的级联
	//@Test
	public void testCourseResource(){
		Course c = new Course();
		c.setTitle("new course");
		c.setDescription("desc");
		
		CourseResource cr = new CourseResource();
		cr.setName("adaf");
		cr.setCourse(c);
		
		c.getCourseResources().add(cr);
		
		Assert.assertNull(cr.getId());
		
		courseManager.createCourse(c);
		
		Assert.assertNotNull(cr.getId());
		
		logger.debug("course resource id is: " + cr.getId());
	}
	
	//@Test
	public void testCourseTag(){
		CourseTag ct = courseManager.getOrInsertCourseTag("tagname");
		Assert.assertNotNull(ct.getId());
	}
}
