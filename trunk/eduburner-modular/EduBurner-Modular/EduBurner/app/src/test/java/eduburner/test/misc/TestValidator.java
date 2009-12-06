package eduburner.test.misc;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;

import eduburner.entity.user.User;
import eduburner.test.service.BaseServiceTestSupport;

public class TestValidator extends BaseServiceTestSupport{
	
	@Autowired
	@Qualifier("validator")
	private Validator validator;

	
	@Test
	public void testValidator(){
		User user = new User();
		user.setUsername("a");
		user.setPassword(null);
		
		Set<ConstraintViolation<User>> violations = validator.validate(user);
		System.out.println("size: " + violations.size());
	}

}
