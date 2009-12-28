package burnerweb.persistence


import javax.persistence.{PersistenceContext, EntityManager}

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-28
 * Time: 10:58:11
 */

class BaseDao  {

  @PersistenceContext
  val entityManager: EntityManager

/*
   * Worker for the previous two methods to handle creating
   * the query and then setting the parameters.
   */
  private def createAndParamify[A](queryName : String, params : Seq[Pair[String,Any]]) : ScalaQuery[A] = {
    val q = createNamedQuery[A](queryName)
    params.foreach(param => q.setParameter(param._1, param._2))
    
    q
  }
      

}