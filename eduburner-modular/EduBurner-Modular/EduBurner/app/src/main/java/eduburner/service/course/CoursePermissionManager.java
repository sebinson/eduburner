package eduburner.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import eduburner.entity.course.Course;
import eduburner.entity.course.CoursePermission;
import eduburner.entity.user.Role;
import eduburner.entity.user.User;
import eduburner.enumerations.PermissionType;
import eduburner.persistence.IDao;

public class CoursePermissionManager implements ICoursePermisionManager{
	
	@Autowired
	@Qualifier("dao")
	private IDao dao;

	@Override
	public void createCoursePermission(Role role, Course course,
			CoursePermission permission) {
	}

	@Override
	public CoursePermission getCoursePermission(Role role, Course course) {
		
		dao.find("FROM CoursePermission WHERE role = ? AND course = ?", role, course);
		
		return null;
	}

	@Override
	public boolean hasPermission(User user, Course course, PermissionType permission) {
		// TODO Auto-generated method stub
		return false;
	}

}
