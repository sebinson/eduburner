(function($){
	var D = YAHOO.util.Dom;
	var E = YAHOO.util.Event;
	
	function onButtonReady() { 
        var submitMsgBtn = new YAHOO.widget.Button("submit-msg-button"); 
    } 
    E.onContentReady("submit-msg-button", onButtonReady);
    E.onDOMReady(function(){
	     $('#home').addClass('selected');
	     $('#post-msg-area').trigger('focus');
	});
})(jQuery);