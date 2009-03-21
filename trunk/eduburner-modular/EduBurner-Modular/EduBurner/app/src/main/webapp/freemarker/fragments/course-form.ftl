<form id="course-form" method="POST" action="/courses">
	<@spring.bind "course.*" />
	<div class="eb-form-container">
	  <div class="eb-form">
	  	<div class="form-header">
			<h2>创建课程</h2>
		</div>
		<@ui.springFormInput label="课程名称" name="course.title" attrs='style="width:253px"' required=true/>
		<@ui.springFormTextarea label="课程描述" name="course.description" attrs='style="height:78px;width:253px"' required=true/>
		<div class="form-item">
			<div class="form-element">
    			<div class="buttons"><input id="submit-button" type="submit" value="创  建" /></div>
			</div>
		</div>
	  </div>
	</div>
</form>
