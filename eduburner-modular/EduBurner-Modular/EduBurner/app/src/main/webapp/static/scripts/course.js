(function($){
	var D = YAHOO.util.Dom;
	var E = YAHOO.util.Event;
	
	function init(){
		$('#c-' + window.EB_MODELINFO.courseId).addClass('selected');
	}
	
    E.onDOMReady(function(){
	     init();
	});
})(jQuery);