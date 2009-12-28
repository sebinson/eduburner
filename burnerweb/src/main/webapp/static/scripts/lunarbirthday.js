YUI().use('node', function(Y) {
	var cal, overCalendar = false, currentField = '';
	
	function setupListeners(){
		Y.on('mouseover', function(e){overCalendar=true}, '#calContainer');
		Y.on('mouseout', function(e){overCalendar=false}, '#calContainer')	
	}
	
	function getDate() {
        var calDate = this.getSelectedDates()[0];
        calDate = calDate.getFullYear() + '-' + (calDate.getMonth() + 1) + '-' + calDate.getDate();
        currentField.set('value', calDate);            
        overCalendar = false;
        hideCal();
    };
	
	function showCal(ev){
		var tar = ev.currentTarget; 
        currentField = tar;
		var xy = tar.getXY();
		xy[1] += 21;
		cal.render();
		Y.one('#calContainer').setStyle('display', 'block').setXY(xy);
	}
	
	function hideCal(){
		if (!overCalendar) { 
			Y.one('#calContainer').setStyle('display', 'none');
        } 
	}
	
	function initCalendarCfg(cal){
		cal.cfg.setProperty("MDY_YEAR_POSITION", 1); 
        cal.cfg.setProperty("MDY_MONTH_POSITION", 2); 
		cal.cfg.setProperty("MDY_DAY_POSITION", 3); 
		cal.cfg.setProperty("MY_YEAR_POSITION", 1); 
		cal.cfg.setProperty("MY_MONTH_POSITION", 2);
		
		cal.cfg.setProperty("MONTHS_SHORT",   ["1\u6708", "2\u6708", "3\u6708", "4\u6708", "5\u6708", "6\u6708", "7\u6708", "8\u6708", "9\u6708", "10\u6708", "11\u6708", "12\u6708"]); 
		cal.cfg.setProperty("MONTHS_LONG",    ["1\u6708", "2\u6708", "3\u6708", "4\u6708", "5\u6708", "6\u6708", "7\u6708", "8\u6708", "9\u6708", "10\u6708", "11\u6708", "12\u6708"]); 
		cal.cfg.setProperty("WEEKDAYS_1CHAR", ["\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"]); 
		cal.cfg.setProperty("WEEKDAYS_SHORT", ["\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"]); 
		cal.cfg.setProperty("WEEKDAYS_MEDIUM",["\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"]); 
		cal.cfg.setProperty("WEEKDAYS_LONG",  ["\u65e5", "\u4e00", "\u4e8c", "\u4e09", "\u56db", "\u4e94", "\u516d"]); 
		 
		cal.cfg.setProperty("MY_LABEL_YEAR_POSITION",  1); 
		cal.cfg.setProperty("MY_LABEL_MONTH_POSITION",  2); 
		cal.cfg.setProperty("MY_LABEL_YEAR_SUFFIX",  "\u5E74"); 
		cal.cfg.setProperty("MY_LABEL_MONTH_SUFFIX",  ""); 
	}
	
	function onCalFormSubmit(e){
		var errorInfos = [];
		if(Y.one('#summary').get('value') == ''){
			errorInfos.push('请输入内容');
		}
		if(Y.one('#birthday').get('value') == ''){
			errorInfos.push('请输入农历生日');
		}
		if(errorInfos.length > 0){
			alert(errorInfos.join('\n'));
			e.halt();
			return false;
		}else{
			return true;
		}
	}
	
	function onDomReady(){
		cal = new YAHOO.widget.Calendar("cal","calContainer", {START_WEEKDAY: 1});
		initCalendarCfg(cal);
		cal.selectEvent.subscribe(getDate, cal, true);
        cal.renderEvent.subscribe(setupListeners, cal, true);

		Y.on('focus', showCal, '#birthday');
		Y.on('blur', hideCal, '#birthday')
		Y.on('submit', onCalFormSubmit, '#cal-form');
		
		document.getElementById('summary').focus();
	}
	
    //assign domready handler:
    Y.on("domready", onDomReady);
});