package eduburner.user.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import eduburner.core.EntityObject;

/**
 * user log
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

}
