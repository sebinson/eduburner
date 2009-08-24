package eduburner.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import eduburner.search.ISearchService;

@Controller
public class SearchController {

	private static final String SEARCH_RESULT_VIEW = "searchResult";
	
	@Autowired
	@Qualifier("searchService")
	private ISearchService searchService;
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public String search(HttpServletRequest request){
		
		String q = request.getParameter("q");
		
		return SEARCH_RESULT_VIEW;
	}
	
}
