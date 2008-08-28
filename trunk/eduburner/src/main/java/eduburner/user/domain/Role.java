package eduburner.user.domain;

import org.springframework.security.GrantedAuthority;

public class Role implements GrantedAuthority {

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

}
