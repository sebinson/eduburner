<#macro header>
    <div id="logo">
    	<a href="/">EduBurner</a>
    </div>
    <div id="linkbar">
        <#if principal?has_content>
             ${principal.username?default("")}
             &nbsp;|&nbsp; <a href="/account/settings">设置</a>
             &nbsp;|&nbsp; <a href="/account/logout">退出</a>
        <#else>
        	<a id="signup-link" href="/account/login">登录</a>
            &nbsp;|&nbsp; <a id="signup-link" href="/account/signup">注册</a>
        </#if>
    </div>
    <div class="clear"></div>
    <div class="bottom"><span class="corner-bottom"><span class="corner-left"></span></span></div>
</#macro>

<#macro footer>
    &copy; EduBurner
</#macro>


<#macro navbar>
  <#if principal?has_content>
	<@ui.courseList />
	<#if principal.administrator>
	<@ui.adminMenu />
	</#if>
  </#if>
</#macro>

<#macro courseList>
	<#if user?has_content>
	<div class="sidebar-block spacing">
	  <div class="sidebar-block-inner">
	    <h4 class="sidebar-block-header">我的课程</h4>
	    <div class="sidebar-block-content">
	      <ul class="sidebar-block-list">
		    <li><a id="course-link-1" href="/courses/1" i="1"><img src="/static/images/silk/book_open.png"/><span>历史</span></a></li>
		    <li><a id="course-link-2" href="/courses/2" i="2"><img src="/static/images/silk/book_open.png"/><span>语文</span></a></li>
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
		    <li><a id="create-course-link" href="/courses/new"><img src="/static/images/silk/user_add.png"/><span>添加用户</span></a></li>
		    <li><a id="course-list-link" href="/courses/"><img src="/static/images/silk/user_edit.png"/><span>编缉用户</span></a></li>
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