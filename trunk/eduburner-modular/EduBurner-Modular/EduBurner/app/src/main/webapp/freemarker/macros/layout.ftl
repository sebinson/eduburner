<#macro masterPage title="" css=[] js=[]>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>

      <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	  
      <link type="text/css" rel="stylesheet" href="${base}/static/styles/yui/reset-fonts-grids.css" />
      <link type="text/css" rel="stylesheet" href="${base}/static/styles/main.css" />
      <link type="text/css" rel="stylesheet" href="${base}/static/styles/tools.css" />
      <link type="text/css" rel="stylesheet" href="${base}/static/styles/fragments.css" />
      
      <#list css as cssFile>   		
      	  <style type="text/css" media="all">@import ${base}/static/${cssFile};</style>
      </#list>
     
      <title>${title} | EduBurner</title>
      
  </head>
  <body>
      <div id="site-doc" class="yui-d0">
      	 <#-- begin of site header -->
         <div id="site-hd">
         	<@ui.header />
         </div>
         <#-- end of site header -->
         
      	 <div id="site-bd" class="yui-t2">
      	     <div id="site-nav" class="yui-b">
      	        <#-- nav bar -->
      	     	<@ui.navbar />
      	     </div>
      	     <div class="yui-main">
      	         <div id="main" class="yui-b">
      	            <#-- main content -->
      	         	<#nested />
      	         </div>
      	     </div>
      	 </div>
      	 
      	 <#-- begin of site footer -->
      	 <div id="site-ft">
      	    <@ui.footer />
      	 </div>
      	 <#-- end of site footer -->
      </div>
      
      <script type="text/javascript" src="${base}/static/scripts/jquery-1.3.2.min.js"></script>
      <#list js as jsFile>   		
		  <script type="text/javascript" src="${base}/static/scripts/${jsFile}"></script>
      </#list>
      <script type="text/javascript">
      	$(document).ready(function(){
      		
      	});
      </script>
  </body>
</html>

</#macro>