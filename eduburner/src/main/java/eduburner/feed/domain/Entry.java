package eduburner.feed.domain;

import java.util.ArrayList;
import java.util.List;

import eduburner.core.EntityObject;

public class Entry extends EntityObject {

	private static final long serialVersionUID = -1106338683739123478L;
	
	private List<Content> contents = new ArrayList<Content>();

	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

}
