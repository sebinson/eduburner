package eduburner.service.user;

import javax.persistence.EntityExistsException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eduburner.entity.user.Role;
import eduburner.persistence.IDao;

@Service("roleManager")
@Transactional
public class RoleManager implements IRoleManager {

	@Autowired
	@Qualifier("dao")
	private IDao dao;
	
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

	@Override
	public void updateRole(Role role) {
		dao.update(role);
	}
}
