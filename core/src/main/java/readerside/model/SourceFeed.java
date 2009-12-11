package readerside.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@Entity
@Table(name="source_feed")
public class SourceFeed extends EntityObject{

    private static final long serialVersionUID = -2706325134453100210L;
	private String grSourceFeedId;
    private String title;
    private String feedUrl;
    private String link;

    @Index(name="grsf_id")
    @Column(length=500)
    public String getGrSourceFeedId() {
        return grSourceFeedId;
    }

    public void setGrSourceFeedId(String grSourceFeedId) {
        this.grSourceFeedId = grSourceFeedId;
    }

    @Column(length=500)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "SourceFeed{" +
                "grSourceFeedId='" + grSourceFeedId + '\'' +
                ", title='" + title + '\'' +
                ", feedUrl='" + feedUrl + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
