package eduburner.service.user;

import java.util.List;

import eduburner.entity.Comment;
import eduburner.entity.Entry;
import eduburner.entity.user.User;
import eduburner.entity.user.UserData;
import eduburner.persistence.EntityExistsException;
import eduburner.persistence.Page;

public interface IUserManager {
	
	public List<User> getAllUsers();

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
	
	public void createEntry(String userId, Entry entry);
	
	public void createEntry(String userId, String courseId, Entry entry);
	
	public void likeEntry(String userId, Entry entry);
	
	public void addEntryComment(String userId, String entryId, Comment comment);
	
	public void getHomePageEntriesForUser(User user);

	Page<Entry> getUserEntriesPage(UserData ud, int pageNo);
	
	public void sendInvitation(String requestorName, String candidateName);
	
	public void approveInvitation(String requestorName, String candidateName);
	
	public List<UserData> getFriends(String username);
}
