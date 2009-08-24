package eduburner.entity.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.gson.annotations.Expose;

import eduburner.entity.Comment;
import eduburner.entity.EntityObject;
import eduburner.entity.Entry;
import eduburner.entity.Like;
import eduburner.entity.course.Course;

/**
 * @author rockmaple
 */
@Entity
@Table(name = "user_data")
public class UserData extends EntityObject {
	private static final long serialVersionUID = -8000191009583356867L;
	
	public static final String PROFILE_PICTURE_PREFIX = "/static/profiles/";

	@Expose
	private String username;
	@Expose
	private String fullname;
	@Expose
	private String email;
	@Expose
	private String userId;
	
	private String description;

	// 加上个人头像
	private String profilePicture = PROFILE_PICTURE_PREFIX + "anonymous.gif";
	
	private List<Entry> entries = Lists.newArrayList();

	private List<Course> courses = Lists.newArrayList();

	// comments user made
	private List<Comment> comments = Lists.newArrayList();

	private List<Invitation> incomingInvitations = Lists.newArrayList();
	private List<Invitation> outgoingInvitations = Lists.newArrayList();

	private List<Like> likes = Lists.newArrayList();
	
	private List<UserData> friends = Lists.newArrayList();

	public UserData() {
	}

	public UserData(User user) {
		username = user.getUsername();
		fullname = user.getFullname();
		userId = user.getId();
		email = user.getEmail();
	}

	public void addCourse(final Course course) {
		boolean containCourse = Iterables.any(this.courses,
				new Predicate<Course>() {
					@Override
					public boolean apply(Course input) {
						return input.getId().equals(course.getId());
					}
				});
		if (!containCourse) {
			courses.add(course);
		}
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@OneToMany(mappedBy="user")
	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "rel_user_course", joinColumns = { @JoinColumn(name = "member_id") }, inverseJoinColumns = { @JoinColumn(name = "course_id") })
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	@OneToMany(mappedBy = "candidate", cascade = { CascadeType.PERSIST }, fetch = FetchType.LAZY)
	public List<Invitation> getIncomingInvitations() {
		return incomingInvitations;
	}

	public void setIncomingInvitations(List<Invitation> incomingInvitations) {
		this.incomingInvitations = incomingInvitations;
	}

	@OneToMany(mappedBy = "requestor", cascade = { CascadeType.PERSIST })
	public List<Invitation> getOutgoingInvitations() {
		return outgoingInvitations;
	}

	public void setOutgoingInvitations(List<Invitation> outgoingInvitations) {
		this.outgoingInvitations = outgoingInvitations;
	}

	@OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST })
	public List<Like> getLikes() {
		return likes;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rel_friends", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "friend_id") })
	public List<UserData> getFriends() {
		return friends;
	}

	public void setFriends(List<UserData> friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("username", username).append("fullname", fullname)
				.toString();
	}
}
