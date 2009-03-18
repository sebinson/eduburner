package eduburner.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import eduburner.enumerations.ServiceType;

@Entity
@Table(name = "service")
public class Service extends EntityObject {

	private static final long serialVersionUID = -8248602365005915433L;
	private String name;
	private ServiceType type;
	private String iconUrl;
	private String profileUrl;

	public String getName() {
		return name;
	}

	public ServiceType getType() {
		return type;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(ServiceType type) {
		this.type = type;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	@Override
	public String toString() {
		return "name" + type + "url";
	}
}
