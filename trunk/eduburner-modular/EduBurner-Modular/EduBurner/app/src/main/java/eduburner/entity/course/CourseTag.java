package eduburner.entity.course;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;

import eduburner.entity.EntityObject;
import eduburner.json.JsonHelper;

@Entity
@Table(name = "course_tag")
public class CourseTag extends EntityObject {

	private static final long serialVersionUID = -4435877401860345611L;
	private String name;
	private List<Course> courses = Lists.newArrayList();
	
	public CourseTag(){}
	
	public CourseTag(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "rel_course_coursetag", joinColumns = { @JoinColumn(name = "tag_id") }, inverseJoinColumns = { @JoinColumn(name = "course_id") })
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return JsonHelper.toJsonMap("name", name);
	}

}
