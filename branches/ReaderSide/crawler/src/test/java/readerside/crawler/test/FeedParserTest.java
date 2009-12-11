package readerside.crawler.test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.namespace.QName;

import junit.framework.TestCase;

import org.apache.abdera.Abdera;
import org.apache.abdera.model.Document;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Source;

import readerside.model.SourceFeed;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-29
 * Time: 3:51:47
 */
public class FeedParserTest extends TestCase{

    public void testParseFeed() throws FileNotFoundException{

        Abdera abdera = Abdera.getInstance();
        InputStream stream = new BufferedInputStream(new FileInputStream("E:\\ReaderSide\\crawler\\src\\test\\java\\readerside\\crawler\\test\\feed.xml"));
        Document<Feed> doc = abdera.getParser().parse(stream);

        Feed feed = doc.getRoot();
        
        for(Entry entry: feed.getEntries()){
        	saveSourceFeed(entry);
        }

        System.out.println("feed title: " + feed.getTitle());
        System.out.println("feed id: " + feed.getId().toString());
        System.out.println("feed link is: " + feed.getLinks().get(0));
        System.out.println("self link is: " + feed.getLink("self").getAttributeValue("href"));
    }
    
    private SourceFeed saveSourceFeed(Entry entry) {
        Source source = entry.getSource();
        String sourceFeedId = source.getId().toString();
        SourceFeed sourceFeed = new SourceFeed();
		sourceFeed.setGrSourceFeedId(sourceFeedId);
        sourceFeed.setTitle(source.getTitle());
        sourceFeed.setLink(source.getLink("alternate").getAttributeValue("href"));
        QName qName = new QName("http://www.google.com/schemas/reader/atom/", "stream-id", "gr");
        String attributeValue = source.getAttributeValue(qName);
        System.out.println("value: " + attributeValue);
		sourceFeed.setFeedUrl(attributeValue);
        return sourceFeed;
    }
    
    public static void main(String... args) throws FileNotFoundException{
    	new FeedParserTest().testParseFeed();
    }
}
