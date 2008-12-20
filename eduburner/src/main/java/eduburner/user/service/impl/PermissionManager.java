package eduburner.user.service.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import eduburner.core.service.BaseManager;
import eduburner.user.domain.PermissionBase;
import eduburner.user.domain.User;
import eduburner.user.service.IPermissionManager;

@Service("permissionManager")
@SuppressWarnings("unchecked")
public class PermissionManager extends BaseManager implements
		IPermissionManager {

	public List getPermittedEntities(User user, PermissionBase permission, List list) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getPermittedEntities(User user, PermissionBase permission,
			Iterator iterator, int i) {
		// TODO Auto-generated method stub
		return null;
	}

	public List getPermittedEntities(User user, PermissionBase permission,
			Iterator iterator, int i, Collection collection) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasPermission(User user, PermissionBase permission, Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

}
