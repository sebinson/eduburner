<@layout.masterPage title="课程" pageType="userPage">
	<div id="main-content">
		<div style="margin:10px 0">
			<h2>课程：${course.title}</h2>
		</div>
		<div id="single-course" class="yui-navset">
			<ul class="yui-nav">
		        <li class="selected"><a href="#course-message"><em>首页</em></a></li>
		        <li><a href="#course-member"><em>成员</em></a></li>
		        <li><a href="#course-resource"><em>资源</em></a></li>
		        <li style="margin-right:15px"><a href="#course-settings"><em>设置</em></a></li>
		    </ul>            
		    <div class="yui-content">
		        <div id="course-message" style="margin-top:10px;">
		        	<@ui.postMessageBox />
					<div class="entries">
						<#list course.entries as e>
							<@ui.entryBox entry=e />
						</#list>
					</div>
		        </div>
		        
		        <div id="course-member" style="margin-top:10px;">
		        	<@ui.userListView course.members/>
		        </div>
		        
		        <div id="course-resource" style="margin-top:10px;" >
		        	<div>上传资源</div>
		        </div>
		        <div id="course-settings" style="margin-top:10px;">
		        </div>
		    </div>
		</div>
		
		<!-- 对话框  -->
		<div id="sel-user-dlg" style="display:none">
		    <div class="hd">请选择一个用户</div>
		    <div class="bd">
		        
		    </div>
		</div>
		
	</div>
	<script type="text/javascript" src="/static/scripts/course.js"></script>
	<script type="text/javascript">
		window._EB_COURSEVIEW_DATA = {
			'courseId' : '${course.id}'
		}
	</script>
</@layout.masterPage>