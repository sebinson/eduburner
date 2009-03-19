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
            <a id="signup-link" href="/account/signup">注册</a>
        </#if>
    </div>
    <div class="clear"></div>
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
		    <li><a href="/courses/new">历史</a></li>
	      </ul>
	    </div>
	  </div>
	</div>
	</#if>
</#macro>

<#macro adminMenu>
	<div class="sidebar-block spacing">
	  <div class="sidebar-block-inner">
	    <h4 class="sidebar-block-header">管理</h4>
	    <div class="sidebar-block-content">
	      <ul class="sidebar-block-list">
		    <li><a href="/users/">用户管理</a></li>
		    <li><a href="/courses/">课程管理</a></li>
	      </ul>
	    </div>
	  </div>
	</div>
</#macro>

<#macro formTextField label="" name="" value="" type="text" desc="">
	<div class="form-field">
		<label class="field-label">${label}：</label>
		<div class="field-value">
			<input class="text" name="${name}" type="${type}" value="${value}"/>
		</div>
		<div class="field-desc">
			${desc}
		</div>
		<div class="clearer"></div>
	</div>
</#macro>

<#macro formTextAreaField label="" name="" value="" type="text" desc="">
	<div class="form-field">
		<label class="field-label" for="${name}">${label}：</label>
		<div class="field-value">
			<textarea class="text" name="${name}" type="${type}" value="${value}"></textarea>
		</div>
		<div class="field-desc">
			${desc}
		</div>
		<div class="clearer"></div>
	</div>
</#macro>