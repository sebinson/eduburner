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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.annotations.Expose;

import eduburner.entity.EntityObject;
import eduburner.entity.Entry;
import eduburner.entity.user.UserData;
import eduburner.enumerations.CourseStatus;
import eduburner.json.JsonHelper;

/**
 * 课程类，一门课程有多个学生，有讨论区和共享资源
 * 
 * 能mashup的就mashup
 * 
 * 日历借助google calendar 每个人的学习笔记借助google notebook? 文章分享借助google reader
 * 
 * 每位同学需要有一个空间，列出其所在的课程，学生可以写微博客 是否需要建立好友关系？
 * 
 * 课程的计划，进度，考试，如何管理呢？
 * 
 * @author zhangyf@gmail.com
 */

@Entity
@Table(name = "course")
public class Course extends EntityObject {

	private static final long serialVersionUID = -1025308879798159789L;

	@Expose
	private String title;
	@Expose
	private String description;

	@Expose
	private CourseStatus status;
	@Expose
	private Date startDate;
	@Expose
	private Date endDate;
	
	private UserData creator;

	// 成员
	private List<UserData> members = Lists.newArrayList();

	private List<CourseResource> courseResources = Lists.newArrayList();

	private Set<CoursePermission> permissions = Sets.newHashSet();
	
	private List<Entry> entries = Lists.newArrayList();

	@Expose
	private List<CourseTag> tags = Lists.newArrayList();

	@NotNull(message = "课程名不能为空！")
	@Length(min = 3, message = "课程名最少 {min}个字符")
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

	@OneToMany(mappedBy="course", cascade = CascadeType.REMOVE)
	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public void setMembers(List<UserData> members) {
		this.members = members;
	}

	@OneToMany(mappedBy = "course", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
	public List<CourseResource> getCourseResources() {
		return courseResources;
	}

	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	public Set<CoursePermission> getPermissions() {
		return permissions;
	}

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "rel_course_coursetag", joinColumns = { @JoinColumn(name = "course_id") }, inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	public List<CourseTag> getTags() {
		return tags;
	}
	
	@ManyToOne()
	@JoinColumn(name = "fk_creator_id")
	public UserData getCreator() {
		return creator;
	}
	

	public void setCreator(UserData creator) {
		this.creator = creator;
	}

	public void setTags(List<CourseTag> courses) {
		this.tags = courses;
	}

	public void setPermissions(Set<CoursePermission> permissions) {
		this.permissions = permissions;
	}

	public void setCourseResources(List<CourseResource> courseResources) {
		this.courseResources = courseResources;
	}

	public void addMemeber(UserData userData) {
		this.members.add(userData);
		userData.getCourses().add(this);
	}

	@Override
	public String toString() {
		return "title: " +title + " description: " + description; 
	}

}
