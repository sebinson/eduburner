package eduburner.components;

import eduburner.core.EntityObject;
import eduburner.util.HtmlUtil;

public class MediaContent extends EntityObject{
    private static final long serialVersionUID = 8339618754895554627L;
	private String url;
    private String type;
    private int width = -1;
    private int height = -1;

    public MediaContent() {
    	super();
    }
    
    public MediaContent(String url, String type, int width, int height) {
        this.url = url;
        this.type = type;
        this.width = width;
        this.height = height;
    }


    public void setUrl(String url)
	{
    		this.url = url;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public void setWidth(int width)
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}

	public String getUrl() {
		

		if (isImgTag())
		{
			return HtmlUtil.getUrlFromImgTag(url);  
		}
		else
		{
			return url;
		}
		
    }

	public boolean isImgTag()
	{
		return HtmlUtil.isImgTag(this.url);
	}
	
	public String getImgTag()
	{
		if (isImgTag())
		{
			return this.url;
		}
		else
		{
			throw new IllegalStateException("not an img tag");
		}
	}
	
    public String getType() {
        return type;
    }

    public boolean isImage() {
    	if (type == null)
    	{
    		return false;
    	}
    	else
    	{
    		return type.toLowerCase().startsWith("image");
    	}
    }
    
    public boolean isJpeg() {
    	if (type == null)
    	{
    		return false;
    	}
    	else
    	{
    		return type.equalsIgnoreCase("image/jpeg");
    	}
    }
    
    public boolean isGif() {
    	if (type == null)
    	{
    		return false;
    	}
    	else
    	{
    		return type.equalsIgnoreCase("image/gif");
    	}
    }
    
    public boolean isPng() {
    	if (type == null)
    	{
    		return false;
    	}
    	else
    	{
    		return type.equalsIgnoreCase("image/png");
    	}
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


	public String toString() {
        return "MediaContent{" +
                "height=" + height +
                ", width=" + width +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
