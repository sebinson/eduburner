package burnerweb.enumerations;

public enum RoleType {
	User,
	CourseAdmin,
	SystemAdmin;
	
	public String toString(){
		return "ROLE_" + this.name();
	}
}
