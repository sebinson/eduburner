package eduburner.course.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import eduburner.core.EntityObject;
import eduburner.user.domain.UserData;

/**
 * 课程类，一门课程有多个学生，有讨论区和共享资源
 * 
 * 能mashup的就mashup
 * 
 * 日历借助google calendar
 * 每个人的学习笔记借助google notebook
 * 文章分享借助google reader
 * 
 * 课程需要一个论坛
 * 
 * 每位同学需要有一个空间，列出其所在的课程，学生可以写微博客
 * 是否需要建立好友关系？
 * 
 * @author zhangyf@gmail.com
 */
public class CourseOffering extends EntityObject {

	private static final long serialVersionUID = -1025308879798159789L;

	private String title;

	private String description;

	// This might be open, closed, planned, or discontinued, for example
	private String status;

	private Date startDate;

	private Date endDate;

	//成员
	private List<UserData> members = new ArrayList<UserData>();

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("title", title).toString();
	}

}
