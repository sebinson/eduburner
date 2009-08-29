<@layout.masterPage title="首页" pageType="userPage">
	<div id="main-content">
		<div style="margin:10px 0">
			<h2>首页</h2>
		</div>
	   <#if page.hasPrevious>
	   		<div class="pager head">
				<#if page.hasPrevious>
					<a href="/?p=${page.previousPage}">&laquo; 前一页</a>
				</#if>
				<#if page.hasNext>
					<a href="/?p=${page.nextPage}">后一页 &raquo;</a>
				</#if>
			</div>
	   <#else>
			<@ui.postMessageBox/>
	    </#if>
		<div id="entries" class="entries">
			<#list page.items as e>
				<@ui.entryBox entry=e />
			</#list>
		</div>
		<div class="pager bottom">
			<#if page.hasPrevious>
				<a href="/?p=${page.previousPage}">&laquo; 前一页</a>
			</#if>
			<#if page.hasNext>
				<a href="/?p=${page.nextPage}">后一页  &raquo;</a>
			</#if>
		</div>
	</div>
	<script type="text/javascript" src="/static/scripts/home.js"></script>
	<script type="text/javascript">
	    <#-- 
		window._EB_HOME_DATA = {
			'entries' : [<#list page.items as entry>{'id' : ${entry.id}}<#if page.items?last != entry>,</#if></#list>]
		}
		-->
	</script>
</@layout.masterPage>