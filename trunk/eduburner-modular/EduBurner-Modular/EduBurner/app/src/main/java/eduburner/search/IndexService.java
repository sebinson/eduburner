package eduburner.search;

import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.springmodules.lucene.index.core.DocumentsCreator;
import org.springmodules.lucene.index.support.LuceneIndexSupport;

import com.google.common.collect.Lists;

import eduburner.entity.Entry;

public class IndexService extends LuceneIndexSupport implements IIndexService{

	@Override
	public void indexEntries(final List<Entry> entries){
		getLuceneIndexTemplate().addDocuments(new DocumentsCreator() {
			@Override
			public List<Document> createDocuments() throws Exception {
				List<Document> docs = Lists.newArrayList();
				for (Entry entry : entries) {
					Document doc = new Document();
					doc
							.add(new Field(SearchConstants.FIELD_ENTRY_ID,
									entry.getId(), Field.Store.NO,
									Field.Index.NOT_ANALYZED));
					doc
							.add(new Field(SearchConstants.FIELD_ENTRY_TITLE,
									entry.getTitle(), Field.Store.YES,
									Field.Index.ANALYZED));
					docs.add(doc);
				}
				return docs;
			}
		});
		
		optimize();
	}
	
	@Override
	public void optimize(){
		getLuceneIndexTemplate().optimize();
	}
	
	@Override
	public void purgeIndex() {
		getLuceneIndexTemplate().deleteDocuments(new Term("",""));
	}
	
	/*
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
	}*/
}
