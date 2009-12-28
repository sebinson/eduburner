package burnerweb.service


import org.springframework.stereotype.Service
import burnerweb.persistence.BaseDao
import org.springframework.beans.factory.annotation.Autowired
import burnerweb.model.Calendar

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-28
 * Time: 10:54:59
 */

@Service("calendarService")
class CalendarService  {

  @Autowired
  var dao: BaseDao = _

  def saveCalendar(cal: Calendar) = dao.persist(cal)

}