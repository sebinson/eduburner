package readerside.crawler;

import org.apache.abdera.model.Feed;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-28
 * Time: 11:30:05
 */
public class FetchResult {

    private String userId;
    private String url;
    private Date crawTime;
    private Feed feed;


    public FetchResult(String userId, String url) {
        this.userId = userId;
        this.url = url;
    }

    public FetchResult(String userId, String url, Feed feed, Date crawTime) {
        this.userId = userId;
        this.url = url;
        this.crawTime = crawTime;
        this.feed = feed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Date getCrawTime() {
        return crawTime;
    }

    public void setCrawTime(Date crawTime) {
        this.crawTime = crawTime;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    @Override
    public String toString() {
        return "FetchResult{" +
                "userId='" + userId + '\'' +
                ", url='" + url + '\'' +
                ", crawTime=" + crawTime +
                '}';
    }
}
