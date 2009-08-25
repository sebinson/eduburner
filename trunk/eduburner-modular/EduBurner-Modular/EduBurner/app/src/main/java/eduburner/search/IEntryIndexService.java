package eduburner.search;

import java.io.IOException;
import java.util.List;

import eduburner.entity.Entry;
import eduburner.entity.user.UserData;

public interface IEntryIndexService {


	/*void addEntryDocument(Entry entry);

	void addUserDocument(UserData user);*/


	void indexEntries(List<Entry> entries);

}
