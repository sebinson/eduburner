package burnerweb.scala.persistence

import javax.persistence.{Persistence, EntityManagerFactory}

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-27
 * Time: 21:51:49
 */

object EMF {

  val get: EntityManagerFactory = Persistence.createEntityManagerFactory("transactions-optional") 

}