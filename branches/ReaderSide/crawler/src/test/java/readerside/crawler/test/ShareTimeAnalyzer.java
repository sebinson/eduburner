package readerside.crawler.test;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.support.GenericXmlContextLoader;

import readerside.crawler.service.ICrawlerService;
import readerside.model.GrEntry;
import readerside.persistence.IDao;

public class ShareTimeAnalyzer {

	public static void main(String... args) throws Exception{
		int[] timeArray = new int[48];
		ContextLoader loader = new GenericXmlContextLoader();
		ApplicationContext ctx = loader.loadContext(getConfigLocations());
		
		IDao dao = (IDao) ctx.getBean("dao");
		
		List<GrEntry> entries = (List<GrEntry>)dao.find("from GrEntry as ge where ge.cnEntry = true");
		
		for(GrEntry entry:entries){
			long crawltime = entry.getGrCrawlTime();
			int index = getTimeArrayIndex(crawltime);
			timeArray[index] ++;
		}
		
		PrintWriter pw = new PrintWriter(new File("d:/test.csv"));
		pw.flush();
		for(int i=0; i<timeArray.length; i++){
			pw.print(i);
			pw.print("," + timeArray[i]);
			pw.println();
		}
		
		pw.close();
	}
	
	private static int getTimeArrayIndex(long time){
		DateTime dt = new DateTime(time);
		
		int h = dt.hourOfDay().get();
		int m = dt.minuteOfHour().get();
		
		int r = 2*h + m/30;
		
		if(r< 0 || r>47){
			System.out.println("out of range");
		}
		
		return r;
	}
	
	private static String[] getConfigLocations(){
		return new String[]{
			"classpath:applicationContext.xml"
		};
	}
}
