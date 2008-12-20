package eduburner.user.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import eduburner.user.domain.PermissionBase;
import eduburner.user.domain.User;

@SuppressWarnings("unchecked")
public interface IPermissionManager {

	public boolean hasPermission(User user, PermissionBase permission, Object target);

	public List getPermittedEntities(User user, PermissionBase permission, List list);

	public List getPermittedEntities(User user, PermissionBase permission,
			Iterator iterator, int i);

	public List getPermittedEntities(User user, PermissionBase permission,
			Iterator iterator, int i, Collection collection);
}
