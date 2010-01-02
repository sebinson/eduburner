(function($){
	var D = YAHOO.util.Dom;
	var E = YAHOO.util.Event;
	
	function init(){
		
		$('#invitation-link').bind('click', function(e){
			e.preventDefault();
			$.waiting.start();
			$.ajax({
				url: '/friends/add.json',
				type: 'POST',
				dataType: 'json',
				data: {'requestor': _EB_USERVIEW_DATA.username, 'candidate':_EB_USERVIEW_DATA.targetUsername},
				success: function(data){
					$.waiting.stop();
					if(data.msg == 'OK'){
						$('#msg-box').css('visibility', '').html('已成功添加好友');
					}
				}
			});
		});
		
	}
	
    E.onDOMReady(function(){
	     init();
	});
})(jQuery);