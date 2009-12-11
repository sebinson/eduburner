package readerside.crawler.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;
import readerside.crawler.Crawler;
import readerside.crawler.FetchResult;
import readerside.crawler.model.CrawlURI;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-27
 * Time: 7:09:05
 */
public class FeedFetchedEvent extends ApplicationEvent{

    private FetchResult fetchResult;
 
    public FeedFetchedEvent(Object source, FetchResult fetchResult) {
        super(source);
        this.fetchResult = fetchResult;
    }

    public FetchResult getFetchResult() {
         return fetchResult;
     }

}
