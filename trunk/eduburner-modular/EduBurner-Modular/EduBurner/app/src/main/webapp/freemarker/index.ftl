<@layout.masterPage title="首页" pageType="userPage">
	<div id="main-content">
		<div style="margin:10px 0">
			<h2>首页</h2>
		</div>
		<@ui.postMessageBox/>
		<div class="entries">
			<#list page.items as e>
				<@ui.entryBox entry=e />
			</#list>
		</div>
	</div>
	<script type="text/javascript">
		function onButtonReady() { 
	        var submitMsgBtn = new YAHOO.widget.Button("submit-msg-button"); 
	    } 
	    YAHOO.util.Event.onContentReady("submit-msg-button", onButtonReady);
	    
	    YAHOO.util.Event.onDOMReady(function(){
		     $('#home').addClass('selected');
		     $('#post-msg-area').trigger('focus');
		});
	</script>
</@layout.masterPage>