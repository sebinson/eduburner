package burnerweb.model

import scala.reflect.BeanProperty
import javax.persistence._

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-28
 * Time: 18:16:37
 */
@MappedSuperclass
class BaseEntity {
  @Id
  @GeneratedValue {val strategy = GenerationType.IDENTITY}
  var id: Int = _

  @Version
  var version: Int = _

}