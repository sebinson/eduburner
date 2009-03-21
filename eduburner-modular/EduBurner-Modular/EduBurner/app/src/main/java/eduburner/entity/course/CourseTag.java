package eduburner.entity.course;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import eduburner.entity.EntityObject;
import eduburner.util.JsonUtils;

public class CourseTag extends EntityObject {
	
	private String name;
	private Course course;

	public String getName() {
		return name;
	}

	public Course getCourse() {
		return course;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return JsonUtils.toJsonMap("name", name);
	}
}
