package eduburner.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.google.common.collect.Lists;

import eduburner.enumerations.ServiceType;

@Entity
@Table(name = "service")
public class Service extends EntityObject {

	private static final long serialVersionUID = -8248602365005915433L;
	private String name;
	private ServiceType type;
	private String iconUrl;
	private String profileUrl;

	private List<Entry> entries = Lists.newArrayList();
	
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
	
	@OneToMany(mappedBy="service",fetch=FetchType.LAZY)
	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	@Override
	public String toString() {
		return "name" + type + "url";
	}

}
