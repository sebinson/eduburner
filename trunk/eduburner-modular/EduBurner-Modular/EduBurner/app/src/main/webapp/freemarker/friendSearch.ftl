<@layout.masterPage title="查找好友" pageType="userPage">
	<div id="main-content">
	    
		<h2 style="margin-bottom:5px">查找好友</h2>
		<div id="search-friend">
			<form id="search-friend-form">
				<input id="searchFriendInput" type="text" class="form-text" style="width:300px;height:22px;float:left;margin-right:5px;font-size:1.2em;"/>
				<input id="search-friend-button" type="submit" value="搜 索" />
			</form>
		</div>
		
		<h2 style="margin-top:20px;margin-bottom:5px;">推荐的好友</h2>
		<@ui.userListView users/>
	</div>
	
	<script type="text/javascript" src="/static/scripts/friendlist.js"></script>
</@layout.masterPage>