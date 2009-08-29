<@layout.masterPage title="好友" pageType="userPage">
	<div id="main-content">
		<div id="search-friend-link"><a href="/friends/search">查找好友</a></div>
	
		<h2 style="margin-bottom:5px">好友</h2>
		<@ui.userListView friends/>
	</div>
	<script type="text/javascript" src="/static/scripts/friendlist.js"></script>
</@layout.masterPage>