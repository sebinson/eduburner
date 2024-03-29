package burnerweb.model.course;

import burnerweb.model.EntityObject;

import javax.persistence.*;

/**
 * 课程资源，上传的文档之类
 * 
 * @author zhangyf@gmail.com
 */

@Entity
@Table(name = "course_resource")
public class CourseResource extends EntityObject {

	private static final long serialVersionUID = -7991836659179446511L;

	private String name;

	private Course course;
	
	private long fileSize;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_course_id")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
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
		return "{name:" + name + "}";
	}
}
