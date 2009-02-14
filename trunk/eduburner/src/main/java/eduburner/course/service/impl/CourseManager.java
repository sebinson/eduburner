package eduburner.course.service.impl;

import org.springframework.stereotype.Service;

import eduburner.core.service.BaseManager;
import eduburner.course.domain.CourseOffering;
import eduburner.course.service.ICourseManager;

@Service("courseManager")
public class CourseManager extends BaseManager implements ICourseManager {

	@Override
	public void createCourse(CourseOffering course) {
		
	}

	@Override
	public CourseOffering getCourse(long id) {
		return dao.getInstanceById(CourseOffering.class, id);
	}

}
