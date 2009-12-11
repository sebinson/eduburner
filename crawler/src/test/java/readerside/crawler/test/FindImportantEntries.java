package readerside.crawler.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.support.GenericXmlContextLoader;

import readerside.model.GrEntry;
import readerside.persistence.IDao;
import readerside.persistence.Page;

public class FindImportantEntries {

	public static void main(String... args) throws Exception {
		ContextLoader loader = new GenericXmlContextLoader();
		ApplicationContext ctx = loader.loadContext(getConfigLocations());

		IDao dao = (IDao) ctx.getBean("dao");
		
		Page<GrEntry> entryPage = new Page<GrEntry>();
		
		entryPage.setPageNo(1);
		entryPage.setPageSize(20);
		entryPage.setAutoCount(false);
		
		dao.findPage(entryPage, "from GrEntry as ge where ge.cnEntry = true order by likingNum desc");
		
		System.out.println("most liking entries: \n ======================================");
		
		List<GrEntry> entries = entryPage.getItems();
		for(GrEntry entry :entries){
			System.out.println("title: " + entry.getTitle());
			System.out.println("link: " +entry.getLink());
		}
		
		System.out.println("most sharing entries: \n =======================================");
		
		dao.findPage(entryPage, "from GrEntry as ge where ge.cnEntry = true order by sharingNum desc");
		
		entries = entryPage.getItems();
		for(GrEntry entry :entries){
			System.out.println("title: " + entry.getTitle());
			System.out.println("link: " +entry.getLink());
		}

	}

	private static String[] getConfigLocations() {
		return new String[] { "classpath:applicationContext.xml" };
	}

}
