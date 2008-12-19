<#macro header>
    <div id="logo">
    	<a href="/"><i>EduTech Spur</i></a>
    </div>
    <div class="linkbar">
        <#if securityHelper.principal?has_content>
             <a href="${base}/account/profile">${securityHelper.principal?default("")} </a>
            |<a id="account-link" href="#">设置</a>
            |<a id="signout-link" href="${base}/logout">退出</a>
            |<a id="help-link" href="#">帮助</a>
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
	
</#macro>