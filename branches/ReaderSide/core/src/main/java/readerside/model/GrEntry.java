package readerside.model;

import com.google.common.base.Joiner;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "gr_entry")
public class GrEntry extends EntityObject {
    
    private static final long serialVersionUID = -8348674790743914578L;
	private String grEntryId;
    private String grFeedId;
    private Long grCrawlTime;
    private String grSourceFeedId;
    private String url;
    private String title;
    private String category;
    private Date published;
    private Date updated;
    private String link;
    private boolean cnEntry = false;

    private String likingUserIds = StringUtils.EMPTY;
    private String sharingUserIds = StringUtils.EMPTY;
    
    private int likingNum = 0;
    private int sharingNum = 0;

    public void addLikingUserId(String userId){
        String[] arr = new String[]{};
        if(!StringUtils.isEmpty(likingUserIds)){
           arr = likingUserIds.split(EntityObject.VALUES_SEPERATOR);
        }
        if(!ArrayUtils.contains(arr, userId)){
            String[] arr1 = (String[])ArrayUtils.add(arr, userId);
            likingUserIds = Joiner.on(EntityObject.VALUES_SEPERATOR).join(arr1);
            likingNum = arr1.length;
        }
    }

    public void addSharingUserId(String userId){
        String[] arr = new String[]{};
        if(!StringUtils.isEmpty(sharingUserIds)){
           arr = sharingUserIds.split(EntityObject.VALUES_SEPERATOR);
        }
        if(!ArrayUtils.contains(arr, userId)){
            String[] arr1 = (String[])ArrayUtils.add(arr, userId);
            sharingUserIds = Joiner.on(EntityObject.VALUES_SEPERATOR).join(arr1);
            sharingNum = arr1.length;
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getGrCrawlTime() {
        return grCrawlTime;
    }

    public void setGrCrawlTime(Long grCrawlTime) {
        this.grCrawlTime = grCrawlTime;
    }

    public String getOriginalId() {
        return grSourceFeedId;
    }

    public void setOriginalId(String originalId) {
        this.grSourceFeedId = originalId;
    }

    @Column(name="category", length = 1000)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Column(name="likingUserIds", length=5000)
    public String getLikingUserIds() {
        return likingUserIds;
    }

    public void setLikingUserIds(String likingUserIds) {
        this.likingUserIds = likingUserIds;
    }

    @Column(name="sharingUserIds", length=12000)
    public String getSharingUserIds() {
        return sharingUserIds;
    }

    public void setSharingUserIds(String sharingUserIds) {
        this.sharingUserIds = sharingUserIds;
    }

    @Index(name = "grentry_id")
    public String getGrEntryId() {
        return grEntryId;
    }

    public void setGrEntryId(String grEntryId) {
        this.grEntryId = grEntryId;
    }

    public String getGrFeedId() {
        return grFeedId;
    }

    public void setGrFeedId(String grFeedId) {
        this.grFeedId = grFeedId;
    }

    @Column(length=500)
    public String getGrSourceFeedId() {
        return grSourceFeedId;
    }

    public void setGrSourceFeedId(String grSourceFeedId) {
        this.grSourceFeedId = grSourceFeedId;
    }

    @Column(name="title", length = 2000)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCnEntry() {
        return cnEntry;
    }

    public void setCnEntry(boolean cnEntry) {
        this.cnEntry = cnEntry;
    }

	public int getLikingNum() {
		return likingNum;
	}

	public void setLikingNum(int likingNum) {
		this.likingNum = likingNum;
	}

	public int getSharingNum() {
		return sharingNum;
	}

	public void setSharingNum(int sharingNum) {
		this.sharingNum = sharingNum;
	}

	@Override
    public String toString() {
        return "GrEntry{" +
                "grEntryId='" + grEntryId + '\'' +
                ", grFeedId='" + grFeedId + '\'' +
                ", grCrawlTime=" + grCrawlTime +
                ", grSourceFeedId='" + grSourceFeedId + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", published=" + published +
                ", updated=" + updated +
                ", link='" + link + '\'' +
                ", cnEntry=" + cnEntry +
                ", likingUserIds='" + likingUserIds + '\'' +
                ", sharingUserIds='" + sharingUserIds + '\'' +
                '}';
    }
}
