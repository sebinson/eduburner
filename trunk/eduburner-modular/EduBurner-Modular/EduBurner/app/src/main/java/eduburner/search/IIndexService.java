package eduburner.search;

import java.util.List;

import eduburner.entity.Entry;
import eduburner.entity.user.UserData;

public interface IIndexService {

	void purgeIndex();

	/*void addEntryDocument(Entry entry);

	void addUserDocument(UserData user);*/

	void optimize();

	void indexEntries(List<Entry> entries);

}
