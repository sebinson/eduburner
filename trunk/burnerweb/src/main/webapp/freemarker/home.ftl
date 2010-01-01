<@layout.masterPage>
<div class="ae-message">
    说明：本工具为Google日历添加按年份重复的农历生日，填写完相关信息后导出ics文件，将导出的ics文件导入到google日历里。
</div>
<div id="validate-info" class="ae-errorbox" style="display:none">
</div>
<div class="ae-message ae-rounded-sml spur-cal-form">
    <form id="cal-form" method="POST" action="/calendar">
        <@spring.bind "calendar.*"/>
        <div class="label-row">
            <label for="summary">内容:</label>
        </div>
        <div class="input-row">
            <input id="summary" name="calendar.summary" class="text fieldinput"/>
        </div>

        <div class="label-row">
            <label for="birthday">农历生日:</label>
        </div>
        <div class="input-row">
            <input id="birthday" name="calendar.lunarBirthday" class="text" type="text" readonly="readonly"/>
            <div id="calContainer" style="display:none;position:absolute;z-index:10001"></div>
        </div>

        <div class="label-row">
            <label>年份范围:</label>
        </div>
        <div class="input-row">
            <select id="start-year" name="calendar.startYear">
                <#list 1980..2009 as i>
                    <option value="${i}" <#if i==2009>selected="selected"</#if>>${i}</option>
                </#list>
            </select>
            到
            <select id="end-year" name="calendar.endYear"/>
                <#list 1980..2050 as i>
                    <option value="${i}" <#if i==2019>selected="selected"</#if>>${i}</option>
                </#list>
            </select>
        </div>

        <div class="label-row">
            <label for="location">地点:</label>
        </div>
        <div class="input-row">
            <input id="location" name="calendar.location" class="text fieldinput"/>
        </div>

        <div class="label-row">
            <label for="description">描述:</label>
        </div>
        <div class="input-row">
            <textarea id="description" name="calendar.description" style="height:80px;"></textarea>
        </div>

        <div class="input-row">
            <!--
                   <input type="button" value="添加到谷歌日历" />&nbsp;&nbsp;
                   -->
            <input type="submit" value="生成ics文件"/>
        </div>
    </form>
</div>

<link rel="stylesheet" type="text/css"
      href="http://yui.yahooapis.com/2.8.0r4/build/calendar/assets/skins/sam/calendar.css">
<script type="text/javascript" src="http://yui.yahooapis.com/2.8.0r4/build/yahoo-dom-event/yahoo-dom-event.js"></script>
<script type="text/javascript" src="http://yui.yahooapis.com/2.8.0r4/build/calendar/calendar-min.js"></script>
<script type="text/javascript" src="/static/scripts/lunarbirthday.js"></script>
</@layout.masterPage>