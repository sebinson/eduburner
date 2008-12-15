package eduburner.course.service.impl;

import org.springframework.stereotype.Service;

import eduburner.core.service.BaseManager;
import eduburner.course.domain.CourseOffering;
import eduburner.course.service.ICourseOfferingManager;

@Service("courseManager")
public class DefaultCourseOfferingManager extends BaseManager implements ICourseOfferingManager {

	@Override
	public void addCourse(CourseOffering course) {
		
	}

}
