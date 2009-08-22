package eduburner.service.user;

import java.util.List;

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
import org.springframework.transaction.annotation.Transactional;

import eduburner.entity.Comment;
import eduburner.entity.Entry;
import eduburner.entity.user.User;
import eduburner.entity.user.UserData;
import eduburner.persistence.EntityExistsException;
import eduburner.persistence.Page;
import eduburner.service.BaseManager;

@Service("userManager")
@Transactional
public class UserManager extends BaseManager implements UserDetailsService,
		IUserManager {
	private Logger logger = LoggerFactory.getLogger(UserManager.class);

	@Autowired
	@Qualifier("passwordEncoder")
	private PasswordEncoder passwordEncoder;

	@Override
	public List<User> getAllUsers() {
		return dao.getAllInstances(User.class);
	}

	@Override
	public void createUser(User user) throws EntityExistsException {
		logger.debug("entering saveUser method...");
		
		try {
			dao.save(user);
		} catch (DataIntegrityViolationException e) {
			throw new EntityExistsException("User '" + user.getUsername()
					+ "' already exists!");
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		return getUserByUsername(username);
	}

	@Override
	public void updateUser(User user) {
		dao.saveOrUpdate(user);
	}

	@Override
	public void alterPassword(User user, String newPassword)
			throws UsernameNotFoundException {
		User foundUser = getUserByUsername(user.getUsername());
		foundUser
				.setPassword(passwordEncoder.encodePassword(newPassword, null));
		updateUser(foundUser);
	}

	@Override
	public User getUserByUsername(String username) {
		/*DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq("username", username));
		User user = (User) dao.getUniqueInstanceByDetachedCriteria(criteria);*/
		
		User user = dao.findUniqueBy(User.class, "username", username);
		return user;
	}

	@Override
	public User getUserById(String userId) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering getUserById method");
		}
		return dao.getInstanceById(User.class, userId);
	}

	@Override
	public void removeUser(String userId) {
		dao.remove(getUserById(userId));
	}

	/**
	 * get UserData, if of UserData found, create new one
	 */
	@Override
	public UserData getUserDataByUserId(String userId) {
		User user = getUserById(userId);
		return getUserData(user);
	}

	@Override
	public UserData getUserDataByUsername(String username) {
		User user = getUserByUsername(username);
		return getUserData(user);
	}

	@Override
	public UserData getUserData(User user) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserData.class);
		String username = user.getUsername();
		logger.debug("username is: "+username);
		criteria.add(Restrictions.eq("username", username));

		List<?> userDatas = dao.getInstancesByDetachedCriteria(criteria);

		if (userDatas.size() == 0) {
			return createUserData(user);
		}

		return (UserData) userDatas.get(0);
	}

	@Override
	public UserData createUserData(User user) {
		UserData ud = new UserData(user);
		dao.save(ud);
		return ud;
	}

	@Override
	public void updateUserData(UserData ud) {
		dao.update(ud);
	}
	
	@Override
	public void createEntry(String userId, Entry entry) {
		UserData ud = getUserDataByUserId(userId);
		entry.setUser(ud);
		dao.save(entry);
	}

	@Override
	public void createEntry(String userId, String courseId, Entry entry) {
		
	}

	@Override
	public void addEntryComment(String userId, String entryId, Comment comment) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void likeEntry(String userId, Entry entry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getHomePageEntriesForUser(User user) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Page<Entry> getUserEntriesPage(UserData ud, int pageNo){
		logger.debug("entering getUserEntriesPage method...");
		Page<Entry> page = new Page<Entry>();
		page.setPageNo(pageNo);
		return dao.findPage(page, "from Entry as e where e.user.id = ?", ud.getId());
	}

	@Override
	public void uploadUserProfilePicture(String username,
			String profilePicturePath) {
		UserData ud = getUserDataByUsername(username);
		ud.setProfilePicture(profilePicturePath);
		updateUserData(ud);
	}
}
