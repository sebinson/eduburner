(function($){
	var D = YAHOO.util.Dom;
	var E = YAHOO.util.Event;
	
	function init(){
		$('#searchFriendInput').trigger('focus');
	}
	
	function onButtonReady() { 
        var searchCourseBtn = new YAHOO.widget.Button("search-friend-button"); 
    } 
    E.onContentReady("search-friend-button", onButtonReady);
	
    E.onDOMReady(function(){
	     init();
	});
})(jQuery);