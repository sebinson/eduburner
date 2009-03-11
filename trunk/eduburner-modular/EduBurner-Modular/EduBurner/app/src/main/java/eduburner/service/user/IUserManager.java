package eduburner.service.user;

import javax.persistence.EntityExistsException;

import eduburner.entity.user.User;

public interface IUserManager {
	public User getUserById(long userId);

	public void createUser(User user) throws EntityExistsException;

	public void updateUser(User user);

	public void alterPassword(User user, String newPassword);

	public User getUserByUsername(String username);

	public void removeUser(long userId);
	
}
