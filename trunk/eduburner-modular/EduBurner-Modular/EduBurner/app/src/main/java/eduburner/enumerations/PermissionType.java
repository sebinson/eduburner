package eduburner.enumerations;

public enum PermissionType {
	VIEW,
	CREATE,
	EDIT,
	REMOVE,
	DELETE,
	SET_PERMISSIONS,
	ADMIN;
	
	public long val(){
		switch(this){
		case VIEW:
			return 0x0000000000000001;
		case CREATE:
			return 0x0000000000000002;
		case EDIT:
			return 0x0000000000000004;
		case REMOVE:
			return 0x0000000000000008;
		case DELETE:
			return 0x0000000000000010;
		case SET_PERMISSIONS:
			return 0x0000000000000020;
		default:
			return 0L;
		}
	};
	
	public static PermissionType fromVal(long val){
		if(val == 0x0000000000000001){
			return VIEW;
		}else if(val == 0x0000000000000002){
			return CREATE;
		}else if(val == 0x0000000000000004){
			return EDIT;
		}else if(val == 0x0000000000000008){
			return REMOVE;
		}else if(val == 0x0000000000000010){
			return DELETE;
		}else if(val == 0x0000000000000020){
			return SET_PERMISSIONS;
		}else{
			return null;
		}
	}
}
