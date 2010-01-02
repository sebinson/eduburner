package burnerweb.model;

import java.io.Serializable;

import javax.persistence.*;

import com.google.gson.annotations.Expose;

@MappedSuperclass
public abstract class EntityObject implements Serializable {
	private static final long serialVersionUID = 3949782437769940769L;

	public static final String VALUES_SEPERATOR = ",";

	@Expose
	protected Long id;

	@Id
	@Column(length = 32)
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
		return id.equals(entityObject.getId());
	}

	public int hashCode() {
		if(id != null){
			return id.hashCode();
		}else{
			return 0;
		}
	}
}
