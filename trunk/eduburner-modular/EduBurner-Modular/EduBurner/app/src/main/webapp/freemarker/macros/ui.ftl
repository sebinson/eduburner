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

<#macro formInput label="" name="" desc="" attrs="" >
  <div class="form-item">
    <label class="form-item-label" style="text-align:right;" for="${name}">${label}：</label>
    <div class="form-element">
      	<input type="text" name="${name}" style="width:180px" class="form-text form-field" ${attrs}/>
      	<span class="form-invalid-msg hidden"></span>
    </div>
    <div class="form-clear-left"></div>
  </div>
</#macro>

<#macro springFormInput label="" name="" desc="" attrs="" >
  <div class="form-item">
    <label class="form-item-label" style="text-align:right;" for="${name}">${label}：</label>
    <div class="form-element">
      	<@spring.formInput "${name}" "${attrs}" />
    </div>
    <div class="form-clear-left"></div>
  </div>
</#macro>

<#macro formTextarea label="" name="" desc="" attrs="" >
  <div class="form-item">
    <label class="form-item-label" style="text-align:right;" for="${name}">${label}：</label>
    <div class="form-element">
      	<textarea name="${name}" style="width: 240px; height: 100px;" class="form-textarea form-field" ${attrs} ></textarea>
    </div>
    <div class="form-clear-left"></div>
  </div>
</#macro>

<#macro springFormTextarea label="" name="" desc="" attrs="" >
  <div class="form-item">
    <label class="form-item-label" style="text-align:right;" for="${name}">${label}：</label>
    <div class="form-element">
      	<@spring.formTextarea  "${name}" "${attrs}" />
    </div>
    <div class="form-clear-left"></div>
  </div>
</#macro>