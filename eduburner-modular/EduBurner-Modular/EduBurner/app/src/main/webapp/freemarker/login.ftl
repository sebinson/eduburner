<@layout.masterPage title="登录" >
    
    <form id="login-form" method="POST" action="/j_spring_security_check">
    	<div class="eb-form" style="margin-top: 20px; margin-left: 60px; margin-bottom: 30px;">
    	  <div class="form-inner">
    		<div class="title-header">
    			<h2>系统登录</h2>
    		</div>
    		<#if error?has_content>
    		<div class="error-box">
    			用户名或密码错误！
    		</div>
    		</#if>
    		<div class="form-body">
    		<@ui.formField label="用户名" name="j_username"/>
    		<@ui.formField label="密码"   name="j_password" attrs='type="password"'/>
    		<div class="form-field">
    			<span class="persistent">
    				<input type="checkbox" name="rememberMe"/>
    				在本机记住我
    			</span>
    		</div>
    		<div class="form-field">
    			<div class="buttons">
    				<input class="button" type="submit" value="提 交" />
    			</div>
    		</div>
    		</div>
    	  </div>
    	</div>
    </form>
    
</@layout.masterPage>