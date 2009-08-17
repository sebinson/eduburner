package eduburner.entity.user;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import eduburner.entity.EntityObject;

/**
 * 邀请信息
 * @author rockmaple@gmail.com
 *
 */

@Entity
@Table(name="invitation")
public class Invitation extends EntityObject {
	
	private static final long serialVersionUID = -5419101643348077726L;
	
	private String code;  //the code for the invite
	private String email; //the email this invite went to
	
	private UserData requestor;
	
	private UserData candidate;
	
	private boolean isAccepted;
	
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

	@ManyToOne
	public UserData getRequestor() {
		return requestor;
	}

	public void setRequestor(UserData requestor) {
		this.requestor = requestor;
	}

	@ManyToOne
	public UserData getCandidate() {
		return candidate;
	}

	public void setCandidate(UserData candidate) {
		this.candidate = candidate;
	}

	public boolean isAccepted() {
		return isAccepted;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	@Override
	public String toString() {
		return "code: " + code;
	}
}
