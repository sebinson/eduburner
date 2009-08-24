package eduburner.search;

import eduburner.entity.Entry;
import eduburner.entity.user.UserData;

public interface IIndexService {

	void purgeIndex();

	void addEntryDocument(Entry entry);

	void addUserDocument(UserData user);

}
