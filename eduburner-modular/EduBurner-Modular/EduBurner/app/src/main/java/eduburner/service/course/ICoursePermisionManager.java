package eduburner.service.course;

import eduburner.entity.course.Course;
import eduburner.entity.course.CoursePermission;
import eduburner.entity.user.Role;

public interface ICoursePermisionManager {
	
	public CoursePermission getCoursePermission(Role role, Course course);
	
	public void createCoursePermission(Role role, Course course, CoursePermission permission);
	
	
	
}
