package eduburner.search;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriter.MaxFieldLength;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.QueryParser.Operator;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;
import org.springmodules.lucene.index.LuceneIndexAccessException;

import eduburner.entity.Entry;

public class EntrySearchService implements IEntrySearchService, InitializingBean{

	private static final int MAX_SEARCH_RESULT_COUNT = 100;
	private static final Logger logger = LoggerFactory.getLogger(EntrySearchService.class);
	private static final int DEFAULT_PHRASE_SLOPE = 2;
	
	private Directory directory;
	private Analyzer analyzer;
	
	private IndexReader indexReader;
	private Searcher searcher;
	
	private int phraseSlop;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		phraseSlop = DEFAULT_PHRASE_SLOPE;
		
		boolean exist = IndexReader.indexExists(directory);
		if(!exist){
			createIndex();
		}
		refreshSearcher();
	}
	
	@Override
	public List<Entry> searchEntry(String query){
		Assert.notNull(searcher);
		
		if(StringUtils.isBlank(query)){
			return null;
		}
		QueryParser parser = getQueryParser();
		try {
			Query q = parser.parse(query);
			TopDocs topDocs = searcher.search(q, (Filter)null, MAX_SEARCH_RESULT_COUNT);
			int hitCount = topDocs.scoreDocs.length;
			for(int i=0; i<hitCount; i++){
				Document doc = searcher.doc(topDocs.scoreDocs[i].doc);
				Entry e = new Entry();
				String title = doc.get(SearchConstants.FIELD_ENTRY_TITLE);
				e.setTitle(title);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * 用于定期更新索引
	 */
	@Override
	public void indexEntries(final List<Entry> entries){
		try {
			IndexWriter writer = new IndexWriter(directory, analyzer,
					MaxFieldLength.LIMITED);
			
			checkIndexLocking();
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
			
			refreshSearcher();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void addEntryDocument(final Entry entry){
		
	}
	
	public void setAnalyzer(Analyzer analyzer) {
		this.analyzer = analyzer;
	}

	public void setDirectory(Directory directory) {
		this.directory = directory;
	}
	
	private void createIndex() {
		IndexWriter indexWriter = null;
		try {
			indexWriter = new IndexWriter(directory, analyzer, true, MaxFieldLength.UNLIMITED);
		} catch(Exception ex) {
			throw new LuceneIndexAccessException("Error during creating a non existent index", ex);
		} finally {
			try {
				if( indexWriter!=null ) {
					indexWriter.close();
				}
			} catch (IOException ex) {
				throw new LuceneIndexAccessException("Error during closing the writer", ex);
			}
		}
	}

	private void refreshSearcher() throws CorruptIndexException, IOException{
		if(indexReader == null){
			indexReader = IndexReader.open(directory, true);
		}else{
			if(!indexReader.isCurrent()){
				indexReader.reopen();
			}
		}
		searcher = new IndexSearcher(indexReader);
	}
	
	private void checkIndexLocking() throws IOException {
		boolean locked = IndexWriter.isLocked(directory);
		if( locked ) {
			IndexWriter.unlock(directory);
		}
	}
	
	private QueryParser getQueryParser(){
		QueryParser parser = new MultiFieldQueryParser(new String[]{
			SearchConstants.FIELD_ENTRY_ID, SearchConstants.FIELD_ENTRY_TITLE
		}, analyzer);
		
		parser.setPhraseSlop(phraseSlop);
		parser.setDefaultOperator(Operator.AND);
		
		return parser;
	}
}
