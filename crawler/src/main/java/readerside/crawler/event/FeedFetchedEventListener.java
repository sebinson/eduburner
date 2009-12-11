package readerside.crawler.event;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.abdera.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import readerside.crawler.FetchResult;
import readerside.crawler.service.ICrawlerService;
import readerside.crawler.util.StringUtil;
import readerside.model.*;

import javax.xml.namespace.QName;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-28
 * Time: 17:03:43
 */
@Component
public class FeedFetchedEventListener implements ApplicationListener<FeedFetchedEvent>, ApplicationContextAware{
    private static final Logger logger = LoggerFactory.getLogger(FeedFetchedEventListener.class);

    private static final String PREFIX = "gr";
    private static final String NAMESPACE_URI = "http://www.google.com/schemas/reader/atom/";

    private int count = 0;

    @Autowired
    @Qualifier("crawlerService")
    private ICrawlerService crawlerService;
    private ApplicationContext appCtx;

    @Override
    public void onApplicationEvent(FeedFetchedEvent event) {
        count ++;
        logger.info("handle feed fetch event. count: " + count);
        FetchResult fetchResult = event.getFetchResult();
        handleFetchReslt(fetchResult);
    }

    @Transactional
    private void handleFetchReslt(FetchResult fetchResult) {
        GrUser user = crawlerService.getGrUser(fetchResult.getUserId());
        if(user == null){
            user = new GrUser();
        }
        user.setLastFetchTime(fetchResult.getCrawTime());
        user.setGrUserId(fetchResult.getUserId());

        Feed feed = fetchResult.getFeed();
        saveGrFeed(feed, fetchResult.getUserId());

        Set<String> newUserIds = Sets.newHashSet();
        Set<String> fetchedUserIds = Sets.newHashSet();

        int cnEntryCnt = 0;
        for (Entry entry : feed.getEntries()) {
            String grEntryId = entry.getId().toString();

            user.addSharingEntry(grEntryId);

            GrEntry grEntry = crawlerService.getGrEntry(grEntryId);
            if(grEntry == null){
                grEntry = new GrEntry();
            }

            fillGrEntry(entry, grEntry);
            grEntry.addSharingUserId(user.getGrUserId());
            Set<String> likingUserIds = extractLikedUsers(entry);

            SourceFeed sourceFeed = saveSourceFeed(entry);;

            grEntry.setGrFeedId(feed.getId().toString());
            grEntry.setGrSourceFeedId(sourceFeed.getGrSourceFeedId());
            
            String s = entry.getSummary();
            String c = entry.getContent();
            boolean chineseText = StringUtil.isChineseText(s, c);
			if(chineseText){
                logger.info("chinese entry. " + grEntry.getTitle());
                grEntry.setCnEntry(true);
                cnEntryCnt++;
                if (cnEntryCnt > 1) {
                    user.setCnUser(true);
                }
            }
            
            for(String likingUserId : likingUserIds){
                GrUser newUser = crawlerService.getGrUser(likingUserId);
                if(newUser == null){
                    newUser = new GrUser();
                }

                if(chineseText && newUser.getLastFetchTime() == null){
                     newUserIds.add(likingUserId);
                }
                if(chineseText){
                	newUser.setCnUser(true);
                }
                newUser.setGrUserId(likingUserId);
                newUser.addLikingEntry(grEntryId);
                crawlerService.saveGrUser(newUser);
                grEntry.addLikingUserId(newUser.getGrUserId());
            }

            crawlerService.saveGrEntry(grEntry);

            saveEntryData(s, c, grEntryId);
        }

        fetchedUserIds.add(user.getGrUserId());
        crawlerService.saveGrUser(user);

        appCtx.publishEvent(new UrlScheduleEvent(this, fetchedUserIds, newUserIds));
    }

    private void fillGrEntry(Entry entry, GrEntry grEntry) {
        grEntry.setTitle(entry.getTitle());
        grEntry.setGrEntryId(entry.getId().toString());
        grEntry.setPublished(entry.getPublished());
        grEntry.setUpdated(entry.getUpdated());
        Link link = entry.getLink("alternate");
        if(link != null){
        	grEntry.setLink(link.getAttributeValue("href"));
        }
        List<String> catList = Lists.newArrayList();
        List<Category> categories =entry.getCategories();
        for(Category cat : categories){
            String term = cat.getAttributeValue("term");
            catList.add(term);
        }
        grEntry.setCategory(Joiner.on(EntityObject.VALUES_SEPERATOR).join(catList));
        QName crawlQName = new QName(NAMESPACE_URI, "crawl-timestamp-msec", PREFIX);
        long crawlTime = new Long(entry.getAttributeValue(crawlQName));
        grEntry.setGrCrawlTime(crawlTime);
    }

    private SourceFeed saveSourceFeed(Entry entry) {
        Source source = entry.getSource();
        String sourceFeedId = source.getId().toString();
        SourceFeed sourceFeed = crawlerService.getSourceFeed(sourceFeedId);
        if(sourceFeed == null){
        	sourceFeed = new SourceFeed();
        }
		sourceFeed.setGrSourceFeedId(sourceFeedId);
        sourceFeed.setTitle(source.getTitle());
        Link link = source.getLink("alternate");
        if(link != null){
        	sourceFeed.setLink(link.getAttributeValue("href"));
        }
        QName qName = new QName(NAMESPACE_URI, "stream-id", PREFIX);
        sourceFeed.setFeedUrl(source.getAttributeValue(qName));
        crawlerService.saveSourceFeed(sourceFeed);
        return sourceFeed;
    }

    private Set<String> extractLikedUsers(Entry entry){
        Set<String> likingUsers = Sets.newHashSet();
        QName qName = new QName(NAMESPACE_URI, "likingUser", PREFIX);
        List<ExtensibleElement> eles = entry.getExtensions(qName);
        for(ExtensibleElement el : eles){
           likingUsers.add(el.getText());
        }
        return likingUsers;
    }

    private void saveGrFeed(Feed feed, String grUserId){
    	String grFeedId = feed.getId().toString();
        GrFeed grFeed = crawlerService.getGrFeed(grFeedId);
        if(grFeed == null){
        	grFeed = new GrFeed();
        }
        grFeed.setGrUserId(grUserId);
		grFeed.setGrFeedId(grFeedId);
        grFeed.setAuthor(feed.getAuthor().getName());
        grFeed.setTitle(feed.getTitle());
        grFeed.setUpdatedTime(feed.getUpdated());
        grFeed.setSelfLink(feed.getLink("self").getAttributeValue("href"));
        crawlerService.saveGrFeed(grFeed);
    }

    private void saveEntryData(String summary, String content, String entryId) {
        EntryData es = crawlerService.getEntryData(entryId);
        if(es == null){
        	es = new EntryData();
        }
        es.setGrEntryId(entryId);
        es.setSummary(summary);
        es.setContent(content);
        crawlerService.saveEntryData(es);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appCtx = applicationContext;
    }
}
