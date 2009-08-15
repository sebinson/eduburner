package eduburner.service.course;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import eduburner.entity.course.Course;
import eduburner.entity.course.CourseTag;
import eduburner.service.BaseManager;

@Component("courseManager")
@Transactional
public class CourseManager extends BaseManager implements ICourseManager {
	
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

	@Override
	public List<Course> getAllCourses() {
		return dao.getAllInstances(Course.class);
	}

	@Override
	public CourseTag getCourseTag(String tagName) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CourseTag getOrInsertCourseTag(String tagName) {
		List tags = dao.find("FROM CourseTag WHERE name= ?", tagName);
		if(tags.size() > 0){
			return (CourseTag)tags.get(0);
		}else{
			CourseTag ct = new CourseTag(tagName);
			dao.save(ct);
			return ct;
		}
	}

}
