package eduburner.propertyeditor;

import java.beans.PropertyEditorSupport;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import eduburner.entity.course.CourseTag;
import eduburner.service.course.ICourseManager;
import eduburner.util.SpringBeanUtils;

/**
 * do not need this any more
 */
@Deprecated
public class CourseTagsPropertyEditor extends PropertyEditorSupport {

	private static final String DEFAULT_SEPARATOR = ",";

	private static final Logger logger = LoggerFactory
			.getLogger(CourseTagsPropertyEditor.class);
	
	private ICourseManager courseManager;

	public CourseTagsPropertyEditor() {
		courseManager = (ICourseManager)SpringBeanUtils.getBean("courseManager");
	}

	@Override
	public void setAsText(String text) {
		logger.debug("entering setAsText method...");
		logger.debug("text is: " +text);
		String[] tagNames = text.trim().split(DEFAULT_SEPARATOR);
		
		List<CourseTag> tagList = Lists.newArrayList();
		
		for(String tagName : tagNames){
			CourseTag tag = courseManager.getOrInsertCourseTag(tagName);
			tagList.add(tag);
		}
		
		setValue(tagList);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getAsText() {
		logger.debug("entering getAsText method...");
		List<CourseTag> tags = (List<CourseTag>) getValue();
		if (null == tags || tags.size() == 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder("");
		for (int i = 0; i < tags.size(); i++) {
			sb.append(tags.get(i).getName());
			if (i > 0) {
				sb.append(DEFAULT_SEPARATOR);
			}
		}
		logger.debug("build tag string: " + sb.toString());
		return sb.toString();
	}
}
