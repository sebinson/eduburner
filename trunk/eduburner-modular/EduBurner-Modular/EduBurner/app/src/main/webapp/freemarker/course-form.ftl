<@layout.masterPage title="登录" >    
    <form id="course-form" method="POST" action="/courses">
    	<@spring.bind "course.*" />
    	<div class="eb-form">
    	  <div class="form-inner">
    	  	<div class="title-header">
    			<h2>创建课程</h2>
    		</div>
    		<div class="form-body">
    		<@ui.formField label="课程名称" name="course.title" attrs='class="text"' tag="spring-input" attrs='style="width:253px"'/>
    		<@ui.formField label="课程描述" name="course.description" tag="spring-textarea" attrs='style="height:78px;width:253px"'/>
    		<div class="form-field">
    			<div class="buttons">
    				<input class="button" type="submit" value="创  建" />
    			</div>
    		</div>
    		</div>
    	  </div>
    	</div>
    </form>
    
</@layout.masterPage>