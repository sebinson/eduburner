package eduburner.util;

public class Thumbnail {
	private int height;
	private int width;
	private String filename;

	public Thumbnail(int height, int width, String fileName) {
		this.height = height;
		this.width = width;
		filename = fileName;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public String getFilename() {
		return filename;
	}

	public String toString() {
		return "Thumbnail " + filename + " width:" + width + " height:"
				+ height;
	}

}
