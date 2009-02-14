package eduburner.entry.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import eduburner.core.EntityObject;

@Entity
@Table(name="media")
public class Media extends EntityObject {
	private static final long serialVersionUID = -2692282673433144788L;
	private String title;
	private String player;
	private String link;
	private List<MediaThumbnail> thumbnails;
	private List<MediaContent> content;
	private List<Enclosure> enclosures;

	public Media() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public String getPlayer() {
		return player;
	}

	public List<MediaThumbnail> getThumbnails() {
		if (thumbnails == null) {
			thumbnails = new ArrayList<MediaThumbnail>();
		}
		return thumbnails;
	}

	public List<MediaContent> getContent() {
		if (content == null) {
			content = new ArrayList<MediaContent>();
		}
		return content;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public void setThumbnails(List<MediaThumbnail> thumbnails) {
		this.thumbnails = thumbnails;
	}

	public void setContent(List<MediaContent> content) {
		this.content = content;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<Enclosure> getEnclosures() {
		if (enclosures == null) {
			enclosures = new ArrayList<Enclosure>();
		}

		return enclosures;
	}

	public void setEnclosures(List<Enclosure> enclosures) {
		this.enclosures = enclosures;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
