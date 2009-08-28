(function($){
	var D = YAHOO.util.Dom;
	var E = YAHOO.util.Event;
	
	function init(){
		
	}
	
	var UserMgr = new function(){
		
		this.sendInvitation = function(){
			$.waiting.start();
			$.ajax({
				url: '/invite'
				type: 'POST',
				data: {}
				dataType: 'json',
				success: function(){
				}	
			});
		}
		
	}
	
    E.onDOMReady(function(){
	     init();
	});
})(jQuery);