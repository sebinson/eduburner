package eduburner.feed.domain;

import eduburner.core.EntityObject;

public class MediaContent extends EntityObject{
	private static final long serialVersionUID = 8656532455176530531L;
	private String url;
	private String type; //enum?
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


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
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
