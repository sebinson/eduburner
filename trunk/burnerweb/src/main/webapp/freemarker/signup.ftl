<@layout.masterPage title="注册" pageType="anonyPage">
    <form id="signup-form" method="POST" action="<@spring.url '/account/signup' />">
    	<@spring.bind "user.*"/>
        <div class="eb-form-container" style="margin-top: 20px; margin-left: 60px; margin-bottom: 30px;">
    	    <div class="eb-form">
    	    	<div class="form-header">
    				<h2>注册</h2>
    			</div>
	    		<@ui.springFormInput label="用户名" name="user.username" required=true/>
	    		<@ui.springFormInput label="密码"   name="user.password" attrs='type="password"' required=true/>
	    		<@ui.springFormInput label="确认密码" name="user.confirmPassword" attrs='type="password"' required=true/>
	    		<div class="seperator"></div>
	    		<@ui.springFormInput label="邮箱" name="user.email" required=true/>
	    		<@ui.springFormInput label="真实姓名" name="user.fullname"/>
	    		<div class="seperator"></div>
	    		<div class="form-item">
	    			<div class="form-element">
		    			<div class="buttons"><input id="submit-button" type="submit" value="注  册" /></div>
	    			</div>
	    		</div>
    	    </div>
        </div>
    </form>
    <script type="text/javascript"> 
	    function onButtonReady() { 
	        var submitButton = new YAHOO.widget.Button("submit-button"); 
	    } 
	    YAHOO.util.Event.onContentReady("submit-button", onButtonReady);
	    
	    YAHOO.util.Event.onDOMReady(function(){
	          $('#username').trigger('focus');
	    }); 
	</script> 
</@layout.masterPage>