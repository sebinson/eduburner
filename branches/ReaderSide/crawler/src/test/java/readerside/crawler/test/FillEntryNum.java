package readerside.crawler.test;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.support.GenericXmlContextLoader;

import readerside.crawler.service.ICrawlerService;
import readerside.model.GrEntry;

public class FillEntryNum{

	public static void main(String... args) throws Exception{
		ContextLoader loader = new GenericXmlContextLoader();
		ApplicationContext ctx = loader.loadContext(getConfigLocations());
		
		ICrawlerService crawlerService = (ICrawlerService)ctx.getBean(ICrawlerService.class);
		
		List<GrEntry> entries = crawlerService.getAllEntries();
		for(GrEntry entry : entries){
			System.out.println("title: " + entry.getTitle());
			System.out.println("liking num: " + entry.getLikingNum());
			System.out.println("sharing num: " + entry.getSharingNum());
			String luids = entry.getLikingUserIds();
			if(!StringUtils.isEmpty(luids)){
				int l = luids.split(",").length;
				System.out.println("l1: " + l);
				entry.setLikingNum(l);
			}
			String suids = entry.getSharingUserIds();
			if(!StringUtils.isEmpty(suids)){
				int l = suids.split(",").length;
				System.out.println("l2: " + l);
				entry.setSharingNum(l);
			}
			crawlerService.saveGrEntry(entry);
		}
	}
	
	private static String[] getConfigLocations(){
		return new String[]{
			"classpath:applicationContext.xml"
		};
	}
}
