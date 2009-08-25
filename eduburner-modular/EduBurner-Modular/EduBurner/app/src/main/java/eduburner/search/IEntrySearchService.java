package eduburner.search;

import java.util.List;

import eduburner.entity.Entry;

public interface IEntrySearchService {

	void addEntryDocument(Entry entry);

	void indexEntries(List<Entry> entries);

	List<Entry> searchEntry(String query);

}
