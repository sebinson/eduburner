<#macro masterPage title="Spur Tools" css=[] >
<!DOCTYPE html>
<html lang="zh-CN">
    <head>
        <meta http-equiv=”Content-Type” content=”text/html; charset=utf-8” />
		<link rel="stylesheet" href="/static/styles/style.css" type="text/css" />
		<link rel="stylesheet" href="/static/styles/spur.css" type="text/css" />
        <script type="text/javascript" src="http://yui.yahooapis.com/3.0.0/build/yui/yui-min.js"></script>
        <title>${title}</title>
    </head>
    <body class="yui-skin-sam">
      <div class="g-doc-1024">
        <div id="hd" style="background-color: white;" class="g-section g-tpl-50-50 g-split" >
            <div class="g-unit g-first">
                <p><a class="ae-ir" id="ae-logo" href="/">Spur Tools</a></p>
            </div>
            <div id="ae-userinfo" class="g-unit">
                <ul>
                    <!--
                	{% if user.is_sign_in %}
					<li><strong>{{ user.email }}</strong> |</li>
					{% end %}
					{% if user.is_sign_in %}
          		    <li><a href = "{{ sign_out_url }}" >登出</a></li>
            		{% else %}
                	<li><a href = "{{ sign_in_url }}" >登录</a></li>
            		{% end %}
            		-->
                </ul>
            </div>
        </div>
		<div class="spur-bar">
			<div class="g-unit" style="float:right;">
			   <span><a href="mailto:rockmaple@gmail.com" >问题和建议</a></span>
			   &nbsp;&nbsp;
			   <span><a href="http://rockmaple.appspot.com" target="_blank">博客</a></span>
			</div>
		</div>
		<div id="bd" class="g-section g-tpl-160">
			<div id="ae-lhs-nav-c" class="g-unit g-first">
			  <div class="g-c" id="ae-lhs-nav">
				<div id="ae-nav">
				    <ul>
				        <li id="ae-nav-dashboard">
				        	<!--
							<span class="ae-nav-section"> 工具</span>
							-->
				            <ul>
				                <li>
				                    <a href="#" class="ae-nav-selected"> 农历生日</a>
				                </li>
				            </ul>
				        </li>
				    </ul>
				</div>
			  </div>
			</div>
			<div id="ae-content" class="g-unit">
                <#nested />
			</div>
		</div>

		<div id="ft">
			Spur Tools. by Rockmaple
		</div>
	  </div>

    </body>
</html>
</#macro>
