package eduburner.validation;

import org.hibernate.validator.InvalidValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eduburner.entity.course.Course;

public class CourseValidator implements Validator {
	
	private static final Logger logger = LoggerFactory.getLogger(CourseValidator.class);

	@SuppressWarnings("unchecked")
	@Override
	public boolean supports(Class clazz) {
		return Course.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Course course = (Course) target;
		InvalidValue[] invalids = AnnotationValidator.CourseValidator
				.getInvalidValues(course);
		for (InvalidValue invalidValue : invalids) {
			String propertyPath = invalidValue.getPropertyPath();
			String message = invalidValue.getMessage();
			logger.debug("validate error, path: " + propertyPath + " message: " + message);
			errors.rejectValue(propertyPath, null, message);
		}

	}

}
