(function($){
	var D = YAHOO.util.Dom;
	var E = YAHOO.util.Event;
	
	var tabView = null;
	
	function init(){
		$('#c-' + window._EB_COURSEVIEW_DATA.courseId).addClass('selected');
		tabView = new YAHOO.widget.TabView('single-course');
		
		$('#post-msg-area').trigger('focus');
	}
	
	function onButtonReady() { 
        var submitMsgBtn = new YAHOO.widget.Button("submit-msg-button"); 
    } 
    E.onContentReady("submit-msg-button", onButtonReady);
    E.onDOMReady(function(){
	     init();
	});
})(jQuery);