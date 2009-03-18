package eduburner.entity.course;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import eduburner.entity.EntityObject;
import eduburner.entity.user.UserData;
import eduburner.enumerations.CourseStatus;

/**
 * 课程类，一门课程有多个学生，有讨论区和共享资源
 * 
 * 能mashup的就mashup
 * 
 * 日历借助google calendar 每个人的学习笔记借助google notebook? 文章分享借助google reader
 * 
 * 每位同学需要有一个空间，列出其所在的课程，学生可以写微博客 是否需要建立好友关系？
 * 
 * @author zhangyf@gmail.com
 */

@Entity
@Table(name = "course")
public class Course extends EntityObject {

	private static final long serialVersionUID = -1025308879798159789L;

	private String title;

	private String description;

	// This might be open, closed, planned, or discontinued, for example
	private CourseStatus status;

	private Date startDate;

	private Date endDate;

	// 成员
	private List<UserData> members = Lists.newArrayList();

	private List<CourseResource> courseResources = Lists.newArrayList();
	
	private Set<CoursePermission> permissions = Sets.newHashSet();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CourseStatus getStatus() {
		return status;
	}

	public void setStatus(CourseStatus status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
	@JoinTable(name = "course_member", joinColumns = { @JoinColumn(name = "course_id") }, inverseJoinColumns = { @JoinColumn(name = "member_id") })
	public List<UserData> getMembers() {
		return members;
	}

	public void setMembers(List<UserData> members) {
		this.members = members;
	}

	@OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public List<CourseResource> getCourseResources() {
		return courseResources;
	}
	
	@OneToMany(mappedBy="course", fetch=FetchType.LAZY)
	public Set<CoursePermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<CoursePermission> permissions) {
		this.permissions = permissions;
	}

	public void setCourseResources(List<CourseResource> courseResources) {
		this.courseResources = courseResources;
	}
	
	public void addMemeber(UserData userData){
		this.members.add(userData);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("title", title).toString();
	}
	
}
