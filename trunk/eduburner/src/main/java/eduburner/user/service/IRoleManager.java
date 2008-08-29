package eduburner.user.service;

import javax.persistence.EntityExistsException;

import eduburner.user.domain.Role;


public interface IRoleManager {
	public Role getRoleById(String id);

	/**
	 * Gets role information based on rolename
	 * 
	 * @param rolename
	 *            the rolename
	 * @return role populated role object
	 */
	public Role getRoleByName(String rolename);

	public void createRole(Role role) throws EntityExistsException;

	/**
	 * Saves a role's information
	 * 
	 * @param role
	 *            the object to be saved
	 */
	public void saveRole(Role role);

	/**
	 * Removes a role from the database by name
	 * 
	 * @param rolename
	 *            the role's rolename
	 */
	public void removeRole(String rolename);
}
