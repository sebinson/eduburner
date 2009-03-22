<form id="course-form" method="POST" action="<@spring.url '/courses'/>">
	<@spring.bind "course.*" />
	<div class="eb-form-container">
	  <div class="eb-form">
	  	<div class="form-header">
			<h2>创建课程</h2>
		</div>
		<@ui.springFormInput label="课程名称" name="course.title" attrs='style="width:253px"' required=true/>
		<@ui.springFormInput label="开始时间" name="course.startDate" attrs='style="width:120px" readonly' required=true/>
		<@ui.springFormInput label="结束时间" name="course.endDate" attrs='style="width:120px" readonly' required=true />
		<@ui.springFormTextarea label="课程描述" name="course.description" attrs='style="height:78px;width:253px"' required=true/>
		<@ui.springFormInput label="课程标签" name="course.tags" attrs='style="width:253px"' required=true desc="标签之间以逗号分隔"/> <!--TODO: 提供一个选择标签的方式-->
		<div class="seperator"></div>
		<div class="form-item">
			<div class="form-element">
    			<div class="buttons"><input id="submit-button" type="submit" value="创  建" /></div>
			</div>
		</div>
	  </div>
	</div>
</form>
<div id="cal1Container" style="position:absolute;z-index:1;display:none"></div>
