package eduburner.enumerations;

public enum Permission {
	VIEW,
	CREATE,
	EDIT,
	REMOVE,
	DELETE,
	SET_PERMISSIONS,
	ADMIN,
	SYSTEM_ADMIN;
	
	public long val(){
		switch(this){
		case VIEW:
			return 0x0000000000000001L;
		case CREATE:
			return 0x0000000000000002L;
		case EDIT:
			return 0x0000000000000004L;
		case REMOVE:
			return 0x0000000000000008L;
		case DELETE:
			return 0x0000000000000010L;
		case SET_PERMISSIONS:
			return 0x0000000000000020L;
		case ADMIN:
			return 0x0100000000000000L;
		case SYSTEM_ADMIN:
			return 0x4000000000000000L;
		default:
			return 0L;
		}
	};
	
	public static Permission fromVal(long val){
		if(val == 0x0000000000000001L){
			return VIEW;
		}else if(val == 0x0000000000000002L){
			return CREATE;
		}else if(val == 0x0000000000000004L){
			return EDIT;
		}else if(val == 0x0000000000000008L){
			return REMOVE;
		}else if(val == 0x0000000000000010L){
			return DELETE;
		}else if(val == 0x0000000000000020L){
			return SET_PERMISSIONS;
		}else if(val == 0x0100000000000000L){
			return ADMIN;
		}else if(val == 0x4000000000000000L){
			return SYSTEM_ADMIN;
		}else{
			return null;
		}
	}
}
