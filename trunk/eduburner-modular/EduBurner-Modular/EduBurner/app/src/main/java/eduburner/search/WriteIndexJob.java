package eduburner.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springmodules.lucene.index.factory.IndexFactory;

import eduburner.entity.user.User;

/**
 * 定时写索引
 * @author zhangyf@gmail.com
 *
 */
public class WriteIndexJob extends QuartzJobBean{
	
	@Autowired
	@Qualifier("indexFactory")
	private IndexFactory indexFactory;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		
	}
	
	private Document buildDocument(User user) {
		Document doc = new Document();
		String id = user.getId();
		String username = user.getUsername();
		String fullName = user.getFullname();
		doc.add(new Field(SearchConstants.FIELD_USER_ID, id,
				Field.Store.NO, Field.Index.NOT_ANALYZED));
		doc.add(new Field(SearchConstants.FIELD_USER_USERNAME, username,
				Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field(SearchConstants.FIELD_USER_FULLNAME, fullName,
				Field.Store.NO, Field.Index.ANALYZED));
		return doc;
	}

	
}
