package eduburner.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class SearchController {

	private static final String SEARCH_RESULT_VIEW = "searchResult";
	
	//private ISearchService searchService;
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(HttpServletRequest request){
		
		String q = request.getParameter("q");
		
		return SEARCH_RESULT_VIEW;
	}
	
}
