<@layout.masterPage title="设置" pageType="userPage">
	<div id="main-content">
		<div style="margin:10px 0">
			<h2>我的设置</h2>
		</div>
		<div id="user-settings" class="yui-navset">
			<ul class="yui-nav">
		        <li class="selected"><a href="#account"><em>帐号</em></a></li>
		        <li><a href="#profile-piecure"><em>头像</em></a></li>
		        <li><a href="#password"><em>密码</em></a></li>
		        <li><a href="#services"><em>服务</em></a></li>
		    </ul>
		    <div class="yui-content">
		    	<div id="account">
			    	<form id="signup-form" method="POST" action="<@spring.url '/account/signup' />">
				    	<@spring.bind "user.*"/>
				    	<input type="hidden" name="_method" value="PUT" />
				        <div style="margin-top: 20px; margin-left: 60px; margin-bottom: 30px;">
				    	    <div class="eb-form">
					    		<@ui.springFormInput label="邮箱" name="user.email" required=true />
					    		<@ui.springFormInput label="真实姓名" name="user.fullname" />
					    		<@ui.springFormTextarea label="描述" name="user.description" />
					    		<div class="form-item">
					    			<div class="form-element">
						    			<div class="buttons"><input id="submit-button" type="submit" value="提 交" /></div>
					    			</div>
					    		</div>
				    	    </div>
				        </div>
				    </form>
		    	</div>
		    	<div id="profile-piecure">
		    		 <div id="uploaderContainer">
					 	<div id="uploaderOverlay" style="position:absolute; z-index:2"></div>
					 	<div id="selectFilesLink" style="z-index:1"><a id="selectLink" href="#">选择头像</a></div>
		  			 </div>
					 <div id="uploadFilesLink"><a id="uploadLink" href="#">上传文件</a></div><br/>
					 
					 <div id="selectedFileDisplay">
						<span id="progressReport" ></span>
					 </div>
		    	</div>
		    	<div id="password">
		    		<form id="alter-password-form" method="POST" action="<@spring.url '/account/alterpassword' />">
		    			 <div style="margin-top: 20px; margin-left: 60px; margin-bottom: 30px;">
		    			 	<@ui.formInput label="密码" name="origionalPassword" required=true attrs="type='password'"/>
		    			 	<@ui.formInput label="新密码" name="newPassword" required=true attrs="type='password'"/>
		    			 	<@ui.formInput label="确认密码" name="confirmPassword" required=true attrs="type='password'"/>
		    			 	
		    			 	<div class="form-item">
				    			<div class="form-element">
					    			<div class="buttons"><input id="submit-password-button" type="submit" value="提 交" /></div>
				    			</div>
				    		</div>
		    			 </div>
		    		</form>
		    	</div>
		    	<div id="services">
		    	</div>
		    </div>
	    </div>
	</div>
	<script type="text/javascript" src="/static/scripts/settings.js"></script>
	<script type="text/javascript">
	    window._EB_SETTINGS_DATA = {
	    	userId: '${principal.id}',
	    	username: '${principal.username}',
	    	sessionId: '${req.session.id}'
	    }
	</script>
</@layout.masterPage>