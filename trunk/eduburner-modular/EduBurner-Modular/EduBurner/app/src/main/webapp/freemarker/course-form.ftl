<@layout.masterPage title="登录" >
    
    <h1>创建课程</h1>
    <p></p>
    
    <form id="course-form" method="POST" action="/courses">
    	<div class="eb-form">
    	  <div class="form-inner">
    	  	<div class="title-header">
    			<h2>创建课程</h2>
    		</div>
    		<div class="form-body">
    		<@ui.formTextField label="课程名称" name="j_username"/>
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