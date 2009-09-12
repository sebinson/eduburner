package eduburner.persistence.user;

import eduburner.entity.Entry;
import eduburner.entity.user.UserData;
import eduburner.persistence.IDao;
import eduburner.persistence.Page;

public interface IUserDao extends IDao{
	
	public Page<Entry> getHomePageEntriesForUser(UserData user, int pageNo);
}
