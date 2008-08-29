package eduburner.user.service;

import javax.persistence.EntityExistsException;

import eduburner.user.domain.User;

public interface IUserManager {
	public User getUserById(long userId);

	public void createUser(User user) throws EntityExistsException;

	public void updateUser(User user);

	public void alterPassword(User user, String newPassword);

	public User getUserByUsername(String username);

	public void removeUser(long userId);
	
}
