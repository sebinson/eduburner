package eduburner.user.service.impl;

import javax.persistence.EntityExistsException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import eduburner.core.service.BaseManager;
import eduburner.user.domain.Role;
import eduburner.user.service.IRoleManager;

@Service("roleManager")
public class RoleManager extends BaseManager implements IRoleManager {

	public Role getRoleById(String id) {
		return dao.getInstanceById(Role.class, id);
	}

	public Role getRoleByName(String rolename) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
		criteria.add(Restrictions.eq("name", rolename));
		return (Role) dao.getUniqueInstanceByDetachedCriteria(criteria);
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