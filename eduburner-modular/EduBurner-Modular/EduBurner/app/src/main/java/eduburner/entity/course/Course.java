package eduburner.entity.course;

import java.util.Arrays;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.annotations.Expose;

import eduburner.entity.EntityObject;
import eduburner.entity.Entry;
import eduburner.entity.user.UserData;
import eduburner.enumerations.CourseStatus;

/**
 * 课程类，一门课程有多个学生，有讨论区和共享资源
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
	
	private String picture;
	
	private UserData creator;

	// 成员
	private List<UserData> members = Lists.newArrayList();

	private List<CourseResource> courseResources = Lists.newArrayList();

	private Set<CoursePermission> permissions = Sets.newHashSet();
	
	private List<Entry> entries = Lists.newArrayList();

	private List<CourseTag> tags = Lists.newArrayList();
	
	@Expose
	private String tagsAsString;

	@NotNull(message = "课程名不能为空！")
	@Size(min = 3, message = "课程名最少 {min}个字符")
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

	@ManyToMany(mappedBy="courses", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	public List<UserData> getMembers() {
		return members;
	}

	@OneToMany(mappedBy="course", cascade = CascadeType.ALL)
	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	public void setMembers(List<UserData> members) {
		this.members = members;
	}

	@OneToMany(mappedBy = "course", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	public List<CourseResource> getCourseResources() {
		return courseResources;
	}

	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	public Set<CoursePermission> getPermissions() {
		return permissions;
	}

	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "rel_course_coursetag", 
			joinColumns = { @JoinColumn(name = "course_id") }, 
			inverseJoinColumns = { @JoinColumn(name = "tag_id") })
	public List<CourseTag> getTags() {
		return tags;
	}
	
	@ManyToOne
	@JoinColumn(name = "fk_creator_id")
	public UserData getCreator() {
		return creator;
	}

	public void setCreator(UserData creator) {
		this.creator = creator;
	}

	public void setTags(List<CourseTag> tags) {
		this.tags = tags;
	}

	public void setPermissions(Set<CoursePermission> permissions) {
		this.permissions = permissions;
	}

	public void setCourseResources(List<CourseResource> courseResources) {
		this.courseResources = courseResources;
	}

	public void addMemeber(final UserData userData) {
		boolean containUser = Iterables.any(members, new Predicate<UserData>() {
			@Override
			public boolean apply(UserData input) {
				return userData.getId().equals(input.getId());
			}
		});
		
		if(!containUser){
			members.add(userData);
		}
	}

	public String getTagsAsString() {
		return tagsAsString;
	}

	public void setTagsAsString(String tagsAsString) {
		this.tagsAsString = tagsAsString;
	}
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@Transient
	public List<String> getTagsStringList(){
		if(tagsAsString == null){
			return Lists.newArrayList();
		}
		return Arrays.asList(tagsAsString.trim().split(EntityObject.VALUES_SEPERATOR));
	}

	@Override
	public String toString() {
		return "title: " +title + " description: " + description; 
	}

}
