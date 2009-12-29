package burnerweb.service


import org.springframework.stereotype.Service
import burnerweb.persistence.BaseDao
import org.springframework.beans.factory.annotation.Autowired
import burnerweb.model.Calendar
import scala.reflect.BeanProperty

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-28
 * Time: 10:54:59
 */

@Service("calendarService")
class CalendarService  {

  private var dao: BaseDao = null

  def saveCalendar(cal: Calendar) = dao.persist(cal)
  def getAllCalendars() = dao.

  @Autowired
  def setDao(dao:BaseDao) = {this.dao = dao}

}