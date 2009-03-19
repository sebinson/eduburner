/**
 * history framework, ported from yui history
 */
window.History = (function ($) {
    var iframe, hiddenField;
    var ready = false;
    var currentToken;

    function getHash() {
        var href = top.location.href, i = href.indexOf("#");
        return i >= 0 ? href.substr(i + 1) : null;
    }

    function doSave() {
        hiddenField.value = currentToken;
    }

    function handleStateChange(token) {
        currentToken = token;
        $().trigger(EventNames.HISTORY_STATE_CHANGED, [token]);
    }

    function updateIFrame (token) {
        var html = ['<html><body><div id="state">',token,'</div></body></html>'].join('');
        try {
            var doc = iframe.contentWindow.document;
            doc.open();
            doc.write(html);
            doc.close();
            return true;
        } catch (e) {
            return false;
        }
    }

    function checkIFrame() {
        if (!iframe.contentWindow || !iframe.contentWindow.document) {
            setTimeout(checkIFrame, 10);
            return;
        }

        var doc = iframe.contentWindow.document;
        var elem = doc.getElementById("state");
        var token = elem ? elem.innerText : null;

        var hash = getHash();

        setInterval(function () {

            doc = iframe.contentWindow.document;
            elem = doc.getElementById("state");

            var newtoken = elem ? elem.innerText : null;

            var newHash = getHash();

            if (newtoken !== token) {
                token = newtoken;
                handleStateChange(token);
                top.location.hash = token;
                hash = token;
                doSave();
            } else if (newHash !== hash) {
                hash = newHash;
                updateIFrame(newHash);
            }

        }, 50);

        ready = true;

        History.HistoryLoadEvent.fire();
    }

    function startUp() {
        currentToken = hiddenField.value;

        if ($.browser.msie) {
            checkIFrame();
        } else {
            var hash = getHash();
            setInterval(function () {
                var newHash = getHash();
                if (newHash !== hash) {
                    hash = newHash;
                    handleStateChange(hash);
                    doSave();
                }
            }, 50);
            ready = true;
            $().trigger(EventNames.HISTORY_LOADED, [hash]);
        }
    }

    return {
        fieldId: 'hist-field',
        iframeId: 'hist-iframe',
        
        init: function () {
			
			hiddenField = $('#' + History.fieldId)[0];
			if($.browser.msie){
				iframe = $('#' + History.iframeId)[0];
			}
			
			startUp();
        },

        navigate: function (token, preventDup) {
            if(preventDup !== false){
                if(this.getToken() == token){
                    return true;
                }
            }
            if ($.browser.msie) {
                return updateIFrame(token);
            } else {
                top.location.hash = token;
                return true;
            }
        },

        back: function(){
            history.go(-1);
        },

        forward: function(){
            history.go(1);
        },

        getToken: function() {
            return ready ? currentToken : getHash();
        },
		
		getHash: function(){
			return getHash();
		}
    };
	
})(jQuery);