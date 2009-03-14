package eduburner.service.course;

import org.springframework.stereotype.Component;

import eduburner.entity.course.Course;
import eduburner.service.BaseManager;

@Component("courseManager")
public class CourseManager extends BaseManager implements ICourseManager
{
	@Override
	public void createCourse(Course course) {
		dao.save(course);
	}

	@Override
	public Course getCourseById(long courseId) {
		return dao.getInstanceById(Course.class, new Long(courseId));
	}

	@Override
	public void updateCourse(Course course) {
		dao.update(course);
	}

	@Override
	public void removeCourse(long courseId) {
		dao.remove(getCourseById(courseId));
	}
	
}
