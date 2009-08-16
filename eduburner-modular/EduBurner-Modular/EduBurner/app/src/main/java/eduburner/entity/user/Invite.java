package eduburner.entity.user;

import javax.persistence.Entity;
import javax.persistence.Table;

import eduburner.entity.EntityObject;

/**
 * 邀请信息
 * @author rockmaple@gmail.com
 *
 */

@Entity
@Table(name="invite")
public class Invite extends EntityObject {
	
	private static final long serialVersionUID = -5419101643348077726L;
	
	private String code;  //the code for the invite
	private String email; //the email this invite went to
	private String fromActorId;
	private String toUserId;
	
	private Status status;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFromActorId() {
		return fromActorId;
	}

	public void setFromActorId(String fromActorId) {
		this.fromActorId = fromActorId;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "code: " + code;
	}
	
	public enum Status{
		ACTIVE, BLOCKED
	}

}
