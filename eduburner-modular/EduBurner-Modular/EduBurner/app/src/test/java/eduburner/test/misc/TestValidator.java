package eduburner.test.misc;

import junit.framework.TestCase;

import org.hibernate.validator.InvalidValue;

import eduburner.entity.user.User;
import eduburner.validation.AnnotationValidator;

public class TestValidator extends TestCase{
	
	public void testValidator(){
		User user = new User();
		user.setUsername("a");
		user.setPassword(null);
		InvalidValue[] invalidValues = AnnotationValidator.UserValidator.getInvalidValues(user);
		System.out.println("iv length: " + invalidValues.length);
		for(InvalidValue iv : invalidValues){
			System.out.println(iv.getMessage());
		}
	}

}
