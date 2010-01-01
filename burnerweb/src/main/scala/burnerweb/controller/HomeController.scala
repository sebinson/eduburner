package burnerweb.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import burnerweb.model.Calendar
import org.springframework.ui.Model
import burnerweb.service.CalendarService
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-27
 * Time: 23:58:26
 */

@Controller
class HomeController{

  private var calendarService: CalendarService = null

  @RequestMapping(Array("/"))
  def home(model: Model):String = {
    val calendar = new Calendar()
    model.addAttribute("calendar", calendar)
    val allCalendars = calendarService.findAllCalendars
    println("cal length: " + allCalendars.toString)
    val c: Option[Calendar] = calendarService.findCalendarById(5L)
    println("end year: " + c.get.endYear)
    "home"
  }

  @Autowired
  def setCalendarService(calendarService: CalendarService) = {this.calendarService = calendarService}

}