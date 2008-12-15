package eduburner.feed.domain;

import eduburner.core.EntityObject;

public class Thumbnail extends EntityObject {

	private static final long serialVersionUID = 7219685444777536127L;
	private String url;
	private int width;
	private int height;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
