(function($){
	
	var Env = {
		_prefix : 'id',
		_uidx : 0
	};
	
	var Utility = new function(){
		
		this.guid = function(prefix){
			var p = prefix || Env._prefix;
			return p + '-' + Env._uidx++;	
		};
		
	};

    var PageMgr = new function(){
		
		var setBodyClass = function(){
			var $bd = $('body');
			var cls = $.browser.msie ? "ie " + ($.browser.version == '6.0' ? 'ie6' : 'ie7')
			            : $.browser.mozilla ? "mozilla"
			            : $.browser.opera ? "opera"
			            : $.browser.safari ? "safari" : "";            
            $bd.addClass(cls);
		};
    
        this.onDomReady = function(){
            setBodyClass();
        };
    };
    
    window.PageMgr = PageMgr;
	window.Utility = Utility;
})(jQuery);
