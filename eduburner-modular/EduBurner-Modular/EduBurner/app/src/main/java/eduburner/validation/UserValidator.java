package eduburner.validation;

import org.hibernate.validator.InvalidValue;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eduburner.entity.user.User;

@Component("userValidator")
public class UserValidator implements Validator {

	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		User user = (User) target;

		InvalidValue[] invalids = AnnotationValidator.UserValidator.getInvalidValues(user);

		for (InvalidValue invalidValue : invalids) {
			errors.rejectValue(invalidValue.getPropertyPath(), null,
					invalidValue.getMessage());
		}
	}

}
