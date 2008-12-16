package eduburner.feed.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import eduburner.core.EntityObject;
import eduburner.user.domain.UserData;

/**
 * @author zhangyf@gmail.com
 * 
 * 可能来自crawler,也可能来自用户提交的内容
 */
@Entity
@Table(name = "entry")
public class Entry extends EntityObject {

	private static final long serialVersionUID = -1106338683739123478L;
	
	private String title;
    private String link;
    private Date published;
    private Date updated;
    private UserData user;
    private Service service;
    private List<Comment> comments;
    private List<Like> likes;
    private List<Media> media;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getLink() {
		return link;
	}



	public void setLink(String link) {
		this.link = link;
	}



	public Date getPublished() {
		return published;
	}



	public void setPublished(Date published) {
		this.published = published;
	}



	public Date getUpdated() {
		return updated;
	}



	public void setUpdated(Date updated) {
		this.updated = updated;
	}



	public UserData getUser() {
		return user;
	}



	public void setUser(UserData user) {
		this.user = user;
	}



	public Service getService() {
		return service;
	}



	public void setService(Service service) {
		this.service = service;
	}



	public List<Comment> getComments() {
		return comments;
	}



	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}



	public List<Like> getLikes() {
		return likes;
	}



	public void setLikes(List<Like> likes) {
		this.likes = likes;
	}



	public List<Media> getMedia() {
		return media;
	}



	public void setMedia(List<Media> media) {
		this.media = media;
	}

}
