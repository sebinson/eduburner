package burnerweb.controller

import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.beans.propertyeditors.{CustomNumberEditor, CustomDateEditor}
import java.text.SimpleDateFormat
import java.util.Date

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-28
 * Time: 19:09:23
 */

class BaseController {
  @InitBinder
  def initBinder(binder: WebDataBinder) {
    binder.registerCustomEditor(classOf[java.lang.Integer],
      new CustomNumberEditor(classOf[java.lang.Integer], true));
    binder.registerCustomEditor(classOf[java.lang.Long], new CustomNumberEditor(
      classOf[java.lang.Integer], true));
    var dateFormat: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    dateFormat.setLenient(false);
    binder.registerCustomEditor(classOf[java.util.Date], new CustomDateEditor(
      dateFormat, true));
  }

}