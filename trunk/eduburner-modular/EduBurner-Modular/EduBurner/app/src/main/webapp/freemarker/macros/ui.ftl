<#macro header>
    <div id="logo">
    	<a href="/">EduBurner</a>
    </div>
    <div id="linkbar">
        <#if securityHelper.principal?has_content>
             ${securityHelper.principal?default("")}
             | <a href="/account/settings">设置</a>
             | <a href="/account/logout">退出</a>
        <#else>
            <a id="signup-link" href="#">注册</a>
        </#if>
    </div>
    <div class="clear"></div>
</#macro>

<#macro footer>
    &copy; EduBurner
</#macro>

<#-- 导航菜单 -->
<#macro navbar>
  <#if securityHelper.principal?has_content>
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
	<div class="sidebar-block spacing">
	  <div class="sidebar-block-inner">
	    <h4 class="sidebar-block-header">管理</h4>
	    <div class="sidebar-block-content">
	      <ul class="sidebar-block-list">
		    <li><a href="/courses/new">创建课程</a></li>
		    <li><a href="/courses/">课程列表</a></li>
	      </ul>
	    </div>
	  </div>
	</div>
  </#if>
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