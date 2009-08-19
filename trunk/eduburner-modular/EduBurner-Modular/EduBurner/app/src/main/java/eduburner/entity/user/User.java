package eduburner.entity.user;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.UserDetails;

import com.google.common.collect.Sets;
import com.google.gson.annotations.Expose;

import eduburner.entity.EntityObject;
import eduburner.enumerations.RoleType;

@NamedQueries({ 
	@NamedQuery(name = "getPermissionsByUserName",
			    query = "FROM PermissionBase AS p INNER JOIN p.role AS r INNER JOIN r.users AS u WHERE u.username = :username") 
})

@Entity
@Table(name = "user", uniqueConstraints = { @UniqueConstraint(columnNames = {
		"username", "email" }) })
public class User extends EntityObject implements UserDetails {

	private static final long serialVersionUID = 3619044126921173168L;
	@Expose
	private String username;
	private String password;
	private String confirmPassword;
	@Expose
	private String fullname;
	@Expose
	private String email;
	private Integer version;
	private Set<Role> roles = Sets.newHashSet();
	private boolean enabled = true;
	private boolean accountExpired = false;
	private boolean accountLocked = false;
	private boolean credentialsExpired = false;

	@NotNull(message = "用户名不能为空！")
	@Length(min = 3, max = 16, message = "用户名最少 {min}, 最大 {max} 个字符")
	@Column(name = "username", length = 50, unique = true, nullable = false)
	public String getUsername() {
		return username;
	}

	@Column(name = "password", length = 50, nullable = false)
	public String getPassword() {
		return password;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	@Column(name = "fullname")
	public String getFullname() {
		return fullname;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	@Column(name = "enabled", nullable = false)
	public boolean isEnabled() {
		return enabled;
	}

	@Column(name = "account_expired")
	public boolean isAccountExpired() {
		return accountExpired;
	}

	@Transient
	public boolean isAccountNonExpired() {
		return !isAccountExpired();
	}

	@Column(name = "account_locked")
	public boolean isAccountLocked() {
		return accountLocked;
	}

	@Transient
	public boolean isAccountNonLocked() {
		return !isAccountLocked();
	}

	@Column(name = "credentials_expired")
	public boolean isCredentialsExpired() {
		return credentialsExpired;
	}

	@Transient
	public boolean isCredentialsNonExpired() {
		return !isCredentialsExpired();
	}

	@Transient
	public GrantedAuthority[] getAuthorities() {
		return roles.toArray(new GrantedAuthority[0]);
	}

	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
	@JoinTable(name = "rel_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setAccountExpired(boolean accountExpired) {
		this.accountExpired = accountExpired;
	}

	public void setAccountLocked(boolean accountLocked) {
		this.accountLocked = accountLocked;
	}

	public void setCredentialsExpired(boolean credentialsExpired) {
		this.credentialsExpired = credentialsExpired;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	@Transient
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void addRole(Role role) {
		this.roles.add(role);
		role.getUsers().add(this);
	}

	@Transient
	public boolean isAdministrator() {
		return IsInRoles(new RoleType[] { RoleType.SystemAdmin });
	}

	@Transient
	public boolean IsInRoles(RoleType[] roleNames) {

		if (roleNames == null || roleNames.length == 0)
			return false;

		Set<Role> userRoles = getRoles();
		for (Role userRole : userRoles) {
			for (RoleType roleName : roleNames) {
				if (roleName.toString().equals(userRole.getName()))
					return true;
			}
		}
		return false;
	}

	public String toString() {
		return new ToStringBuilder(this).append("username", username).append(
				"password", password).toString();
	}
}