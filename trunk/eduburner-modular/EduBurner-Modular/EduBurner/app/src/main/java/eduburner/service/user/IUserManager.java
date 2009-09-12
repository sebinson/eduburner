package eduburner.service.user;

import java.util.List;

import eduburner.entity.Comment;
import eduburner.entity.Entry;
import eduburner.entity.course.Course;
import eduburner.entity.user.User;
import eduburner.entity.user.UserData;
import eduburner.persistence.EntityExistsException;
import eduburner.persistence.Page;

public interface IUserManager {
	
	public List<User> getAllUsers();
	
	public List<UserData> getAllUserDatas();

	public User getUserById(String userId);

	public void createUser(User user) throws EntityExistsException;

	public void updateUser(User user);
	
	public void uploadUserProfilePicture(String username, String profilePicturePath);

	public void alterPassword(User user, String newPassword);

	public User getUserByUsername(String username);

	public void removeUser(String userId);

	public UserData getUserDataByUserId(String userId);
	
	public UserData getUserDataByUsername(String username);

	public UserData createUserData(User user);

	public void updateUserData(UserData ud);
	
	public UserData getUserData(User user);
	
	public void createEntry(UserData user, Entry entry);
	
	public void createEntry(UserData user, Course course, Entry entry);
	
	public void likeEntry(UserData user, Entry entry);
	
	public void addEntryComment(UserData user, Entry entry, Comment comment);
	
	public Page<Entry> getHomePageEntriesForUser(UserData user, int pageNo);

	Page<Entry> getUserEntriesPage(UserData ud, int pageNo);
	
	public void sendInvitation(String requestorName, String candidateName);
	
	public void approveInvitation(String requestorName, String candidateName);
	
	public List<UserData> getFriends(String username);
	
	public List<Entry> getAllEntries();
	
	public void addFriend(UserData requestor, UserData candidate);
}
