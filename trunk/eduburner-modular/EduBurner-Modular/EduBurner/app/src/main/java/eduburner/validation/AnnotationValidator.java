package eduburner.validation;

import org.hibernate.validator.ClassValidator;
import org.hibernate.validator.InvalidValue;

import eduburner.entity.EntityObject;
import eduburner.entity.course.Course;
import eduburner.entity.user.User;

@SuppressWarnings("unchecked")
public enum AnnotationValidator {
	
	UserValidator(User.class),
	CourseValidator(Course.class);
	
	private ClassValidator validator;
	
	private AnnotationValidator(Class<? extends EntityObject> clazz){
		validator = new ClassValidator(clazz);
	}
	
	public InvalidValue[] getInvalidValues(EntityObject modelObject) {
		String nullProperty = null;
		return getInvalidValues(modelObject, nullProperty);
	}
	
	public InvalidValue[] getInvalidValues(EntityObject modelObject,
			String property) {

		InvalidValue[] validationMessages;

		if (property == null) {
			validationMessages = validator.getInvalidValues(modelObject);
		} else {
			validationMessages = validator.getInvalidValues(modelObject,
					property);
		}
		return validationMessages;
	}
}
