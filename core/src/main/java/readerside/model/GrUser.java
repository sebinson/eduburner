package readerside.model;

import com.google.common.base.Joiner;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Index;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 *  google reader user
 */
@Entity
@Table(name="gr_user")
public class GrUser extends EntityObject{

	private static final long serialVersionUID = 2743105909819673075L;
	private String grUserId;
	private String email = StringUtils.EMPTY;
    private Date lastFetchTime;

    private boolean cnUser = false;


    private String likingEntries = StringUtils.EMPTY;

    private String sharingEntries = StringUtils.EMPTY;

    public void addSharingEntry(String grEntryId){
        String[] arr = new String[]{};
        if(StringUtils.isEmpty(likingEntries)){
            arr = likingEntries.split(EntityObject.VALUES_SEPERATOR);
        }
        if(!ArrayUtils.contains(arr, grEntryId)){
            String[] arr1 = (String[])ArrayUtils.add(arr, grEntryId);
            likingEntries = Joiner.on(EntityObject.VALUES_SEPERATOR).join(arr1);
        }
    }

    public void addLikingEntry(String grEntryId){
        String[] arr = new String[]{};
        if(StringUtils.isEmpty(sharingEntries)){
            arr = sharingEntries.split(EntityObject.VALUES_SEPERATOR);
        }
        if(!ArrayUtils.contains(arr, grEntryId)){
            String[] arr1 = (String[])ArrayUtils.add(arr, grEntryId);
            sharingEntries = Joiner.on(EntityObject.VALUES_SEPERATOR).join(arr1);
        }
    }

    @Index(name="gruserid_index")
    public String getGrUserId() {
		return grUserId;
	}

	public void setGrUserId(String grUserId) {
		this.grUserId = grUserId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    @Column(name="likingEntries", length=10000)
    public String getLikingEntries() {
        return likingEntries;
    }

    public void setLikingEntries(String likingEntries) {
        this.likingEntries = likingEntries;
    }
    @Column(name="sharingEntries", length=10000)
    public String getSharingEntries() {
        return sharingEntries;
    }

    public void setSharingEntries(String sharingEntries) {
        this.sharingEntries = sharingEntries;
    }

    public Date getLastFetchTime() {
        return lastFetchTime;
    }

    public void setLastFetchTime(Date lastFetchTime) {
        this.lastFetchTime = lastFetchTime;
    }

    public boolean isCnUser() {
        return cnUser;
    }

    public void setCnUser(boolean cnUser) {
        this.cnUser = cnUser;
    }

    @Override
    public String toString() {
        return "GrUser{" +
                "grUserId='" + grUserId + '\'' +
                '}';
    }
}
