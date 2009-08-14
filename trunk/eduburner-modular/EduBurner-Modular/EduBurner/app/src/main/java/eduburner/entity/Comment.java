package eduburner.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eduburner.entity.user.UserData;

@Entity
@Table(name="comment")
public class Comment extends EntityObject {
	
	private static final long serialVersionUID = 6416275923661542381L;
	private Date date;
	private String commentId;
	private String body;
	//author
	private UserData user;
	//entry comment belongs to
	private Entry  entry;
	
	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public Date getDate() {
		return date;
	}

	public UserData getUser() {
		return user;
	}

	public String getBody() {
		return body;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_user_id")
	public void setUser(UserData user) {
		this.user = user;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public String toString() {
		return "Comment{" + "date=" + date + ", id='" + id + '\'' + ", user="
				+ user + ", body='" + body + '\'' + '}';
	}

}
