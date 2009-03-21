(function($){
	
	var HomePage = new Class({
		Extends: Page
		
		
	});
	
	var EditCoursePage = new Class({
		Extends: Page,
		
		buildState: function(){
			return this.id;
		},
		
		updateUi: function(state){
			this.loadPage();
		},
		
		loadPage: function(){
			$.waiting.start();
			$.ajax({
				url:'/courses/new',
				type: 'GET',
				success: function(data){
					$.waiting.stop();
					$('#main-content').html(data);
					var submitBtn = new YAHOO.widget.Button('submit-button');
					$('ul.sidebar-block-list>li>a').removeClass('selected');
					$('#create-course-link').addClass('selected');
				}
			});
		}
	});
	
	window.HomePage = HomePage;
	window.EditCoursePage = EditCoursePage;
	
	PageMapping.extend({
		'main': HomePage,
		'editcourse': EditCoursePage
	});
})(jQuery);
