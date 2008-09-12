package eduburner.section.domain;

import eduburner.core.EntityObject;
import eduburner.user.domain.UserData;

/**
 * 可以是用户的个人空间，课程空间等
 * @author zhangyf@gmail.com
 */
public class Section extends EntityObject {

	private static final long serialVersionUID = -3555661438054103397L;
	
	private String name;
	
	private String description;
	
	//space owner
	private UserData owner;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
