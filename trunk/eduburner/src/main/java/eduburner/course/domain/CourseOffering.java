package eduburner.course.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

import eduburner.core.EntityObject;

/**
 * 课程类，一门课程有多个学生，有讨论区和共享资源
 * @author zhangyf@gmail.com
 */
public class CourseOffering extends EntityObject {

	private static final long serialVersionUID = -1025308879798159789L;
	
	private String title;
	
	private String description;
	
	//This might be open, closed, planned, or discontinued, for example
	private String status;
	
	private Date startDate;
	
	private Date endDate;

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("title", title).toString();
	}

}
