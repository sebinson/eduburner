package eduburner.entity.course;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import eduburner.entity.user.PermissionBase;
import eduburner.entity.user.Role;
import eduburner.enumerations.PermissionType;

@Entity
@DiscriminatorValue("eduburner.entity.course.CoursePermission")
public class CoursePermission extends PermissionBase {

	private static final long serialVersionUID = -7084640553967090278L;

	private Course course;
	
	public CoursePermission(){
		super();
	}
	
	public CoursePermission(Course course, Role role){
		super(role);
		this.course = course;
	}
	
	public CoursePermission(Course course, Role role, PermissionType allowMask, PermissionType denyMask, boolean implied){
		super(role, allowMask, denyMask, implied);
		this.course = course;
	}
	
	public boolean canView(){
		return getBit(PermissionType.VIEW);
	}
	
	public boolean canEdit(){
		return getBit(PermissionType.EDIT);
	}
	
	public boolean canSetPermissions(){
		return getBit(PermissionType.SET_PERMISSIONS);
	}

	@ManyToOne
	@JoinColumn(name = "fk_course_id")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
