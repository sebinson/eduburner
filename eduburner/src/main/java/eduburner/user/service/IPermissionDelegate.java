package eduburner.user.service;

import eduburner.user.domain.User;

public interface IPermissionDelegate {
	public abstract boolean canCreate(User user, Object target);
	
	public abstract boolean canView(User user, Object target);

	public abstract boolean canEdit(User user, Object target);
	
	public abstract boolean canRemove(User user, Object target);

	public abstract boolean canSetPermissions(User user, Object target);
	
}
