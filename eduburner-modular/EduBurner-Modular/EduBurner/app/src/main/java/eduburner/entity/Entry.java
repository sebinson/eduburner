package eduburner.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;

import eduburner.entity.course.Course;
import eduburner.entity.user.UserData;

@Entity
@Table(name="entry")
public class Entry extends EntityObject {
	private static final long serialVersionUID = -1018771380257973544L;
	
	private String entryId;
	private String title;
	private String link;
	private Date published;
	private Date updated;
	private boolean anonymous;
	private boolean hidden;
	
	private UserData user;
	private Course course;
	private Service service;
	private List<Comment> comments = Lists.newArrayList();

	public String getEntryId() {
		return entryId;
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public Date getPublished() {
		return published;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public boolean isHidden() {
		return hidden;
	}

	@ManyToOne
	@JoinColumn(name="fk_service_id")
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}


	@OneToMany(mappedBy="entry", fetch=FetchType.LAZY)
	public List<Comment> getComments() {
		if (comments == null) {
			comments = new ArrayList<Comment>();
		}
		return comments;
	}

	@ManyToOne
	@JoinColumn(name="fk_course_id")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setEntryId(String id) {
		this.entryId = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
	
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	public String toString() {
		return "Entry{" + "id='" + entryId + '\'' + ", title='" + title + '\''
				+ ", link='" + link + '\'' + ", published=" + published
				+ ", updated=" + updated + ", hidden=" + hidden + ", user="
				+ user + '}';
	}

}
