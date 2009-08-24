<@layout.masterPage title="课程" pageType="userPage">
	<div id="main-content">
	    <!-- 弹出创建课程的对话框 -->
	    <div id="create-course-link"><a href="#">创建课程</a></div>
	    
		<h2 style="margin-bottom:5px">查找课程</h2>
		<div id="search-course">
			<form id="search-course-form">
				<input id="searchCourseInput" type="text" class="form-text" style="width:300px;float:left;margin-top:3px;margin-right:5px;"/>
				<input id="search-course-button" type="submit" value="搜 索" />
			</form>
		</div>
		
		<h2 style="margin-top:20px;margin-bottom:5px;">受欢迎的课程</h2>
		<@ui.courseListView courses/>
	</div>
	
	<script type="text/javascript" src="/static/scripts/courselist.js"></script>
</@layout.masterPage>