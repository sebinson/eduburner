package eduburner.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.annotations.Test;

import eduburner.entity.user.Role;
import eduburner.entity.user.User;
import eduburner.entity.user.UserData;
import eduburner.enumerations.RoleType;
import eduburner.persistence.EntityExistsException;
import eduburner.service.course.ICourseManager;
import eduburner.service.user.IRoleManager;
import eduburner.service.user.IUserManager;
import eduburner.test.util.TestDataGenerator;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
@TransactionConfiguration(defaultRollback = false)
public class GenUserDataTest extends AbstractTransactionalTestNGSpringContextTests{
	
	@Autowired
	@Qualifier("courseManager")
	private ICourseManager courseManager;
	
	@Autowired
	@Qualifier("userManager")
	private IUserManager userManager;
	
	@Autowired
	@Qualifier("roleManager")
	private IRoleManager roleManager;


	//@Test
	public void genData() throws EntityExistsException{
	    System.out.println("entering gen data method...");
		saveDatas();
		fillAdminData();
	}
	
	private void saveDatas() throws EntityExistsException{
		User admin = userManager.getUserByUsername("admin");
		User user = userManager.getUserByUsername("rockmaple");
		Role role = roleManager.getRoleByName(RoleType.User.toString());
		UserData adminData = userManager.getUserData(admin);
		UserData userData = userManager.getUserData(user);
		
		List<User> users = TestDataGenerator.genUsers();
		
		for(User u : users){
			createSingleUser(u);
		}
		
		/*List<Course> courses = TestDataGenerator.genCourses();
		
		for(Course c : courses){
			courseManager.createCourse(c);
			userData.addCourse(c);
			c.addMemeber(userData);
			adminData.addCourse(c);
			c.addMemeber(adminData);
			userManager.updateUserData(adminData);
			userManager.updateUserData(userData);
		}
		
		List<Entry> entries = TestDataGenerator.genEntries();*/
		
		
	}
	
	private void createSingleUser(User user) throws EntityExistsException{
		//TODO: 把addRole放到这句话前面会出问题(user.addRole)，why?
		userManager.createUser(user);
		Role roleByName = roleManager.getRoleByName(RoleType.User.toString());
		user.addRole(roleByName);
		UserData ud = userManager.createUserData(user);
		ud.setProfilePicture("/static/profiles/anonymous.gif");
		userManager.updateUserData(ud);
	}
	
	private void fillAdminData(){
		User admin = userManager.getUserByUsername("admin");
		User user = userManager.getUserByUsername("rockmaple");
	}
}
