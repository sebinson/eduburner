(function($){
	var D = YAHOO.util.Dom;
	var E = YAHOO.util.Event;
	
	function init(){
		$('#searchCourseInput').trigger('focus');
	}
	
	function onButtonReady() { 
        var searchCourseBtn = new YAHOO.widget.Button("search-course-button"); 
    } 
    E.onContentReady("search-course-button", onButtonReady);
	
    E.onDOMReady(function(){
	     init();
	});
})(jQuery);