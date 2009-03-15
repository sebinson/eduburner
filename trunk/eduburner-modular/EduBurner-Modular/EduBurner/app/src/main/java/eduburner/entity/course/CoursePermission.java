package eduburner.entity.course;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import eduburner.entity.user.PermissionBase;

@Entity
@DiscriminatorValue("eduburner.entity.course.CoursePermission")
public class CoursePermission extends PermissionBase {

	private static final long serialVersionUID = -7084640553967090278L;

	private Course course;

	@ManyToOne
	@JoinColumn(name = "fk_course_id")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
