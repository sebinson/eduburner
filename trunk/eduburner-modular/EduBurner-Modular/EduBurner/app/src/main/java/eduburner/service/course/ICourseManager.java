package eduburner.service.course;

import java.util.List;

import eduburner.entity.course.Course;
import eduburner.entity.course.CourseTag;

public interface ICourseManager {
	
	public List<Course> getAllCourses();
	
	public Course getCourseById(String courseId);
	
	public void createCourse(Course course);
	
	public void updateCourse(Course course);
	
	public void removeCourse(String courseId);
	
	public CourseTag getCourseTag(String tagName);
	
	public CourseTag getOrInsertCourseTag(String tagName);

}
