<#macro header>
    <div id="logo">
    	<a href="/">EduBurner</a>
    </div>
    <@ui.menubar />
    <div class="clear"></div>
    <div class="bottom"><span class="corner-bottom"><span class="corner-left"></span></span></div>
</#macro>

<!-- 目前用不上   -->
<#macro navMenuBar>
	<div class="top-nav-menu-bar">
		<ul class="menu-list">
			<#if principal?has_content>
				<li class="menu" style="margin-left:35px">
					<div class="menu-title">
						<a href="/">首页</a>
					</div>
				</li>
				<#if principal.administrator>
				<li class="menu last">
					<div class="menu-title">
						<a href="/admin">管理</a>
					</div>
				</li>
				</#if>
				<li class="menu">
					<div class="menu-title">
						<a href="#">消息</a>
					</div>
				</li>
			</#if>
		</ul>
	</div>
</#macro>

<#macro menubar>
	<div class="top-menu-bar">
		<ul class="menu-list">
			<#if principal?has_content>
			    <li class="menu">
			    	<div id="top-search">
			    		<form id="top-search-form" action="/search" method="GET">
			    			<div class="search-form-input">
			    				<input id="q" name="q" value="搜索" style="color:#666;" onfocus="this.style.color='#000';if(this.value=='搜索'){this.value='';}" onblur="if(this.value == ''){this.value='搜索'; this.style.color='#666';}"/>
			    			</div>
			    			<div class="search-form-submit">
			    			    <a id="submit-top-search-link" href="#" onclick="document.getElementById('top-search-form').submit();return false;"><span>&nbsp;</span></a>
			    			</div>
			    		</form>
			    	</div>
			    </li>
			    <#--
				<li class="menu">
					<div class="menu-title last">
						<a href="/account/logout">退出</a>
					</div>
				</li>
				<li class="menu">
					<div class="menu-title">
						<a href="/account/settings">设置</a>
					</div>
				</li>
				<li class="menu">
					<div class="menu-title">
						<a href="#">${principal.username?default("")}</a>
					</div>
				</li>
				-->
			<#else>
				<li class="menu last">
					<div class="menu-title">
						<a href="/account/signup">注册</a>
					</div>
				</li>
				<li class="menu">
					<div class="menu-title">
						<a href="/account/login">登录</a>
					</div>
				</li>
			</#if>
		</ul>
	</div>
</#macro>

<#macro footer>
    &copy;2009 EduBurner
</#macro>

<#macro userNavBar>
    <@ui.navProfile />
	<@ui.leftNav />
	<#if principal?has_content>
		<@ui.courseList />
	</#if>
</#macro>

<#macro adminNavBar>
	<@ui.leftNav />
	<#if principal?has_content && principal.administrator>
		<@ui.adminMenu />
	</#if>
</#macro>

<#macro navProfile>
	<div id="profile">
		<div class="profile-image">
			<a href="#"><img style="width: 50px; height: 50px;" src="${user.profilePicture}"/></a>
		</div>
		<div class="profile-body">
			<div class="name">
				<a href="/users/${principal.username}">${principal.username}</a>
			</div>
			<div class="actions">
				<a href="/account/settings">设置</a> - <a href="/account/logout">退出</a>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</#macro>

<#macro leftNav>
	<div class="sidebar-block spacing">
	  <div class="sidebar-block-inner">
	    <h4 class="sidebar-block-header">导航</h4>
	    <div class="sidebar-block-content">
	      <ul class="sidebar-block-list">
	      	 <li><a id="home" href="/"><img src="/static/images/silk/house.png"/><span>首页</span></a></li>
	      	 <#--
	      	 <li><a id="home" href="/messages"><img src="/static/images/silk/email_go.png"/><span>消息</span></a></li>
	      	 -->
	      	 <li><a id="home" href="/friends"><img src="/static/images/silk/group_go.png"/><span>好友</span></a></li>
	      </ul>
	    </div>
	  </div>
	</div>
</#macro>

<#macro courseList>
	<#if user?has_content>
	<div class="sidebar-block spacing">
	  <div class="sidebar-block-inner">
	    <h4 class="sidebar-block-header">课程</h4>
	    <div class="sidebar-block-content">
	      <ul class="sidebar-block-list">
	      	<#list user.courses as c>
	      		<li><a id="c-${c.id}" href="/courses/${c.id}" i="${c.id}"><img src="/static/images/silk/book_open.png"/><span>${c.title}</span></a></li>
	      	</#list>
	      </ul>
	    </div>
	    <div class="sidebar-block-footer">
	    	<a href="/courses/">浏览/编缉课程</a>
	    </div>
	  </div>
	</div>
	</#if>
</#macro>

<#macro adminMenu>
	<div class="sidebar-block spacing">
	  <div class="sidebar-block-inner">
	    <h4 class="sidebar-block-header">课程管理</h4>
	    <div class="sidebar-block-content">
	      <ul class="sidebar-block-list">
		    <li><a id="create-course-link" href="/courses/new"><img src="/static/images/silk/book_add.png"/><span>创建课程</span></a></li>
		    <li><a id="course-list-link" href="/courses/"><img src="/static/images/silk/table_multiple.png"/><span>课程列表</span></a></li>
	      </ul>
	    </div>
	  </div>
	</div>
	<div class="sidebar-block spacing">
	  <div class="sidebar-block-inner">
	    <h4 class="sidebar-block-header">用户管理</h4>
	    <div class="sidebar-block-content">
	      <ul class="sidebar-block-list">
	      	<li><a id="user-create-link" href="/users/new"><img src="/static/images/silk/user_add.png"/><span>添加用户</span></a></li>
		    <li><a id="user-list-link" href="/courses/new"><img src="/static/images/silk/user_edit.png"/><span>用户列表</span></a></li>
		    <li><a id="role-manage-link" href="/courses/"><img src="/static/images/silk/key.png"/><span>角色管理</span></a></li>
		    <li><a id="perm-manage-link" href="/courses/"><img src="/static/images/silk/shield.png"/><span>权限管理</span></a></li>
		    <!--
		    <li><a id="course-list-link" href="/courses/"><img src="/static/images/silk/page_key.png"/><span>安全设置</span></a></li>
		    -->
	      </ul>
	    </div>
	  </div>
	</div>
	<div class="sidebar-block spacing">
	  <div class="sidebar-block-inner">
	    <h4 class="sidebar-block-header">系统管理</h4>
	    <div class="sidebar-block-content">
	      <ul class="sidebar-block-list">
		    <li><a id="create-course-link" href="/courses/new"><img src="/static/images/silk/bullet_wrench.png"/><span>系统设置</span></a></li>
	      </ul>
	    </div>
	  </div>
	</div>
</#macro>

<#macro formInput label="" name="" desc="" attrs="" required=false>
  <div class="form-item">
    <label class="form-item-label" style="text-align:right;" for="${name}">
    	${label}：<#if required><span>*</span></#if>
    </label>
    <div class="form-element">
      	<input id="${name}" name="${name}" style="width:180px" class="form-text form-field" ${attrs}/>
      	<span class="form-invalid-msg hidden"></span>
      	<span class="form-field-desc <#if desc==''>hidden</#if>">${desc}</span>
    </div>
    <div class="form-clear-left"></div>
  </div>
</#macro>

<#macro springFormInput label="" name="" desc="" attrs="" required=false>
  <div class="form-item">
    <label class="form-item-label" style="text-align:right;" for="${name}">
    	${label}：<#if required><span>*</span></#if>
    </label>
    <div class="form-element">
      	<@spring.formInput "${name}" "class='form-text form-field' ${attrs}" />
      	<span class="form-invalid-msg hidden"></span>
      	<span class="form-field-desc <#if desc==''>hidden</#if>">${desc}</span>
    </div>
    <div class="form-clear-left"></div>
  </div>
</#macro>

<#macro formTextarea label="" name="" desc="" attrs="" required=false>
  <div class="form-item">
    <label class="form-item-label" style="text-align:right;" for="${name}">
    	${label}：<#if required><span>*</span></#if>
    </label>
    <div class="form-element">
      	<textarea id="${name}" name="${name}" class="form-textarea form-field" ${attrs} ></textarea>
    </div>
    <div class="form-clear-left"></div>
  </div>
</#macro>

<#macro springFormTextarea label="" name="" desc="" attrs="" required=false>
  <div class="form-item">
    <label class="form-item-label" style="text-align:right;" for="${name}">
    	${label}：<#if required><span>*</span></#if>
    </label>
    <div class="form-element">
      	<@spring.formTextarea  "${name}" "class='form-textarea form-field' ${attrs}" />
    </div>
    <div class="form-clear-left"></div>
  </div>
</#macro>

<#macro postMessageBox>
  <div class="post-message-box">
    <form id="add-entry-form">
	    <div class="message-form">
	    	<textarea id="post-msg-area" name="entry.title"></textarea>
	    </div>
	    <div class="message-actions">
	    	<div class="submit-button">
	        	<input id="submit-msg-button" type="submit" value="发 表"/>
	        </div>
	        <div class="clear"></div>
	    </div>
    </form>
  </div>
</#macro>

<#macro entryBox entry>
  <div class="entry" i=${entry.id}>
  	<div class="profile">
  	  <a href="/users/${entry.user.username}"><img src="${entry.user.profilePicture}" style="width:50px;height:50px;"/></a>
  	</div>
  	<div class="content">
  	  <div class="entry-info">
  	  	<div class="name">
  	  		<a href="${entry.user.profilePicture}">${entry.user.username}</a>
  	  	</div>
  	  	<div class="text">
  	  		${entry.title}
  	  	</div>
  	  </div>
  	  <div class="actions">
  	  	<span>${entry.published?datetime}</span>
  	  	<span class="add-comment link">评论</span>
  	  	<#if (entry.user.username!=principal.username)>
  	  	   <span> - </span><span class="like link">喜欢</span>
  	  	<#else>
  	  	   <span> - </span><span class="edit link">编缉</span>
  	  	   <span> - </span><span class="remove link">删除</span>
  	  	</#if>
  	  </div>
  	  <#if (entry.likes?size>0) >
  	  <div class="likes">
  	  	a, b, c喜欢此条目
  	  </div>
  	  </#if>
  	  <div class="comments">
  	  	<#list entry.comments as c>
	  	  	<div class="comment">
	  	  		<div class="comment-content">
	  	  			${c.body}
	  	  		</div>
	  	  	</div>
  	  	</#list>
  	  	<div class="comment-form" style="display:none">
  	  	  <form>
  	  	    <div class="text">
  	  	      <textarea name="body"></textarea>
  	  	    </div>
  	  	    <div class="buttons">
  	  	      <input type="submit" value="发布" />
  	  	      <span class="link _cancel_">取消</span>
  	  	    </div>
  	  	  </form>
  	  	</div>
  	  </div>
  	</div>
  	<div class="clear"></div>
  </div>
</#macro>

<#macro courseListView courses=[]>
  <div class="grid-view">
    <#list courses as c>
       <div class="single-item">
       		<div class="picture">
       			<a href="#"><img style="width:75px;height:75px;" src="${c.picture}" /></a>
       		</div>
       		<div class="name">
       		    <a href="#">${c.title}</a>
       		</div>
       </div>
    </#list>
  </div>
</#macro>

<#macro userListView users=[]>
  <div class="grid-view">
  	<#if (users?size > 0)>
	    <#list users as u>
	       <#if u.id != user.id>
	       <div class="single-item">
	       		<div class="picture">
	       			<a href="/users/${u.username}"><img style="width:75px;height:75px;" src="${u.profilePicture}" /></a>
	       		</div>
	       		<div class="name">
	       		    <a href="/users/${u.username}">${u.username}</a>
	       		</div>
	       </div>
	       </#if>
	    </#list>
	<#else>
		<div class="info" style="margin-top:30px;">当前没有好友</div>
	</#if>
  </div>
</#macro>

<#macro courseMemberListView users=[]>
	<div class="grid-view">
  	<#if (users?size > 0)>
	    <#list users as u>
	       <div class="single-item">
	       		<div class="picture">
	       			<a href="/users/${u.username}"><img style="width:75px;height:75px;" src="${u.profilePicture}" /></a>
	       		</div>
	       		<div class="name">
	       		    <a href="/users/${u.username}">${u.username}</a>
	       		</div>
	       </div>
	    </#list>
	<#else>
		<div class="info" style="margin-top:30px;">当前没有成员</div>
	</#if>
  </div>
</#macro>