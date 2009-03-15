package eduburner.entity.user;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.ToStringBuilder;

import eduburner.entity.EntityObject;
import eduburner.enumerations.AccessControlEntry;
import eduburner.enumerations.PermissionType;

@Entity
@Table(name = "permission")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "acl_class", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("eduburner.entity.user.PermissionBase")
public abstract class PermissionBase extends EntityObject {

	private static final long serialVersionUID = 1072287163954242724L;

	// borrowed from community server's SecurityEnums.cs
	protected long allowMask;

	protected long denyMask;

	protected String description;

	protected Role role;

	public PermissionBase() {
	}

	public PermissionBase(Role role) {
		this.role = role;
		allowMask = 0L;
		denyMask = -1L;
	}

	public PermissionBase(Role role, PermissionType allowMask, PermissionType denyMask) {
		this.role = role;
		this.allowMask = allowMask.val();
		this.denyMask = denyMask.val();
	}

	// borrowed from community server's PermissionBase.cs
	@Transient
	public boolean getBit(PermissionType mask) {
		boolean bReturn = false;

		if ((denyMask & mask.val()) == mask.val())
			bReturn = false;

		if ((allowMask & mask.val()) == mask.val())
			bReturn = true;

		return bReturn;
	}

	public void setBit(PermissionType mask, AccessControlEntry accessControl) {
		
		if (accessControl == AccessControlEntry.ALLOW) {
			allowMask |= (long) mask.val() & (long) -1;
			denyMask &= ~(long) mask.val() & (long) -1;
		} else if (accessControl == AccessControlEntry.NOTSET) {
			allowMask &= ~(long) mask.val() & (long) -1;
			denyMask &= ~(long) mask.val() & (long) -1;
		} else {
			allowMask &= ~(long) mask.val() & (long) -1;
			denyMask |= (long) mask.val() & (long) -1;
		}
	}

	public void merge(PermissionBase permissionBase) {
		this.allowMask |= permissionBase.getAllowMask();
		this.denyMask |= permissionBase.getDenyMask();
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getAllowMask() {
		return allowMask;
	}

	public void setAllowMask(long allowMask) {
		this.allowMask = allowMask;
	}

	public long getDenyMask() {
		return denyMask;
	}

	public void setDenyMask(long denyMask) {
		this.denyMask = denyMask;
	}

	@Transient
	public PermissionType getAllowMaskPermission(){
		return PermissionType.fromVal(allowMask);
	}
	
	@Transient
	public PermissionType getDenyMaskPermission(){
		return PermissionType.fromVal(denyMask);
	}
	
	@ManyToOne
	@JoinColumn(name = "fk_role_id")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public boolean equals(Object obj){
		boolean isEqual = true;
        PermissionBase permissionBase = (PermissionBase)obj;
        if (permissionBase == null && this != null)
            return isEqual;
        
        for(PermissionType permission : PermissionType.values()){
        	if(permissionBase.getBit(permission) != this.getBit(permission)){
        		isEqual = false;
        		break;
        	}
        }
        return isEqual;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("id", id).append("description",
				description).toString();
	}
}
