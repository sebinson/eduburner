package eduburner.service.course;

import eduburner.entity.course.Course;
import eduburner.entity.course.CoursePermission;
import eduburner.entity.user.Role;
import eduburner.entity.user.User;
import eduburner.enumerations.PermissionType;

public interface ICoursePermisionManager {
	
	public boolean hasPermission(User user, Course course, PermissionType permission);
	
	public CoursePermission getCoursePermission(Role role, Course course);
	
	public void createCoursePermission(Role role, Course course, CoursePermission permission);
	
}
