package eduburner.service.user;

import java.util.Date;
import java.util.List;
import java.util.UUID;

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

import com.google.common.collect.Lists;

import eduburner.entity.Comment;
import eduburner.entity.Entry;
import eduburner.entity.user.Invitation;
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
	public List<UserData> getAllUserDatas() {
		return dao.getAllInstances(UserData.class);
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
	public void createEntry(UserData user, Entry entry) {
		entry.setUser(user);
		user.getEntries().add(entry);
		dao.save(entry);
	}

	@Override
	public void createEntry(UserData user, String courseId, Entry entry) {
		
	}

	@Override
	public void addEntryComment(UserData user, Entry entry, Comment comment) {
		comment.setUser(user);
		entry.getComments().add(comment);
		dao.save(comment);
		dao.update(entry);
	}

	@Override
	public void likeEntry(UserData user, Entry entry) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Page<Entry> getHomePageEntriesForUser(UserData user, int pageNo) {
		List<String> usernames = Lists.newArrayList();
		usernames.add(user.getUsername());
		for(UserData ud : user.getFriends()){
			usernames.add(ud.getUsername());
		}
		long totalCount = dao.findUnique("select count(*) from Entry as e where e.user.username in {?}", usernames);
		Page<Entry> page = new Page<Entry>();
		page.setTotalCount(totalCount);
		return dao.findPage(page, "from Entry as e where e.user.username in {?}", usernames);
	}
	
	@Override
	public Page<Entry> getUserEntriesPage(UserData ud, int pageNo){
		long totalCount = dao.findUnique("select count(*) from Entry as e where e.user.id = ? order by e.published desc", ud.getId());
		Page<Entry> page = new Page<Entry>();
		page.setPageNo(pageNo);
		page.setTotalCount(totalCount);
		return dao.findPage(page, "from Entry as e where e.user.id = ? order by e.published desc", ud.getId());
	}

	@Override
	public void uploadUserProfilePicture(String username,
			String profilePicturePath) {
		UserData ud = getUserDataByUsername(username);
		ud.setProfilePicture(profilePicturePath);
		updateUserData(ud);
	}

	@Override
	public List<UserData> getFriends(String username) {
		UserData user = getUserDataByUsername(username);
		return user.getFriends();
	}

	@Override
	public void sendInvitation(String requestorName, String candidateName) {
		UserData requestor = getUserDataByUsername(requestorName);
		UserData candidate = getUserDataByUsername(candidateName);
		Invitation invitation = new Invitation();
		invitation.setCreateTime(new Date());
		invitation.setRequestor(requestor);
		invitation.setCode(UUID.randomUUID().toString());
		requestor.getOutgoingInvitations().add(invitation);
		invitation.setCandidate(candidate);
		candidate.getIncomingInvitations().add(invitation);
		dao.save(invitation);
	}
	
	@Override
	public void approveInvitation(String requestorName, String candidateName) {
		UserData requestor = getUserDataByUsername(requestorName);
		UserData candidate = getUserDataByUsername(candidateName);
		Invitation invitation = dao.findUnique("from Invitation as i where i.requestor.username = ? and i.candidate.username = ?", requestorName, candidateName);
		if(invitation != null){
			invitation.setAccepted(true);
		}
		dao.update(invitation);
		requestor.getFriends().add(candidate);
		dao.update(requestor);
	}

	@Override
	public List<Entry> getAllEntries() {
		return dao.getAllInstances(Entry.class);
	}

	@Override
	public void addFriend(UserData requestor, UserData candidate) {
		requestor.getFriends().add(candidate);
		dao.update(requestor);
	}
}
