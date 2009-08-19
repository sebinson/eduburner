package eduburner.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;

import eduburner.entity.Entry;
import eduburner.entity.course.Course;
import eduburner.entity.user.User;
import eduburner.entity.user.UserData;
import eduburner.persistence.EntityExistsException;
import eduburner.service.course.ICourseManager;
import eduburner.service.user.IUserManager;
import eduburner.test.util.TestDataGenerator;

public class GenUserDataTest extends BaseServiceTestSupport{
	
	@Autowired
	@Qualifier("courseManager")
	private ICourseManager courseManager;
	
	@Autowired
	@Qualifier("userManager")
	private IUserManager userManager;


	//@Test
	public void genData() throws EntityExistsException{
	    System.out.println("entering gen data method...");
		saveDatas();
		fillAdminData();
	}
	
	private void saveDatas() throws EntityExistsException{
		User admin = userManager.getUserByUsername("admin");
		User user = userManager.getUserByUsername("rockmaple");
		
		UserData adminData = userManager.getUserData(admin);
		UserData userData = userManager.getUserData(user);
		
		List<User> users = TestDataGenerator.genUsers();
		
		for(User u : users){
			userManager.createUser(u);
		}
		
		List<Course> courses = TestDataGenerator.genCourses();
		
		for(Course c : courses){
			courseManager.createCourse(c);
			userData.addCourse(c);
			c.addMemeber(userData);
			adminData.addCourse(c);
			c.addMemeber(adminData);
			userManager.updateUserData(adminData);
			userManager.updateUserData(userData);
		}
		
		List<Entry> entries = TestDataGenerator.genEntries();
		
		
		
		for(Entry e : entries){
			userManager.createEntry(admin.getId(), e);
		    userManager.createEntry(user.getId(), e);
		}
	}
	
	private void fillAdminData(){
		User admin = userManager.getUserByUsername("admin");
		User user = userManager.getUserByUsername("rockmaple");
	}
}
