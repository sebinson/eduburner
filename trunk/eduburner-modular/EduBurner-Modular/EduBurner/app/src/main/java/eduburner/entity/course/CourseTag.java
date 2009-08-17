package eduburner.entity.course;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;

import eduburner.entity.EntityObject;

@Entity
@Table(name = "course_tag")
public class CourseTag extends EntityObject {

	private static final long serialVersionUID = -4435877401860345611L;
	@Expose
	private String name;
	private List<Course> courses = Lists.newArrayList();
	
	public CourseTag(){}
	
	public CourseTag(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@ManyToMany(mappedBy="tags")
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
		return "name: "+name;
	}

}
