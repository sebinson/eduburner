<form id="course-form" method="POST" action="<@spring.url '/users'/>">
	<@spring.bind "user.*" />
	<div class="eb-form-container">
	  <div class="eb-form">
	  	<div class="form-header">
			<h2>创建用户</h2>
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
    			<div class="buttons"><input id="submit-button" type="submit" value="创  建" /></div>
			</div>
		</div>
	  </div>
	</div>
</form>