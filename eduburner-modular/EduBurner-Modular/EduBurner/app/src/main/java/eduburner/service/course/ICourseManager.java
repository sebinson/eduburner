package eduburner.service.course;

import eduburner.entity.course.Course;

public interface ICourseManager {
	
	public Course getCourseById(long courseId);
	
	public void createCourse(Course course);
	
	public void updateCourse(Course course);
	
	public void removeCourse(long courseId);
	
}
