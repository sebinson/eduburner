package eduburner.validation;

import org.hibernate.validator.InvalidValue;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import eduburner.entity.course.Course;

public class CourseValidator implements Validator {

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
			errors.rejectValue(invalidValue.getPropertyPath(), null,
					invalidValue.getMessage());
		}

	}

}
