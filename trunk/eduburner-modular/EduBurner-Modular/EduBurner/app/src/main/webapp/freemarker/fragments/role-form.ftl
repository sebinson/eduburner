<form id="course-form" method="POST" action="<@spring.url '/users'/>">
	<@spring.bind "role.*" />
	<div class="eb-form-container">
	  <div class="eb-form">
	  	<div class="form-header">
			<h2>创建角色</h2>
		</div>
		<@ui.springFormInput label="用户名" name="role.name" required=true/>
		<@ui.springFormInput label="添加用户" name="role.users" required=true />
		<div class="seperator"></div>
		<div class="form-item">
			<div class="form-element">
    			<div class="buttons"><input id="submit-button" type="submit" value="创  建" /></div>
			</div>
		</div>
	  </div>
	</div>
</form>