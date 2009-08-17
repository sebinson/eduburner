package eduburner.test.service;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.testng.Assert;
import org.testng.annotations.Test;

import eduburner.entity.user.Role;
import eduburner.entity.user.User;
import eduburner.enumerations.RoleType;
import eduburner.persistence.EntityExistsException;
import eduburner.service.user.IRoleManager;
import eduburner.service.user.IUserManager;

public class UserServiceTest extends BaseServiceTestSupport {
	private static final Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

	@Autowired
	@Qualifier("userManager")
	private IUserManager userManager;

	@Autowired
	@Qualifier("roleManager")
	private IRoleManager roleManager;

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	public UserServiceTest() {
		// make sure the change will affect database
		// setDefaultRollback(false);
	}

	@Test
	public void testCreateUser() {
		User user = new User();
		user.setUsername("user");
		user.setPassword("password");
		Role roleByName = roleManager.getRoleByName(RoleType.User.toString());
		user.addRole(roleByName);
		try {
			userManager.createUser(user);
		} catch (Exception e) {
			Assert.fail("创建用户失败", e);
		}
		
		Role role = roleManager.getRoleByName(RoleType.User.toString());
		for(User usr : role.getUsers()){
			logger.debug("users for role is: " + usr.getUsername());
		}
	}

	@Test
	public void testTreatingAUserCausesADatabaseRowToBeInserted() {
		
		logger.debug("entering testTreatingAUserCausesADatabaseRowToBeInserted method...");
		
		int before = getCount("SELECT COUNT(*) FROM user;");

		if (logger.isDebugEnabled()) {
			logger.debug("before create, user number is: " + before);
		}

		User user = createDefaultUser();

		int after = getCount("SELECT COUNT(*) FROM user;");

		logger.debug("after create user number is: " + after);

		Assert.assertNotNull(user);
		System.out.println("user name is: " + user.getUsername());
		
		//测试里的改动不会影响数据库，只在内存里玩?
		//Assert.assertEquals(after - before, 1);

		User user1 = userManager.getUserByUsername("niko2416");
		Set<Role> roles = user1.getRoles();
		Assert.assertNotNull(roles);
		Assert.assertEquals(1, roles.size());
		for (Role role : roles) {
			logger.debug("role name is: " + role.getName());
		}
	}

	// just show how to change test order
	@Test(dependsOnMethods = { "testTreatingAUserCausesADatabaseRowToBeInserted" }, alwaysRun = true)
	public void testGetUser() {
		User user = userManager.getUserById("5cb64b881e5438ba9a76ca60fc05aea4");
		if (logger.isDebugEnabled()) {
			logger.debug("user's password is: " + user.getPassword());
		}
		for (GrantedAuthority au : user.getAuthorities()) {
			logger.debug("auth is: " + au.getAuthority());
		}
		Assert.assertNotNull(user.getAuthorities());
		Assert.assertEquals(1, user.getAuthorities().length);
	}

	@Test
	public void testGetRole() {
		int before = getCount("SELECT COUNT(*) FROM role;");
		Role role = new Role();
		role.setName("rolename");
		roleManager.saveRole(role);
		int after = getCount("SELECT COUNT(*) FROM role;");
		int here = getCount("SELECT COUNT(*) FROM rel_user_role;");

		if (logger.isDebugEnabled()) {
			logger.debug("before is: " + before);
			logger.debug("after is: " + after);
			logger.debug("here is: " + here);
		}

		// assertEquals(1, after - before);

		role = roleManager.getRoleByName("rolename");

		Assert.assertNotNull(role);
	}

	protected User createUser(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encodePassword(password, null));
		Role role = roleManager.getRoleByName(RoleType.User.toString());
		user.addRole(role);
		try {
			userManager.createUser(user);
		} catch (EntityExistsException e) {
			Assert.fail("failed to create user: " +username);
			e.printStackTrace();
		}
		return user;
	}

	protected User createDefaultUser() {
		return createUser("niko2416", "password");
	}

	protected User createDefaultSecondUser() {
		return createUser("travhoang", "admin");
	}

	protected User createDefaultSpecialUser() {
		return createUser("test", "password");
	}
}
