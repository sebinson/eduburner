<@layout.masterPage title="登录" >
    <form id="login-form" method="POST" action="/j_spring_security_check">
        <div class="eb-form-container" style="margin-top: 20px; margin-left: 60px; margin-bottom: 30px; width:500px;">
    	    <div class="eb-form">
    	    	<div class="form-header">
    				<h2>系统登录</h2>
    			</div>
    			<#if error?has_content>
		    		<div class="error-box">
		    			用户名或密码错误！
		    		</div>
	    		</#if>
	    		<@ui.formInput label="用户名" name="j_username"/>
	    		<@ui.formInput label="密码"   name="j_password" attrs='type="password"'/>
	    		<div class="form-item">
	    			<div class="form-element">
		    			<span class="persistent">
		    				<input type="checkbox" name="rememberMe"/>
		    				在本机记住我
		    			</span>
	    			</div>
	    		</div>
	    		<div class="form-item">
	    			<div class="form-element">
		    			<div class="buttons"><input id="ok-button" type="submit" value="提 交" /></div>
	    			</div>
	    		</div>
    	    </div>
        </div>
    </form>
    <script type="text/javascript"> 
	    function onButtonReady() { 
	        var oKButton = new YAHOO.widget.Button("ok-button"); 
	    } 
	    YAHOO.util.Event.onContentReady("ok-button", onButtonReady);
	    
	    YAHOO.util.Event.onDOMReady(function(){
	          $('#j_username').trigger('focus');
	    });
	</script> 
</@layout.masterPage>