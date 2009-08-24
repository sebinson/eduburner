package eduburner.test.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;

import eduburner.entity.Entry;
import eduburner.search.IIndexService;
import eduburner.search.ISearchService;
import eduburner.search.SearchConstants;
import eduburner.service.user.IUserManager;
import eduburner.test.service.BaseServiceTestSupport;

public class SearchServiceTest extends BaseServiceTestSupport{
	
	@Autowired
	@Qualifier("searchService")
	private ISearchService searchService;
	
	@Autowired
	@Qualifier("indexService")
	private IIndexService indexService;
	
	@Autowired
	@Qualifier("userManager")
	private IUserManager userManager;

	@Test
	public void testSearchService(){
		
		indexService.purgeIndex();
		
		List<Entry> entries = userManager.getAllEntries();
		
		for(Entry e : entries){
			indexService.addEntryDocument(e);
		}
		
		System.out.println("test search service");
		
		List l = searchService.search(SearchConstants.FIELD_ENTRY_TITLE, "Lorem");
		
		for(Object o : l){
			System.out.println("l: " + l.toString());
		}
		
	}
}
