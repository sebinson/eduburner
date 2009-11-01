(function($){
	var D = YAHOO.util.Dom;
	var E = YAHOO.util.Event;
	
	function onButtonReady() { 
        var submitMsgBtn = new YAHOO.widget.Button("submit-msg-button"); 
    };
    E.onContentReady("submit-msg-button", onButtonReady);
    E.onDOMReady(function(){
	     $('#home').addClass('selected');
	     $('#post-msg-area').trigger('focus');
	     init();
	});
	
	function init(){
		initAddEntryForm();
		initEntries();
	};
	
	function initAddEntryForm(){
		$('#add-entry-form').bind('submit', function(e){
			e.preventDefault();
			var msg = $.trim($('#post-msg-area').val());
			if(msg == '') return;
			$.waiting.start();
			$.ajax({
				url: '/entries/',
				type: 'POST',
				data: {'title' : msg},
				success: function(data){
					$.waiting.stop();
					$('#post-msg-area').val('').trigger('focus');
					$('#entries').html(data);
					initEntries();
				}
			});
		});
	};
	
	function initEntries(){
		function initComment(entryId, $entryEl){
			var $toggleCommentLink = $entryEl.find('.add-comment');
			var $comments = $entryEl.find('.comments'),
			    $commentForm = $entryEl.find('.comment-form');
			
			$toggleCommentLink.bind('click', function(e){
				$commentForm.toggle();
			});
			
			$commentForm.find('._cancel_').bind('click', function(e){
				$commentForm.hide();
			});
			
			$commentForm.bind('submit', function(e){
				e.preventDefault();
				$.waiting.start();
				$.ajax({
					success: function(data){
						$.waiting.stop();
					}
				});
			});
		};
		
		function initEdit(entryId, $entryEl){
			
		};
		
		function initRemove(entryId, $entryEl){
			$entryEl.find('.remove').bind('click', function(e){
				e.preventDefault();
				var c = confirm('确定要删除吗？');
				
			});
		};
		
		$('#entries>.entry').each(function(){
			var id = this.getAttribute('i');
			initComment(id, $(this));
			initEdit(id, $(this));
			initRemove(id, $(this));
		});
	};
	
})(jQuery);