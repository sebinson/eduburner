package eduburner.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eduburner.entity.user.UserData;

@Entity
@Table(name="user_like_entry")
public class Like extends EntityObject{

	private static final long serialVersionUID = 802007405441401721L;

	private UserData user;
	private Entry entry;

	@ManyToOne
	@JoinColumn(name="fk_userdata_id")
	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	@ManyToOne
	@JoinColumn(name="fk_entry_id")
	public Entry getEntry() {
		return entry;
	}

	public void setEntry(Entry entry) {
		this.entry = entry;
	}

	@Override
	public String toString() {
		return id;
	}

}
