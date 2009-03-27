<div style="margin:10px 0">
	<h2>课程：${course.title}</h2>
</div>
<div id="single-course" class="yui-navset">
	<ul class="yui-nav">
        <li class="selected"><a href="#course-message"><em>消息</em></a></li>
        <li><a href="#course-member"><em>成员</em></a></li>
        <li><a href="#course-resource"><em>资源</em></a></li>
        <li style="margin-right:15px"><a href="#course-manage"><em>管理</em></a></li>
    </ul>            
    <div class="yui-content">
        <div id="course-message">
        	<div>发新消息</div>
        </div>
        <div id="course-member">
        	<div>添加成员</div>
        	<div id="add-members-section" style="display: none;">
	            <form class="simple-form" action="" method="post" name="addMembersForm">
	                <fieldset>
	                    <label for="usersToAdd">
						    <span class="legend">添加成员</span>
							<input type="text" tabindex="2" size="60" id="users-to-add" name="usersToAdd"/>
						</label>                    
						<a id="user-picker" class="icon search-icon" href="#">User Picker</a>
	                    <fieldset class="submit">
	                        <input type="submit" value="添 加"/>                        
	                        <input type="submit" value="取 消" id="cancel-button"/>                    
	                    </fieldset>
	                    <p class="desc">输入用户名，以豆号分隔</p>
	                </fieldset>
	            </form>
        	</div>
        </div>
        <div id="course-resource">
        	<div>上传资源</div>
        </div>
        <div id="course-manage">
        </div>
    </div>
</div>

<div id="sel-user-dlg" style="display:none">
    <div class="hd">请选择一个用户</div>
    <div class="bd">
        
    </div>
</div>
