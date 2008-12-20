package eduburner.feed.domain;

import java.util.Date;
import java.util.List;

import eduburner.core.EntityObject;
import eduburner.entry.domain.Entry;

public class Feed extends EntityObject {
	private static final long serialVersionUID = 367824212338403517L;
	
	private String url;
	private String feedId;
	private String title;
	private Date updated;
	private String link;

	public List<Entry> entries;

	public String getFeedId() {
		return feedId;
	}

	public void setFeedId(String feedId) {
		this.feedId = feedId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
}
