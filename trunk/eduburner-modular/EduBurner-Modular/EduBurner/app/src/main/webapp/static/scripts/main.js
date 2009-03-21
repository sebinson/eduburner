(function($){
	var Dom = YAHOO.util.Dom, Event = YAHOO.util.Event,
		cal1, overCalendar = false, currentField = '';
	
	var setupListeners = function() {
        Event.addListener('cal1Container', 'mouseover', function() {
            overCalendar = true;
        });
        Event.addListener('cal1Container', 'mouseout', function() {
            overCalendar = false;
        });
    };
	
	var getDate = function() {
        var calDate = this.getSelectedDates()[0];
        calDate = calDate.getFullYear() + '-' + (calDate.getMonth() + 1) + '-' + calDate.getDate();
        currentField.value = calDate;            
        overCalendar = false;
        hideCal();
    };
	
	var showCal = function(ev) { 
        var tar = Event.getTarget(ev); 
        currentField = tar; 
     
        var xy = Dom.getXY(tar), 
            date = Dom.get(tar).value; 
        //if (date) { 
        //    cal1.cfg.setProperty('selected', date); 
        //    cal1.cfg.setProperty('pagedate', new Date(date), true); 
        //} else { 
            cal1.cfg.setProperty('selected', ''); 
            cal1.cfg.setProperty('pagedate', new Date(), true); 
        //} 
        cal1.render(); 
        Dom.setStyle('cal1Container', 'display', 'block'); 
        xy[1] = xy[1] + 21; 
        Dom.setXY('cal1Container', xy); 
    };
 
    var hideCal = function() { 
        if (!overCalendar) { 
            Dom.setStyle('cal1Container', 'display', 'none'); 
        } 
    };
	
	var initCalendarCfg = function(cal){
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
	
	var HomePage = new Class({
		Extends: Page,
		
		buildState:function(){
			return this.id;
		},
		
		updateUi: function(state){
			
		}
	});
	
	var EditCoursePage = new Class({
		Extends: Page,
		
		buildState: function(){
			return this.id;
		},
		
		updateUi: function(state){
			this.loadPage();
		},
		
		loadPage: function(){
			var self = this;
			$.waiting.start();
			$.ajax({
				url:'/courses/new',
				type: 'GET',
				success: function(data){
					$.waiting.stop();
					$('#main-content').html(data);
					self.initPage();
				}
			});
		},
		
		initPage: function(){
			var submitBtn = new YAHOO.widget.Button('submit-button');
			$('ul.sidebar-block-list>li>a').removeClass('selected');
			$('#create-course-link').addClass('selected');
			
			cal1 = new YAHOO.widget.Calendar("cal1","cal1Container", {START_WEEKDAY: 1});
			initCalendarCfg(cal1);
			cal1.selectEvent.subscribe(getDate, cal1, true);
        	cal1.renderEvent.subscribe(setupListeners, cal1, true);
        	Event.addListener(['startDate', 'endDate'], 'focus', showCal);
        	Event.addListener(['startDate', 'endDate'], 'blur', hideCal);
        	cal1.render();
		}
		
	});
	
	window.HomePage = HomePage;
	window.EditCoursePage = EditCoursePage;
	
	PageMapping.extend({
		'main': HomePage,
		'editcourse': EditCoursePage
	});
})(jQuery);