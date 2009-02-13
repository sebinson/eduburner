<#macro header>
    <div id="logo">
    	<a href="/"><i>EduBurner</i></a>
    </div>
    <div id="linkbar">
        <#if securityHelper.principal?has_content>
             <a href="${base}/account/profile">${securityHelper.principal?default("")}</a>
             | <a href="#">设置</a>
             | <a href="${base}/logout">退出</a>
        <#else>
            <a id="signup-link" href="#">注册</a>
        </#if>
    </div>
    <div class="clear"></div>
</#macro>

<#macro footer>
    <p>&copy; EduBurner | <a href="#" target="_blank">Privacy</a></p>
</#macro>

<#-- 导航菜单 -->
<#macro navbar>
	<div class="ui-widget-content ui-corner-all sidebar-block spacing">
	  <div class="sidebar-block-inner">
	    <h4 class="sidebar-block-header">我的课程</h4>
	    <div class="sidebar-block-content">
	      <ul class="sidebar-block-list">
		    <li><a href="/courses/3">历史</a></li>
	      </ul>
	    </div>
	  </div>
	</div>
</#macro>

<#macro button>
	<span id="addRow" class="yui-button">
		<span class="first-child">
			<button type="button" tabindex="0">按钮</button>
		</span>
	</span>
</#macro>