package eduburner.user.service.impl;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import eduburner.core.service.BaseManager;
import eduburner.user.domain.Role;
import eduburner.user.service.IRoleManager;


@Service("roleManager")
public class DefaultRoleManager extends BaseManager implements IRoleManager {

	public Role getRoleById(String id) {
		return dao.getInstanceById(Role.class, id);
	}

	@SuppressWarnings("unchecked")
	public Role getRoleByName(String rolename) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
		criteria.add(Restrictions.eq("name", rolename));

		List roles = dao.getInstancesByDetachedCriteria(criteria);

		if (roles.size() == 0) {
			throw new UsernameNotFoundException("Role not found");
		}

		return (Role) roles.get(0);
	}

	public void removeRole(String rolename) {
		dao.remove(getRoleByName(rolename));

	}

	public void saveRole(Role role) {
		dao.saveOrUpdate(role);
	}

	public void createRole(Role role) throws EntityExistsException {
		dao.save(role);
	}
}
