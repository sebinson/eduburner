<#macro masterPage title="" css=[] js=[]>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>

      <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	  
      <link type="text/css" rel="stylesheet" href="/static/yui/reset-fonts-grids/reset-fonts-grids.css" />
      <link rel="stylesheet" type="text/css" href="/static/yui/button/assets/skins/sam/button.css">
      <link type="text/css" rel="stylesheet" href="/static/styles/main.css" />
      <link type="text/css" rel="stylesheet" href="/static/styles/form.css" />
      <link type="text/css" rel="stylesheet" href="/static/styles/tools.css" />
      <link type="text/css" rel="stylesheet" href="/static/styles/fragments.css" />
      
      <script type="text/javascript" src="/static/scripts/libs/jquery-1.3.2.min.js"></script>
      <script type="text/javascript" src="/static/scripts/libs/mootools-1.2.1-core.js"></script>
      <script type="text/javascript" src="/static/yui/yahoo-dom-event/yahoo-dom-event.js"></script> 
      <script type="text/javascript" src="/static/yui/element/element-min.js"></script> 
      <script type="text/javascript" src="/static/yui/button/button-min.js"></script>
      <script type="text/javascript" src="/static/scripts/common.js"></script>
      
      <#list js as jsFile>   		
		  <script type="text/javascript" src="/static/scripts/${jsFile}"></script>
      </#list>
      
      <script type="text/javascript"> 
	      YAHOO.util.Event.onDOMReady(function(){
	          $.utils.setBodyClass();
	      }); 
	  </script> 
      
      <#list css as cssFile>   		
      	  <style type="text/css" media="all">@import /static/${cssFile};</style>
      </#list>
     
      <title>${title} | EduBurner</title>
      
  </head>
  <body>
  	  <iframe id="hist-iframe" class="hidden"></iframe>
      <input id="hist-field" type="hidden"/>
      <div id="doc3" class="yui-t2">
      	 <#-- begin of site header -->
         <div id="hd" class="eb-header">
         	<@ui.header />
         </div>
         <#-- end of site header -->
         
      	 <div id="bd" class="eb-body">
      	     <div id="nav" class="yui-b">
      	        <#-- nav bar -->
      	     	<@ui.navbar />
      	     </div>
      	     <div id="yui-main">
      	         <div id="main" class="yui-b">
      	            <#-- main content -->
      	         	<#nested />
      	         </div>
      	     </div>
      	 </div>
      	 
      	 <#-- begin of site footer -->
      	 <div id="ft" class="eb-footer">
      	    <@ui.footer />
      	 </div>
      	 <#-- end of site footer -->
      </div>
      <div id="overlay" style="display:none"></div>
      <div id="loading" style="display:none">
    	<div class="loading-indicator">
			<img src="/static/images/indicator_blue.gif" width="32" height="32" style="float:left;vertical-align:top;"/>
		</div>
	 </div>
  </body>
</html>

</#macro>