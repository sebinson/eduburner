<@layout.masterPage title="首页" pageType="userPage">
	<div id="main-content">
		<@ui.postMessageBox/>
		<div class="entries">
			<#list page.items as e>
				<@ui.entryBox entry=e />
			</#list>
		</div>
	</div>
	<script type="text/javascript"> 
	    YAHOO.util.Event.onDOMReady(function(){
		     $('#home').addClass('selected');
		});
	</script>
</@layout.masterPage>