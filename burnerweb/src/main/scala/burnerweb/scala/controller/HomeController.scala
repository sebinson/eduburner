package burnerweb.scala.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-27
 * Time: 23:58:26
 */

@Controller
class HomeController{

  @RequestMapping(Array("/"))
  def home:String = { "index" }

}