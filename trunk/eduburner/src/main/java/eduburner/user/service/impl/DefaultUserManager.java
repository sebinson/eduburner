package eduburner.user.service.impl;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.providers.encoding.PasswordEncoder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import eduburner.core.service.BaseManager;
import eduburner.user.domain.User;
import eduburner.user.service.IUserManager;

@Service("userManager")
public class DefaultUserManager extends BaseManager implements
		UserDetailsService, IUserManager {
	private Logger logger = LoggerFactory.getLogger(DefaultUserManager.class);

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	public void createUser(User user) throws EntityExistsException {
		if (logger.isDebugEnabled()) {
			logger.debug("entering saveUser method...");
		}
		try {
			dao.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new EntityExistsException("User '" + user.getUsername()
					+ "' already exists!");
		}
	}

	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		return getUserByUsername(username);
	}

	public void updateUser(User user) {
		dao.saveOrUpdate(user);
	}

	public void alterPassword(User user, String newPassword)
			throws UsernameNotFoundException {
		User foundUser = getUserByUsername(user.getUsername());
		foundUser
				.setPassword(passwordEncoder.encodePassword(newPassword, null));
		updateUser(foundUser);
	}

	@SuppressWarnings("unchecked")
	public User getUserByUsername(String username) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("username", username));

		List users = dao.getInstancesByDetachedCriteria(criteria);

		if (logger.isDebugEnabled()) {
			logger.debug("user number: " + users.size());
		}

		if (users.size() == 0) {
			throw new UsernameNotFoundException("User not found");
		}

		User user = (User) users.get(0);
		return user;
	}

	public User getUserById(long userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering getUserById method");
		}
		return dao.getInstanceById(User.class, new Long(userId));
	}

	public void removeUser(long userId) {
		dao.remove(getUserById(userId));
	}
}
