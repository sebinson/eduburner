package eduburner.service.user;

import eduburner.entity.user.User;
import eduburner.entity.user.UserData;
import eduburner.persistence.EntityExistsException;

public interface IUserManager {

	public User getUserById(long userId);

	public void createUser(User user) throws EntityExistsException;

	public void updateUser(User user);

	public void alterPassword(User user, String newPassword);

	public User getUserByUsername(String username);

	public void removeUser(long userId);

	public UserData getUserData(long userId);

	public UserData createUserData(long userId);

	public void updateUserDate(UserData ud);
}
