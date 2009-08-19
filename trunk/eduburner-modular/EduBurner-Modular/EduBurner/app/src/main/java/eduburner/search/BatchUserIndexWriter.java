package eduburner.search;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springmodules.lucene.index.factory.IndexFactory;

import eduburner.entity.user.User;

@Component("userIndexWriter")
public class BatchUserIndexWriter implements ItemWriter<User> {

	@Autowired
	@Qualifier("indexFactory")
	private IndexFactory indexFactory;

	@Override
	public void write(List<? extends User> users) throws Exception {
		for (User user : users) {
			indexFactory.getIndexWriter().addDocument(buildDocument(user));
		}
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
