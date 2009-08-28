(function($){
	var D = YAHOO.util.Dom;
	var E = YAHOO.util.Event;
	
	function init(){
		$('#add-entry-form').bind('submit', function(e){
			e.preventDefault();
			$.ajax({
				
			});
		});
	}
	
	function onButtonReady() { 
        var submitMsgBtn = new YAHOO.widget.Button("submit-msg-button"); 
    } 
    E.onContentReady("submit-msg-button", onButtonReady);
    E.onDOMReady(function(){
	     $('#home').addClass('selected');
	     $('#post-msg-area').trigger('focus');
	});
})(jQuery);