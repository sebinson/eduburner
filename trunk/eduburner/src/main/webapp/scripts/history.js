/**
 * @(#)history.js, Feb 5, 2009. 
 * 
 * Copyright 2009 Yodao, Inc. All rights reserved.
 * YODAO PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

/**
 * @author zhangyf
 */

var History = (function ($) {
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
        Youdao.History.StateChangeEvent.fire();
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

        Youdao.History.HistoryLoadEvent.fire();
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
            Youdao.History.HistoryLoadEvent.fire();
        }
    }

    return {
		HistoryLoadEvent: new Reader.CustomEvent("onLoad"),
		StateChangeEvent: new Reader.CustomEvent("histStateChange"),
        
        fieldId: 'hist-field',
        iframeId: 'hist-iframe',
        
        init: function () {
			
			hiddenField = document.getElementById(Youdao.History.fieldId);
			if($.browser.msie){
				iframe = $("#" + Youdao.History.iframeId)[0];
			}
			
			startUp();
        },

        add: function (token, preventDup) {
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
        }
    };
})(jQuery);