package eduburner.entity.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.common.collect.Lists;

import eduburner.entity.EntityObject;
import eduburner.entity.course.Course;

/**
 * @author rockmaple
 */
@Entity
@Table(name = "user_data")
public class UserData extends EntityObject {
	private static final long serialVersionUID = -8000191009583356867L;

	private String username;

	private String fullname;

	private String email;

	private long userId;

	// 加上个人头像
	private byte[] profilePicture;
	
	private List<Course> courses = Lists.newArrayList();

	public UserData() {
	}

	public UserData(User user) {
		username = user.getUsername();
		fullname = user.getFullname();
		userId = user.getId();
		email = user.getEmail();
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@Lob
	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "user_course", joinColumns = { @JoinColumn(name = "member_id") }, inverseJoinColumns = { @JoinColumn(name = "course_id") })
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("username", username).append(
				"fullname", fullname).toString();
	}
}
