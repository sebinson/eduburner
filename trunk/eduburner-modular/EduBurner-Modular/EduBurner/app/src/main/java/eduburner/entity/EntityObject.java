package eduburner.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class EntityObject implements Serializable {
	private static final long serialVersionUID = 3949782437769940769L;

	protected Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public abstract String toString();

	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof EntityObject))
			return false;
		EntityObject entityObject = (EntityObject) o;
		return id == entityObject.getId();
	}

	public int hashCode() {
		long idValue;
		if (null != id) {
			idValue = id.longValue();
		} else {
			idValue = 0;
		}
		return (int) (idValue ^ idValue >>> 32);
	}
}
