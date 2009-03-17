<@layout.masterPage title="登录" >
    
    <form id="login-form" method="POST" action="/j_spring_security_check">
    	<div class="eb-form">
    		<h2>系统登录</h2>
    		<@ui.formField label="用户名" name="j_username"/>
    		<@ui.formField label="密码" type="password" name="j_password"/>
    		<div class="form-footer">
    			<div class="buttons">
    				<input type="submit" value="提交" />
    				<input type="button" value="取消" />
    			</div>
    		</div>
    	</div>
    </form>
    
</@layout.masterPage>