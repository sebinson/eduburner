(function($){

    var Page = new function(){
		
		var $mainContent = null;
		
		var viewEntriesForTag = function(tagKeyName){
			$.waiting.start();
			var url = '/tags/' + tagKeyName + '/entries';
			$.ajax({
				'url' : url,
				'success' : function(data){
					$.waiting.stop();
					$mainContent.html(data);
				}
			});
		};
		
		var viewAllTags = function(){
			$.waiting.start();
			$.ajax({
				type: "GET",
				url : '/entrytags/',
				success : function(data){
					$.waiting.stop();
					$mainContent.html(data);
					$('#all-tags-list>li>a').bind('click', function(e){
						e.preventDefault();
						var tagKeyName = $(this).attr("kn");
    					viewEntriesForTag(tagKeyName);
    					return false;
					})
				}				
			});
		};
    
        var bindEvents = function(){
			
			$mainContent = $('#main-content');
			
			$('#all-tags-link').bind('click', function(e){
				e.preventDefault();
    			viewAllTags()
    			return false;
    		});
			
        	$('#tag-cloud>li>a').bind('click', function(e){
				e.preventDefault();
    			var tagKeyName = $(this).attr("kn");
    			viewEntriesForTag(tagKeyName);
    			return false;
    		});
        }
        
        this.init = function(){
            $.utils.setBodyClass();
            bindEvents();
        }
    };
    
    $(document).ready(function(){
        Page.init();
    });
})(jQuery)
