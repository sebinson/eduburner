package eduburner.user.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import eduburner.core.EntityObject;
import eduburner.course.domain.CourseOffering;
import eduburner.feed.domain.Feed;

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
	
	private Feed feed;
	
	//参与的课程,多对多
	private List<CourseOffering> courses = new ArrayList<CourseOffering>();

	// 加上个人头像
	private byte[] profilePicture;

	
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

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("username", username).append(
				"fullname", fullname).toString();
	}
}
