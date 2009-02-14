package eduburner.user.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.GrantedAuthority;

import com.google.common.collect.Sets;

import eduburner.core.EntityObject;

@Entity
@Table(name = "role")
public class Role extends EntityObject implements GrantedAuthority {
	private static final long serialVersionUID = -908605749081541265L;

	public static final String DEFAULT_ROLE_NAME = "role_user";

	private String name;
	
	private String description;

	protected Set<User> users = Sets.newHashSet();

	@Column(name = "name", nullable = false, unique = true)
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER, mappedBy = "roles")
	public Set<User> getUsers() {
		return users;
	}

	@Override
	@Transient
	public String getAuthority() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int compareTo(Object o) {
		if(!(o instanceof Role)){
			throw new IllegalArgumentException();
		}
		Role role = (Role)o;
		if(role.getName().equals(this)){
			return 0;
		}
		return 1;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
