<@layout.masterPage title="首页" pageType="userPage">
	<div id="main-content">
		<div style="margin:10px 0">
			<h2>首页</h2>
		</div>
		<@ui.postMessageBox/>
		<div id="entries" class="entries">
			<#list page.items as e>
				<@ui.entryBox entry=e />
			</#list>
		</div>
		<div class="pager bottom">
			<#if page.hasPrevious>
				<a href="/?page=${page.previousPage}">&laquo;前一页</a>
			</#if>
			<#if page.hasNext>
				<a href="/?page=${page.nextPage}">后一页 &raquo;</a>
			</#if>
		</div>
	</div>
	<script type="text/javascript" src="/static/scripts/home.js"></script>
	<script type="text/javascript">
		
	</script>
</@layout.masterPage>