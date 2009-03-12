package eduburner.entity.entry;

import javax.persistence.Entity;
import javax.persistence.Table;

import eduburner.entity.EntityObject;

@Entity
@Table(name="enclosure")
public class Enclosure extends EntityObject {
	private static final long serialVersionUID = -3164951260165938724L;
	private String type;
	private String url;
	private Integer length;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer l) {
		this.length = l;
	}

	public String toString() {
		return "Enclosure{" + "url=" + url + ",type=" + type + "}";
	}
}
