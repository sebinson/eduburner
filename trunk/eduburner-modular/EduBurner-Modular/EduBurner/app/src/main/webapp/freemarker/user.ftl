<@layout.masterPage title="课程" pageType="userPage">
	<div id="main-content">
		<div id="msg-box" class="hidden"><#-- 消息区   --></div>
		<div style="margin:10px 0">
			<h2>${userToShow.username}</h2>
		</div>
		<div><a href="#">加为好友</a></div>
		<div class="entries">
			<#list userToShow.entries as e>
				<@ui.entryBox entry=e />
			</#list>
		</div>		
	</div>
	<script type="text/javascript" src="/static/scripts/course.js"></script>
	<script type="text/javascript">
		window._EB_USERVIEW_DATA = {
			'username' : '${user.username}',
			'targetUsername' : '${userToShow.username}'
		}
	</script>
</@layout.masterPage>