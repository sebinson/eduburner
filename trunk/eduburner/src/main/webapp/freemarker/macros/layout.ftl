<#macro masterPage title="" css=[] js=[] initType="">
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>
      <!-- HTTP 1.1 -->
      <meta http-equiv="Cache-Control" content="no-store"/>
      <!-- HTTP 1.0 -->
      <meta http-equiv="Pragma" content="no-cache"/>
      <!-- Prevents caching at the Proxy Server -->
      <meta http-equiv="Expires" content="0"/>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	  
	  <link type="text/css" rel="stylesheet" href="${base}/styles/reset-min.css" />
      <link type="text/css" rel="stylesheet" href="${base}/styles/fonts-min.css" />
      <link type="text/css" rel="stylesheet" href="${base}/styles/grids-min.css" />
      <link type="text/css" rel="stylesheet" href="${base}/styles/base-min.css" />
      
      <#list css as cssFile>   		
      	  <style type="text/css" media="all">@import ${base}/${cssFile};</style>
      </#list>

      <script type="text/javascript" src="${base}/scripts/jquery-1.2.6.js"></script>
      
      <#list js as jsFile>   		
		  <script type="text/javascript" src="${base}/${jsFile}"></script>
      </#list>
      <title>${title} | EduBurner</title>
      
      <!-- 在此切换初始化代码 -->
      <script type="text/javascript">
      	$(document).ready(function(){
      		
      	});
      </script>
      
  </head>
  <body>
      <div class="yui-d1">
         <div id="header">
         </div>
         
      	 <div class="yui-t2">
      	     <div class="yui-b">
      	     </div>
      	     <div class="yui-main">
      	         <div id="main" class="yui-b">
      	         </div>
      	     </div>
      	 </div>
      	 
      	 <div id="footer">
      	 </div>
      </div>
  </body>
</html>

</#macro>