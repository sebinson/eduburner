package eduburner.entity.entry;

import javax.persistence.Entity;
import javax.persistence.Table;

import eduburner.entity.EntityObject;
import eduburner.util.HtmlUtil;

@Entity
@Table(name="media_thumbnail")
public class MediaThumbnail extends EntityObject {
	private static final long serialVersionUID = -7214123833351476904L;
	private String url;
	private int width = -1;
	private int height = -1;

	public MediaThumbnail() {
		super();
	}

	public MediaThumbnail(String url, int width, int height) {
		this.url = url;
		this.width = width;
		this.height = height;
	}

	public boolean isImgTag() {
		return HtmlUtil.isImgTag(this.url);
	}

	public String getImgTag() {
		if (isImgTag()) {
			return this.url;
		} else {
			throw new IllegalStateException("not an img tag");
		}
	}

	public String getUrl() {

		if (isImgTag()) {
			return HtmlUtil.getUrlFromImgTag(url);
		} else {
			return url;
		}

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setUrl(String url) {
		this.url = url;

	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String toString() {
		return "MediaThumbnail{" + "height=" + height + ", width=" + width
				+ ", url='" + url + '\'' + '}';
	}
}
