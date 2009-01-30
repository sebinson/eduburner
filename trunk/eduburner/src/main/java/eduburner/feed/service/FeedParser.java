package eduburner.feed.service;

import java.io.IOException;
import java.net.URL;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.parser.ParseException;
import org.apache.abdera.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * demo to use abdera
 * @author zhangyf@gmail.com
 *
 */
public class FeedParser {
	
	@Autowired
	@Qualifier("abdera")
	private Abdera abdera;
	
	public void parse(URL url) throws ParseException, IOException{
		Parser parser = abdera.getParser();
		  
		Document<Feed> doc = parser.parse(url.openStream());
		Feed feed = doc.getRoot();
		
		System.out.println(feed.getTitle());
		for (Entry entry : feed.getEntries()) {
		  System.out.println("\t" + entry.getTitle());
		}
	}
}
