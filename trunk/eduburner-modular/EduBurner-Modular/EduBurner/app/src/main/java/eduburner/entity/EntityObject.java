package eduburner.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import com.google.gson.annotations.Expose;

@MappedSuperclass
public abstract class EntityObject implements Serializable {
	private static final long serialVersionUID = 3949782437769940769L;

	public static final String VALUES_SEPERATOR = ",";

	@Expose
	protected String id;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public abstract String toString();

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof EntityObject))
			return false;
		EntityObject entityObject = (EntityObject) o;
		return id.equals(entityObject.getId());
	}

	public int hashCode() {
		return id.hashCode();
	}
}
