package eduburner.course.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import eduburner.core.EntityObject;

/**
 * 课程资源，上传的文档之类
 * @author zhangyf@gmail.com
 */

@Entity
@Table(name="course_resource")
public class CourseResource extends EntityObject {

	private static final long serialVersionUID = -7991836659179446511L;
	
	private String name;

	private CourseOffering course;

	public CourseOffering getCourse() {
		return course;
	}

	public void setCourse(CourseOffering course) {
		this.course = course;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}