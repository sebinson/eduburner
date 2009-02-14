package eduburner.entry.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import eduburner.core.EntityObject;

@Entity
@Table(name="via")
public class Via extends EntityObject {
	private static final long serialVersionUID = 6794354127987399201L;
	private String name;
	private String url;

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String toString() {
		return "Via{" + "name='" + name + '\'' + ", url='" + url + '\'' + '}';
	}
}
