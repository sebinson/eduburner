package eduburner.test.util;

import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;

import eduburner.entity.Entry;
import eduburner.entity.course.Course;
import eduburner.entity.user.User;

public class TestDataGenerator {

	public static List<User> genUsers(){
		List<User> users = Lists.newArrayList();
		for(int i=0; i<10; i++){
			User user = genUser("user" + i, "536c0b339345616c1b33caf454454d8b8a190d6c", "useremail" + i + "@test.com", "userfullname" + i);
			users.add(user);
		}
		return users;
	}
	
	public static User genUser(String username, String password, String email, String fullname){
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setFullname(fullname);
		return user;
	}
	
	public static List<Course> genCourses(){
		List<Course> courses = Lists.newArrayList();
		for(int i=0; i<10; i++){
			Course c = genCourse("course" +i);
			courses.add(c);
		}
		return courses;
	}
	
	public static Course genCourse(String coursename){
		Course c = new Course();
		c.setStartDate(new Date());
		c.setEndDate(new Date());
		c.setTitle(coursename);
		return c;
	}
	
	public static List<Entry> genEntries(){
		List<Entry> entries = Lists.newArrayList();
		for(int i=0; i<100; i++){
			Entry e = genEntry("entry content " + i);
			entries.add(e);
		}
		return entries;
	}
	
	public static Entry genEntry(String content){
		Entry e = new Entry();
		e.setTitle(content);
		return e;
	}
	
}
