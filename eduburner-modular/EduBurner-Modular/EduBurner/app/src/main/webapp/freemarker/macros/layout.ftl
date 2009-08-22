<#macro masterPage title="" css=[] pageType="userPage">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>

      <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	  
      <link type="text/css" rel="stylesheet" href="/static/yui/reset-fonts-grids/reset-fonts-grids.css" />
      <link rel="stylesheet" type="text/css" href="/static/yui/button/assets/skins/sam/button.css">
      <link type="text/css" rel="stylesheet" href="/static/yui/calendar/assets/skins/sam/calendar.css"> 
      <link rel="stylesheet" type="text/css" href="/static/yui/container/assets/skins/sam/container.css">
      <link type="text/css" rel="stylesheet" href="/static/yui/datatable/assets/skins/sam/datatable.css">
      <link rel="stylesheet" type="text/css" href="/static/yui/tabview/assets/skins/sam/tabview.css"> 
       
      <link type="text/css" rel="stylesheet" href="/static/styles/main.css" />
      <link type="text/css" rel="stylesheet" href="/static/styles/form.css" />
      <link type="text/css" rel="stylesheet" href="/static/styles/fragments.css" />
      
      <script type="text/javascript" src="/static/scripts/libs/jquery-1.3.2.min.js"></script>
      
      <script type="text/javascript" src="/static/yui/yahoo-dom-event/yahoo-dom-event.js"></script> 
      <script type="text/javascript" src="/static/yui/element/element-min.js"></script>
      <script type="text/javascript" src="/static/yui/datasource/datasource-min.js"></script>
      <script type="text/javascript" src="/static/yui/json/json-min.js"></script>
      <script type="text/javascript" src="/static/yui/connection/connection-min.js"></script>
      <script type="text/javascript" src="/static/yui/get/get-min.js"></script>
      <script type="text/javascript" src="/static/yui/dragdrop/dragdrop-min.js"></script>
      <script type="text/javascript" src="/static/yui/calendar/calendar-min.js"></script> 
      <script type="text/javascript" src="/static/yui/button/button-min.js"></script>
      <script type="text/javascript" src="/static/yui/tabview/tabview-min.js"></script> 
      <script type="text/javascript" src="/static/yui/datatable/datatable-min.js"></script>
      <script type="text/javascript" src="/static/yui/container/container-min.js"></script> 
      <script type="text/javascript" src="/static/yui/uploader/uploader-min.js"></script> 
      
      <script type="text/javascript" src="/static/scripts/common.js"></script>
      
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
      <div id="doc4" class="yui-t2">
      	 <#-- begin of site header -->
         <div id="hd" class="eb-header">
         	<@ui.header />
         </div>
         <#-- end of site header -->
      	 <div id="bd" class="eb-body">
      	     <div id="nav" class="yui-b">
      	        <#if pageType == "userPage">
      	        	<@ui.userNavBar />
      	        <#elseif pageType == "adminPage">
      	            <@ui.adminNavBar />
      	        <#elseif pageType == "anonyPage">
      	        </#if>
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
	   <div id="top-loading" style="display:none">
		    加载中...
	   </div>
  </body>
</html>

</#macro>