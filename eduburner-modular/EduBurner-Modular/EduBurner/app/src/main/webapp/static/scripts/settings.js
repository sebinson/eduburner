(function($){
	var D = YAHOO.util.Dom;  
	var E = YAHOO.util.Event; 
	
	YAHOO.widget.Uploader.SWFURL = "/static/yui/uploader/assets/uploader.swf";
	
	var tabView = null;
	
	var init = function(){
		tabView = new YAHOO.widget.TabView('user-settings');
		initUploader();
	}
	
	var initUploader = function(){
		var fileId;
		
		var uploader = new YAHOO.widget.Uploader("uploaderOverlay");
		
		var uiLayer = D.getRegion('selectLink');
		var overlay = D.get('uploaderOverlay');
		D.setStyle(overlay, 'width', uiLayer.right-uiLayer.left + "px");
		D.setStyle(overlay, 'height', uiLayer.bottom-uiLayer.top + "px");
		
		E.on('uploadLink', 'click', handleUpload);
		
		uploader.addListener('contentReady', handleContentReady);
		uploader.addListener('fileSelect', onFileSelect);
		uploader.addListener('uploadStart', onUploadStart);
		uploader.addListener('uploadProgress', onUploadProgress);
		uploader.addListener('uploadComplete', onUploadComplete);
		uploader.addListener('uploadError', onUploadError);
		
		function handleUpload(event){
			if (fileID != null) {
				uploader.upload(fileID, "/account/profilepicture",  "POST", {
					'userId': window._EB_SETTINGS_DATA.userId,
					'username': window._EB_SETTINGS_DATA.username
				});
			}	
		}

		// When contentReady event is fired, you can call methods on the uploader.
		function handleContentReady () {
		    // Allows the uploader to send log messages to trace, as well as to YAHOO.log
			uploader.setAllowLogging(false);
			
			// Disallows multiple file selection in "Browse" dialog.
			uploader.setAllowMultipleFiles(false);
			
			// New set of file filters.
			var ff = new Array({description:"Images", extensions:"*.jpg;*.png;*.gif"});
			
			// Apply new set of file filters to the uploader.
			uploader.setFileFilters(ff);
		}

		//这里使用var onFileSelect = function(){}则无法回调， WHY?
		function onFileSelect(event){
			for (var file in event.fileList) {
			    if(YAHOO.lang.hasOwnProperty(event.fileList, file)) {
					fileID = event.fileList[file].id;
				}
			}
			
			this.progressReport = document.getElementById("progressReport");
			this.progressReport.innerHTML = "选择文件 " + event.fileList[fileID].name;
		}
		
		function onUploadStart(event) {
			this.progressReport.innerHTML = "开始上传...";
		}
		
		// As upload progresses, we report back to the user via the
		// progress report textfield.
		function onUploadProgress(event) {
			prog = Math.round(100*(event["bytesLoaded"]/event["bytesTotal"]));
			this.progressReport.innerHTML = "已上传 " + prog + "% ...";
		}
		
		// Report back to the user that the upload has completed.
		function onUploadComplete(event) {
			this.progressReport.innerHTML = "上传完毕";
		}
		
		// Report back to the user if there has been an error.
		function onUploadError(event) {
			this.progressReport.innerHTML = "上传失败";
		}
	}
	
	
    YAHOO.util.Event.onContentReady("submit-button", function(){
    	var submitButton = new YAHOO.widget.Button("submit-button"); 
    });
	YAHOO.util.Event.onContentReady('submit-password-button', function(){
		var submitPasswordButton = new YAHOO.widget.Button("submit-password-button"); 
	});
	YAHOO.util.Event.onDOMReady(function(){
	     init();
	});
})(jQuery);