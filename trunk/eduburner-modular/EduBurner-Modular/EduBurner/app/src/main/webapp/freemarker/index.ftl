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
	<script type="text/javascript" src="/static/scripts/home.js"></script>
	<script type="text/javascript">
		
	</script>
</@layout.masterPage>