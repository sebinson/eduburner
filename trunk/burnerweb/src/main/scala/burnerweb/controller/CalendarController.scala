package burnerweb.controller

import org.springframework.stereotype.Controller
import burnerweb.service.CalendarService
import org.springframework.beans.factory.annotation.Autowired
import burnerweb.model.Calendar
import org.springframework.web.bind.annotation.{ModelAttribute, RequestMethod, RequestMapping}
import org.springframework.validation.BindingResult
import org.springframework.ui.Model
import reflect.BeanProperty

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-28
 * Time: 18:11:06
 */

@Controller
class CalendarController extends BaseController{

  @Autowired
  @BeanProperty
  var calendarService: CalendarService = _

  @RequestMapping{val value = Array("/calendar.*"), val method = Array(RequestMethod.POST)}
  def create(@ModelAttribute("calendar") calendar:Calendar, br:BindingResult, model:Model){
      println("go to create method.")
  }

}