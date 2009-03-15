<#macro header>
    <div id="logo">
    	<a href="/"><i>EduBurner</i></a>
    </div>
    <div id="linkbar">
        <#if securityHelper.principal?has_content>
             <a href="/account/profile">${securityHelper.principal?default("")}</a>
             | <a href="#">设置</a>
             | <a href="/logout">退出</a>
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
	<div class="sidebar-block spacing">
	  <div class="sidebar-block-inner">
	    <h4 class="sidebar-block-header">管理</h4>
	    <div class="sidebar-block-content">
	      <ul class="sidebar-block-list">
		    <li><a href="/courses/new">创建课程</a></li>
	      </ul>
	    </div>
	  </div>
	</div>
</#macro>