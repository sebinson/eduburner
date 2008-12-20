package eduburner.entry.domain;

import java.util.Date;

import eduburner.core.EntityObject;
import eduburner.user.domain.UserData;

public class Comment extends EntityObject {
	private static final long serialVersionUID = 6416275923661542381L;
	private Date date;
	private String commentId;
	private UserData user;
	private String body;

	public Comment() {
	}

	public Date getDate() {
		return date;
	}

	public String getBody() {
		return body;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	public String toString() {
		return "Comment{" + "date=" + date + ", id='" + id + '\'' + ", user="
				+ user + ", body='" + body + '\'' + '}';
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

}
