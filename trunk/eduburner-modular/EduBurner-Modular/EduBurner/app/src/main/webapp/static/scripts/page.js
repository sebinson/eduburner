window.PageMapping = new Hash();

(function($){
    var Page = new Class({
        id: null,
        initialize: function(pageId){
            this.id = pageId;
        },
        buildState: function(){},
        updateUi: function(histState){},
		adjustSize: function(){},
		/**
		 * @abstract
		 * update page's init data
		 */
		updateInitData: function(data){
			$.extend(this, data);
		},
		/**
		 * @abstract
		 * do some clear work when page change
		 */
		clear: function(){
		}
    });
	Page.getIdByToken = function(token){
		return token.split("/")[0];
	};
    
    var PageMgr = new function(){
        var pageCache = $H();
        var currentPageId = null;
		
		var adjustSize = function(){
			if(!currentPageId){
				
			}
		};
		
		var getPageById = function(pageId){
			var page = pageCache[pageId];
			if(!page){
				page = new PageMapping[pageId](pageId);
				pageCache[pageId] = page;
			}
			return page;
		};
		
		var onStateChange = function(type, args){
			var token = args[0];
			//do some clear work for previous page
			if(currentPageId){
				getPageById(currentPageId).clear();
			}
			var page = getPageById(Page.getIdByToken(token));
			page.updateUi(token);
		};
		
		//handle page initialization here
		var onHistoryLoad = function(){
			var hash = History.getHash();
			if(hash == null){
				PageMgr.goToPage(PageMapping.keyOf(HomePage));
			}else{
				var pageId = Page.getIdByToken(hash);
				getPageById(pageId).updateUi(hash);
				currentPageId = pageId;
			}
		};
		
		var initEvents = function(){
			EventList.HISTORY_LOADED.subscribe(onHistoryLoad);
			EventList.HISTORY_STATE_CHANGED.subscribe(onStateChange);
			
			$('#create-course-link').bind('click', function(e){
				e.preventDefault();
				PageMgr.goToPage(PageMapping.keyOf(EditCoursePage));
			});
			
			$('#course-list-link').bind('click', function(e){
				e.preventDefault();
				PageMgr.goToPage(PageMapping.keyOf(CourseListPage));
			});
			
			$('#course-link-1').bind('click', function(e){
				e.preventDefault();
				PageMgr.goToPage(PageMapping.keyOf(CoursePage), {
					courseId: $(this).attr('i')
				});
			});
			
			$('#user-create-link').bind('click', function(e){
				e.preventDefault();
				PageMgr.goToPage(PageMapping.keyOf(EditUserPage));
			});
		};
		
		this.onDomReady = function(){
			$.utils.setBodyClass();
			initEvents();
			History.init();
		};
        
        this.goToPage = function(id, data){
			var page = getPageById(id);
			if(data){
				page.updateInitData(data);
			}
			var newstate = page.buildState();
			History.navigate(newstate);
        };
    };
	window.PageMgr = PageMgr;
	window.Page = Page;
})(jQuery);
