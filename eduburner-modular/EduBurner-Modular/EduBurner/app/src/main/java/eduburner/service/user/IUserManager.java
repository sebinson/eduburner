package eduburner.service.user;

import java.util.List;

import eduburner.entity.user.User;
import eduburner.entity.user.UserData;
import eduburner.persistence.EntityExistsException;

public interface IUserManager {
	
	public List<User> getAllUsers();

	public User getUserById(long userId);

	public void createUser(User user) throws EntityExistsException;

	public void updateUser(User user);

	public void alterPassword(User user, String newPassword);

	public User getUserByUsername(String username);

	public void removeUser(long userId);

	public UserData getUserDataByUserId(long userId);
	
	public UserData getUserDataByUsername(String username);

	public UserData createUserData(User user);

	public void updateUserDate(UserData ud);
	
	public UserData getUserData(User user);
}
