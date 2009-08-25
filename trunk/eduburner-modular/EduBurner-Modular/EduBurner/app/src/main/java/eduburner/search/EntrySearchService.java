package eduburner.search;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.store.Directory;
import org.springframework.beans.factory.InitializingBean;

import eduburner.entity.Entry;

public class EntrySearchService implements IEntrySearchService, InitializingBean{
	
	private Directory directory;
	private Analyzer analyzer;

	@Override
	public void indexEntries(final List<Entry> entries){
		try {
			IndexWriter writer = new IndexWriter(directory, analyzer,
					MaxFieldLength.LIMITED);

			IndexWriter.unlock(directory);
			IndexReader reader = IndexReader.open(directory, false);

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
				
			}
			
			reader.close();
			writer.optimize();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void addEntryDocument(final Entry entry){
		
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
	}

	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}

	
}
