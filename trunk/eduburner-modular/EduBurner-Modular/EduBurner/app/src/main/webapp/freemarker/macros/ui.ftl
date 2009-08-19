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
			    <!--
			    <li class="menu">
			    	<div id="top-search">
			    		
			    	</div>
			    </li>
			    -->
				<li class="menu">
					<div class="menu-title last">
						<a href="/account/logout">退出</a>
					</div>
				</li>
				<#if principal.administrator>
				<li class="menu">
					<div class="menu-title">
						<a href="/account/setting">设置</a>
					</div>
				</li>
				</#if>
				<li class="menu">
					<div class="menu-title">
						<a href="#">${principal.username?default("")}</a>
					</div>
				</li>
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

<#macro leftNav>
	<div class="sidebar-block spacing">
	  <div class="sidebar-block-inner">
	    <h4 class="sidebar-block-header">导航</h4>
	    <div class="sidebar-block-content">
	      <ul class="sidebar-block-list">
	      	 <li><a href="/"><img src="/static/images/silk/house.png"/><span>首页</span></a></li>
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
	      		<li><a id="course-link-${c.id}" href="/courses/${c.id}" i="${c.id}"><img src="/static/images/silk/book_open.png"/><span>${c.title}</span></a></li>
	      	</#list>
	      </ul>
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