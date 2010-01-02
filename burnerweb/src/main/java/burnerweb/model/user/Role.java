package burnerweb.model.user;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import burnerweb.model.EntityObject;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;


@Entity
@Table(name = "role")
public class Role extends EntityObject implements GrantedAuthority {
	private static final long serialVersionUID = -908605749081541265L;

	private String name;
	private String description;

	protected Set<User> users = Sets.newHashSet();

    @Transient
    @Override
    public String getAuthority() {
        return name;
    }

	@Column(name = "name", nullable = false, unique = true)
	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
	public Set<User> getUsers() {
		return users;
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

	@Transient
	public String getUsersString() {
		Iterable<String> iter = Iterables.transform(getUsers(),
				new Function<User, String>() {
					@Override
					public String apply(User from) {
						return from.getUsername();
					}
				});

		return Joiner.on(",").join(iter);
	}

	@Override
	public String toString() {
		return this.name;
	}


}
