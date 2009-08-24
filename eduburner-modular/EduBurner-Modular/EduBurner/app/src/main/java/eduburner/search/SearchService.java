package eduburner.search;

import java.util.List;

import org.apache.lucene.document.Document;
import org.springmodules.lucene.search.core.HitExtractor;
import org.springmodules.lucene.search.core.ParsedQueryCreator;
import org.springmodules.lucene.search.support.LuceneSearchSupport;

/**
 * TODO: 需要注入analyzer和searcherFactory
 * @author zhangyf@gmail.com
 */

public class SearchService extends LuceneSearchSupport implements ISearchService{
	
	@Override
	public List search(final String fieldName,final String textToSearch) {
		return getLuceneSearcherTemplate().search(new ParsedQueryCreator() {
			public QueryParams configureQuery() {
				return new QueryParams(fieldName, textToSearch);
			}
		}, new HitExtractor() {
			public Object mapHit(int id, Document document, float score) {
				String s = document.get(SearchConstants.FIELD_ENTRY_TITLE);
				if( s!=null ) {
					return s;
				} else {
					return "not found";
				}
			}
		});
	}
	
}
