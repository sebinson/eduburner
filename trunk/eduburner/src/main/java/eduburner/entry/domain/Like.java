package eduburner.entry.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import eduburner.core.EntityObject;
import eduburner.user.domain.UserData;

@Entity
@Table(name="like")
public class Like extends EntityObject {
	private static final long serialVersionUID = -6439803377969170768L;

	private UserData user;
	private Entry entry;
	private Date date;

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return null;
	}
}
