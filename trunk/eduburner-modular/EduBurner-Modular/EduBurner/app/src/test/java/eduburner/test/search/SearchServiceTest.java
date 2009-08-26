package eduburner.test.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;

import eduburner.entity.Entry;
import eduburner.search.IEntrySearchService;
import eduburner.service.user.IUserManager;
import eduburner.test.service.BaseServiceTestSupport;

public class SearchServiceTest extends BaseServiceTestSupport{
	
	@Autowired
	@Qualifier("entrySearchService")
	private IEntrySearchService entrySearchService;
	
	@Autowired
	@Qualifier("userManager")
	private IUserManager userManager;

	@Test
	public void testSearchService(){
		
		List<Entry> entries = userManager.getAllEntries();
		
		entrySearchService.indexEntries(entries);
		
		System.out.println("test search service");
		
		//List l = searchService.search(SearchConstants.FIELD_ENTRY_TITLE, "Lorem");
		
		/*for(Object o : l){
			System.out.println("l: " + l.toString());
		}*/
		
	}
}
