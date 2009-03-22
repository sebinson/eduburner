window.PageMapping = $H();

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
        var _pageCache = $H();
        var _currentPageId = null;
		
		var adjustSize = function(){
			if(!_currentPageId){
				
			}
		};
		
		var getPageById = function(pageId){
			var page = _pageCache[pageId];
			if(!page){
				page = new PageMapping[pageId](pageId);
				_pageCache[pageId] = page;
			}
			return page;
		};
		
		var onStateChange = function(type, args){
			var token = args[0];
			//do some clear work for previous page
			if(_currentPageId){
				getPageById(_currentPageId).clear();
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
				getPageById(pageId).updateUi();
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
