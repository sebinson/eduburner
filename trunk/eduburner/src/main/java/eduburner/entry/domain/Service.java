package eduburner.entry.domain;

import eduburner.core.EntityObject;

/**
 * 服务类型，google reader, delicious...
 * @author zhangyf@gmail.com
 *
 */
public class Service extends EntityObject {

	private static final long serialVersionUID = -2738135912576421035L;
	private String serviceId;
	private String name;
	private String iconUrl;
	private String profileUrl;

	public Service() {
	}

	public String getName() {
		return name;
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

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String toString() {
		return "Service{" + "id='" + serviceId + '\'' + ", name='" + name
				+ '\'' + ", iconUrl='" + iconUrl + '\'' + ", profileUrl='"
				+ profileUrl + '\'' + '}';
	}

}
