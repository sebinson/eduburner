;(function($){
	/**
	 * type :  requiredstring, email, int
	 * {'type':'int', msg:'必需是数字', min:0, max:20}
	 */
	var Rules = {
		"url" : [{
			type: "requiredstring",
			errorMsg: "网址为必填项"
		}]
	};
	
	var Page = new function(){
		
		var SUCCESS_MSG = '<div id="message-area" class="message-success">感谢您的提交，为了确保资源内容和教育技术相关，资源内容会被审核，请等待批准</div>';
		
		var self = this;
		
		var validateForm = function(formArray){
			var errors = [];
			$.each(formArray, function(i, ele){
				//var $msgEl = $('#' + ele.name + '-msg');
				var vResult = $.utils.validate(ele, Rules);
				
				if(vResult.msg == 'FAIL'){
					errors.push(vResult);
					//$msgEl.html(vResult.errorMsg).removeClass("hidden");
				}else{
					//$msgEl.addClass("hidden");
				}
				if(ele.name == 'tags'){
					ele.value = $.trim(ele.value).replace(/ /g, "-").replace(/，/g, ",").replace(/,-/g, ",");
				}
			});
			return errors;
		};
		
		var handleErrors = function(errors){
			var $msgArea = $('#message-area').empty().removeClass('hidden');
			$.each(errors, function(i, error){
				$msgArea.append('<img src="/static/images/status-red.gif" align="absmiddle" />' + error.errorMsg + '<br/>');
			});
		};
		
		var addRes = function(data){
			if(data.msg == 'OK'){
				$('#main-content').html(SUCCESS_MSG);
			}else{
				handleErrors(data.errors);
			}
		};
		
		var initForm = function(){
			var $form = $('#add-res-form'),
			    targetUrl = $form.attr('action'),
				method = $form.attr('method');
			
			$form.bind("submit", function(e){
				e.preventDefault();
				var formArray = $.utils.formToArray($form);
				var errors = validateForm(formArray);
				if(errors && errors.length > 0){
					handleErrors(errors);
					return false;
				}
				$.waiting.start();
				$.ajax({
					url: targetUrl,
					type: method,
					data: $.param(formArray),
					dataType: "json",
					success: function(data){
						$.waiting.stop();
						addRes(data);
					},
					error: function(xreq, msg){}
				});
				return false;
			});
		};
		
		this.init = function(){
			$.utils.setBodyClass();
			initForm();
		};
	};
	
	$(document).ready(function(){
		Page.init();
	});
	
})(jQuery)