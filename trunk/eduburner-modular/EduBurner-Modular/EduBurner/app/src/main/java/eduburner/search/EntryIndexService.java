package eduburner.search;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.springmodules.lucene.index.factory.IndexFactory;
import org.springmodules.lucene.index.factory.LuceneIndexReader;
import org.springmodules.lucene.index.factory.LuceneIndexWriter;

import eduburner.entity.Entry;

public class EntryIndexService implements IEntryIndexService{
	
	private IndexFactory indexFactory;
	private Analyzer analyzer;

	@Override
	public void indexEntries(final List<Entry> entries){
		try {
			LuceneIndexReader reader = indexFactory.getIndexReader();
			LuceneIndexWriter writer = indexFactory.getIndexWriter();
			
			IndexWriter.unlock(writer.getDirectory());
			
			for (Entry entry : entries) {

				reader.deleteDocuments(new Term(SearchConstants.FIELD_ENTRY_ID,
						entry.getId()));

				Document doc = new Document();
				doc
						.add(new Field(SearchConstants.FIELD_ENTRY_ID, entry
								.getId(), Field.Store.NO,
								Field.Index.NOT_ANALYZED));
				doc.add(new Field(SearchConstants.FIELD_ENTRY_TITLE, entry
						.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
				writer.addDocument(doc);

				reader.close();
				writer.optimize();
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setIndexFactory(IndexFactory indexFactory) {
		this.indexFactory = indexFactory;
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
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
