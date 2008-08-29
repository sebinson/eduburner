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

import eduburner.user.domain.Role;
import eduburner.user.domain.User;
import eduburner.user.service.IRoleManager;
import eduburner.user.service.IUserManager;

public class UserServiceTest extends BaseServiceTestSupport {
	private Logger logger = LoggerFactory.getLogger(UserServiceTest.class);

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
		user.addRole(roleManager.getRoleByName(Role.DEFAULT_ROLE_NAME));
		try {
			userManager.createUser(user);
		} catch (Exception e) {
			Assert.fail("创建用户失败");
			e.printStackTrace();
		}
	}

	@Test
	public void testTreatingAUserCausesADatabaseRowToBeInserted() {
		if (logger.isDebugEnabled()) {
			logger.debug("entering test method...");
		}
		int before = getCount("SELECT COUNT(*) FROM user;");

		if (logger.isDebugEnabled()) {
			logger.debug("before create, user number is: " + before);
		}

		User user = createDefaultUser();

		int after = getCount("SELECT COUNT(*) FROM user;");

		if (logger.isDebugEnabled()) {
			logger.debug("after create user number is: " + after);
		}

		Assert.assertNotNull(user);
		System.out.println("user name is: " + user.getUsername());
		Assert.assertEquals(after - before, 1);

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
		User user = userManager.getUserById(1);
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
		int here = getCount("SELECT COUNT(*) FROM user_role;");

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
		Role role = roleManager.getRoleByName(Role.DEFAULT_ROLE_NAME);
		user.addRole(role);
		userManager.updateUser(user);
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
