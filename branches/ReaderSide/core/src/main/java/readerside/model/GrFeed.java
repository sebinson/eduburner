package readerside.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

import java.util.Date;

@Entity
@Table(name="gr_shared_feed")
public class GrFeed extends EntityObject{

    private static final long serialVersionUID = 8080111141541518466L;
	private String grFeedId;
    private String grUserId;
	private String title;
	private String author;
    private String selfLink;
	private Date updatedTime;

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getGrUserId() {
        return grUserId;
    }

    public void setGrUserId(String grUserId) {
        this.grUserId = grUserId;
    }

    @Index(name="grfeed_index")
    public String getGrFeedId() {
        return grFeedId;
    }

    public void setGrFeedId(String grFeedId) {
        this.grFeedId = grFeedId;
    }

    @Override
    public String toString() {
        return "GrFeed{" +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", updatedTime=" + updatedTime +
                '}';
    }
}
