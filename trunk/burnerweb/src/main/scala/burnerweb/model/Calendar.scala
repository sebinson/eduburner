package burnerweb.model

import reflect.BeanProperty
import javax.persistence.Entity
import java.util.Date

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-28
 * Time: 18:14:58
 */
@Entity
class Calendar extends BaseEntity{
  @BeanProperty
  var summary: String = _
  @BeanProperty
  var lunarBirthday: Date = _
  @BeanProperty
  var location: String = _
  @BeanProperty
  var description: String = _
  @BeanProperty
  var startYear: Int = _
  @BeanProperty
  var endYear: Int = _
  @BeanProperty
  var icsContent: String = _
}