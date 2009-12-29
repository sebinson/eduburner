package burnerweb.controller

import org.springframework.stereotype.Controller
import burnerweb.service.CalendarService
import org.springframework.beans.factory.annotation.Autowired
import burnerweb.model.Calendar
import org.springframework.web.bind.annotation.{ModelAttribute, RequestMethod, RequestMapping}
import org.springframework.validation.BindingResult
import org.springframework.ui.Model
/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-28
 * Time: 18:11:06
 */
@Controller
@RequestMapping(Array("/calendar"))
class CalendarController extends BaseController{

  private var calendarService: CalendarService = null

  @RequestMapping{val method = Array(RequestMethod.POST)}
  def create(@ModelAttribute("calendar") calendar:Calendar, br:BindingResult, model:Model): String = {
     println("go to create method.")
     calendarService.saveCalendar(calendar)
     "home"
  }

  @Autowired
  def setCalendarService(calendarService: CalendarService) = {this.calendarService = calendarService}
}