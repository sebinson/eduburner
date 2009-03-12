package eduburner.entity.user;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import eduburner.entity.EntityObject;

/**
 * user log
 * 
 * @author zhangyf@gmail.com
 * 
 */

@Entity
@Table(name = "user_log")
public class UserLog extends EntityObject {

	private static final long serialVersionUID = -8282684097913298667L;

	private long userId;
	private String action;
	private Date actionDate;

	@Override
	public String toString() {
		return userId + " action: " + action;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

}
