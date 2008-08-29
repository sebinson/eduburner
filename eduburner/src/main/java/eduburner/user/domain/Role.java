package eduburner.user.domain;

import org.springframework.security.GrantedAuthority;

import eduburner.core.EntityObject;

public class Role extends EntityObject implements GrantedAuthority {

	private static final long serialVersionUID = -908605749081541265L;
	
	private String name;

	@Override
	public String getAuthority() {
		return this.name;
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public String toString() {
		return this.name;
	}

}
