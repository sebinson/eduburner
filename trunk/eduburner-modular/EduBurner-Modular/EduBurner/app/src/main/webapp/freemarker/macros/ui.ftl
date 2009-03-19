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
	    <h4 class="sidebar-block-header">课程管理</h4>
	    <div class="sidebar-block-content">
	      <ul class="sidebar-block-list">
		    <li><a href="/courses/new">创建课程</a></li>
	      </ul>
	    </div>
	  </div>
	</div>
</#macro>

<#macro formField label="" name="" desc="" attrs="" tag="input" >
	<div class="form-field">
		<label class="field-label">${label}：</label>
		<div class="field-value">
		    <#if tag == "input">
		    	<input class="text" name="${name}" ${attrs}/>
		    <#elseif tag == "textarea">
		    	<textarea name="${name}" ${attrs}></textarea>
		    <#elseif tag == "spring-input">
		    	<@spring.formInput "${name}" "${attrs}" />
		    <#elseif tag == "spring-textarea">
		    	<@spring.formTextarea  "${name}" "${attrs}" />
		    </#if>
		</div>
		<div class="field-desc">
			${desc}
		</div>
		<div class="clearer"></div>
	</div>
</#macro>