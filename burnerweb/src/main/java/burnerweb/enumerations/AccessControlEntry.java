package burnerweb.enumerations;

public enum AccessControlEntry {
	
	NOTSET,
	ALLOW,
	DENY;
	
	public long val(){
		switch(this){
		case NOTSET:
			return 0x00;
		case ALLOW:
			return 0x01;
		case DENY:
			return 0x02;
		default:
			return 0x00;
		}
	}
	
	public static AccessControlEntry fromVal(long val){
		if(val == 0x00){
			return NOTSET;
		}else if(val == 0x01){
			return ALLOW;
		}else if(val == 0x02){
			return DENY;
		}else{
			return NOTSET;
		}
	}
}
