package eduburner.service.course;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import eduburner.entity.course.Course;
import eduburner.entity.course.CourseTag;
import eduburner.entity.user.UserData;
import eduburner.persistence.IDao;

@SuppressWarnings("unchecked")
@Component("courseManager")
@Transactional
public class CourseManager implements ICourseManager {
	
	@Autowired
	@Qualifier("dao")
	private IDao dao;

	@Override
	public void createCourse(Course course) {
		createCourse(course, null, false);
	}

	@Override
	public void createCourse(Course course, boolean updateTagsString) {
		createCourse(course, null, updateTagsString);
	}

	@Override
	public void createCourse(Course course, UserData creator,
			boolean updateTagsString) {

		if (updateTagsString) {
			updateCourseTagsForTagsString(course);
		}

		if (creator != null) {
			course.setCreator(creator);
			course.addMemeber(creator);
			creator.addCourse(course);
		}

		dao.save(course);

	}

	@Override
	public Course getCourseById(String courseId) {
		return dao.getInstanceById(Course.class, courseId);
	}

	@Override
	public void updateCourse(Course course) {
		dao.update(course);
	}

	@Override
	public void removeCourse(String courseId) {
		dao.remove(getCourseById(courseId));
	}

	@Override
	public List<Course> getAllCourses() {
		return dao.getAllInstances(Course.class);
	}

	@Override
	public CourseTag getCourseTag(String tagName) {
		List tags = dao.find("FROM CourseTag WHERE name= ?", tagName);
		if (tags.size() > 0) {
			return (CourseTag) tags.get(0);
		} else {
			return null;
		}
	}

	@Override
	public CourseTag getOrInsertCourseTag(String tagName) {
		List tags = dao.find("FROM CourseTag WHERE name= ?", tagName);
		if (tags.size() > 0) {
			return (CourseTag) tags.get(0);
		} else {
			CourseTag ct = new CourseTag(tagName);
			dao.save(ct);
			return ct;
		}
	}

	@Override
	public void updateCourseTagsForTagsString(Course course) {

		List<CourseTag> tags = Lists.newArrayList();

		for (String t : course.getTagsStringList()) {
			CourseTag ct = getOrInsertCourseTag(t);
			tags.add(ct);
		}

		course.setTags(tags);
	}

}
