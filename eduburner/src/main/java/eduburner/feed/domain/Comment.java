package eduburner.feed.domain;

import java.util.Date;

import eduburner.core.EntityObject;
import eduburner.user.domain.UserData;

public class Comment extends EntityObject {
	private static final long serialVersionUID = -8070149621622984770L;
	private Date date;
	private UserData user;
	private String body;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
