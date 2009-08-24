package eduburner.search;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.springmodules.lucene.index.core.DocumentCreator;
import org.springmodules.lucene.index.support.LuceneIndexSupport;

import eduburner.entity.Entry;
import eduburner.entity.user.UserData;

public class IndexService extends LuceneIndexSupport implements IIndexService{

	@Override
	public void addEntryDocument(final Entry entry){
		getLuceneIndexTemplate().addDocument(new DocumentCreator() {
			@Override
			public Document createDocument() throws Exception {
				Document doc = new Document();
				doc
						.add(new Field(SearchConstants.FIELD_ENTRY_ID, entry
								.getId(), Field.Store.NO,
								Field.Index.NOT_ANALYZED));
				doc.add(new Field(SearchConstants.FIELD_ENTRY_TITLE, entry
						.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
				return doc;
			}
		});
	}
	
	@Override
	public void addUserDocument(final UserData user){
		getLuceneIndexTemplate().addDocument(new DocumentCreator() {
			@Override
			public Document createDocument() throws Exception {
				Document doc = new Document();
				doc.add(new Field(SearchConstants.FIELD_USER_ID, user.getId(),
						Field.Store.NO, Field.Index.NOT_ANALYZED));
				doc.add(new Field(SearchConstants.FIELD_USER_USERNAME, user.getUsername(),
						Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field(SearchConstants.FIELD_USER_FULLNAME, user.getFullname(),
						Field.Store.NO, Field.Index.ANALYZED));
				return doc;
			}
		});
	}
	
	@Override
	public void purgeIndex() {
		getLuceneIndexTemplate().deleteDocuments(new Term("",""));
	}
}
