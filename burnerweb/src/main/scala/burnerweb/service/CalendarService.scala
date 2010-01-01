package burnerweb.service


import org.springframework.stereotype.Service
import burnerweb.persistence.BaseDao
import org.springframework.beans.factory.annotation.Autowired
import burnerweb.model.Calendar
import org.springframework.transaction.annotation.Transactional

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-28
 * Time: 10:54:59
 */

@Service("calendarService")
class CalendarService  {

  private var dao: BaseDao = null

  def saveCalendar(cal: Calendar) = { dao.persist(cal) }
  def findAllCalendars = { dao.find[Calendar]("SELECT FROM Calendar") }
  def findCalendarById(id: Long) = { dao.find[Calendar](classOf[Calendar], id)}

  @Autowired
  def setDao(dao:BaseDao) = {this.dao = dao}

}