window.EventNames = {
	HISTORY_LOADED: 'history-loaded',
	HISTORY_STATE_CHANGED: 'history-changed'
};

(function($){

    $.utils = {
    
        setBodyClass: function(){
            var $bd = $('body');
            var cls = $.browser.msie ? "ie " + ($.browser.version == '6.0' ? 'ie6' : 'ie7') : $.browser.mozilla ? "mozilla" : $.browser.opera ? "opera" : $.browser.safari ? "safari" : "";
            $bd.addClass(cls);
        },
        
        findParent: function(el, selector, maxDepth){
        
            var b = document.body, depth = 0;
            maxDepth = maxDepth || 50;
            var p = el;
            
            while (p && p.nodeType == 1 && depth < maxDepth && p != b) {
                if ($(p).is(selector)) {
                    return p;
                }
                depth++;
                p = p.parentNode;
            }
            
            return null;
        }
    };
	
	$.formUtils = {
		/**
		 * taken from jquery form plugin
		 * 
		 * formToArray() gathers form element data into an array of objects that can
		 * be passed to any of the following ajax functions: $.get, $.post, or load.
		 * Each object in the array has both a 'name' and 'value' property.  An example of
		 * an array for a simple login form might be:
		 *
		 * [ { name: 'username', value: 'jresig' }, { name: 'password', value: 'secret' } ]
		 *
		 */
		formToArray: function($form, semantic) {
		    var a = [];
		    if (!$form || $form.length == 0) return a;
		
		    var form = $form[0];
		    var els = semantic ? form.getElementsByTagName('*') : form.elements;
		    if (!els) return a;
		    for(var i=0, max=els.length; i < max; i++) {
		        var el = els[i];
		        var n = el.name;
		        if (!n) continue;
		
		        if (semantic && form.clk && el.type == "image") {
		            // handle image inputs on the fly when semantic == true
		            if(!el.disabled && form.clk == el)
		                a.push({name: n+'.x', value: form.clk_x}, {name: n+'.y', value: form.clk_y});
		            continue;
		        }
		
		        var v = this.fieldValue(el, true);
		        if (v && v.constructor == Array) {
		            for(var j=0, jmax=v.length; j < jmax; j++)
		                a.push({name: n, value: v[j]});
		        }
		        else if (v !== null && typeof v != 'undefined')
		            a.push({name: n, value: v});
		    }
		
		    if (!semantic && form.clk) {
		        // input type=='image' are not found in elements array! handle them here
		        var inputs = form.getElementsByTagName("input");
		        for(var i=0, max=inputs.length; i < max; i++) {
		            var input = inputs[i];
		            var n = input.name;
		            if(n && !input.disabled && input.type == "image" && form.clk == input)
		                a.push({name: n+'.x', value: form.clk_x}, {name: n+'.y', value: form.clk_y});
		        }
		    }
		    return a;
		},
		
		/**
		 * The successful argument controls whether or not the field element must be 'successful'
 		 * (per http://www.w3.org/TR/html4/interact/forms.html#successful-controls).
		 */
		fieldValue: function(el, successful) {
		    var n = el.name, t = el.type, tag = el.tagName.toLowerCase();
		    if (typeof successful == 'undefined') successful = true;
		
		    if (successful && (!n || el.disabled || t == 'reset' || t == 'button' ||
		        (t == 'checkbox' || t == 'radio') && !el.checked ||
		        (t == 'submit' || t == 'image') && el.form && el.form.clk != el ||
		        tag == 'select' && el.selectedIndex == -1))
		            return null;
		
		    if (tag == 'select') {
		        var index = el.selectedIndex;
		        if (index < 0) return null;
		        var a = [], ops = el.options;
		        var one = (t == 'select-one');
		        var max = (one ? index+1 : ops.length);
		        for(var i=(one ? index : 0); i < max; i++) {
		            var op = ops[i];
		            if (op.selected) {
		                // extra pain for IE...
		                var v = $.browser.msie && !(op.attributes['value'].specified) ? op.text : op.value;
		                if (one) return v;
		                a.push(v);
		            }
		        }
		        return a;
		    }
		    return el.value;
		},
		
		validate: function(ele, rules){
			var name = ele.name,
				value = ele.value,
			    vInfos = rules[name];
			var vResult = {};
			if(!vInfos){
				vResult.result = 'OK';
				return vResult;
			}
			$.each(vInfos, function(i, vInfo){
				switch(vInfo.type){
					case "requiredstring":
						if(!value || value == ''){
							vResult.msg = 'FAIL';
							vResult.errorMsg = vInfo.errorMsg;
						}
						break;
					default:
						break;
				}
				if(vResult.result == 'FAIL'){
					return vResult;
				}
			});
			return vResult;		
		}
	}
})(jQuery)

