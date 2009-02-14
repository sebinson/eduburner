package eduburner.entry.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.google.common.collect.Lists;

import eduburner.core.EntityObject;
import eduburner.user.domain.UserData;

@Entity
@Table(name = "entry")
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
	private Service service;
	private List<Comment> comments = Lists.newArrayList();
	private List<Like> likes = Lists.newArrayList();
	private List<Media> media = Lists.newArrayList();
	private Via via;

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

	
	public Service getService() {
		return service;
	}

	public List<Comment> getComments() {
		if (comments == null) {
			comments = new ArrayList<Comment>();
		}
		return comments;
	}

	public List<Like> getLikes() {
		if (likes == null) {
			likes = new ArrayList<Like>();
		}
		return likes;
	}

	public List<Media> getMedia() {
		if (media == null) {
			media = new ArrayList<Media>();
		}
		return media;
	}

	public Via getVia() {
		return via;
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

	public void setService(Service service) {
		this.service = service;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}

	public void setMedia(List<Media> media) {
		this.media = media;
	}

	public void setVia(Via via) {
		this.via = via;
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
				+ user + ", service=" + service + '}';
	}

}
