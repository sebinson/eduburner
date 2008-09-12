package eduburner.section.domain;

import eduburner.core.EntityObject;
import eduburner.user.domain.UserData;

/**
 * 用户个人空间
 * @author zhangyf@gmail.com
 */
public class Section extends EntityObject {

	private static final long serialVersionUID = -3555661438054103397L;
	//space owner
	private UserData owner;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
