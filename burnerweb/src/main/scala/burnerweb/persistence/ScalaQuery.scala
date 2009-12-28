package burnerweb.persistence


import java.util.{Calendar, Date}
import _root_.scala.collection.jcl.Conversions
import javax.persistence.{Query, FlushModeType, TemporalType}


/**
 * This class represents a Scalafied version of the JPA Query class. Big
 * features include better type safety and returning results as Lists. Instances
 * of this class should be obtained via a ScalaEntityManager instance.
 *
 * @author Derek Chen-Becker
 */
class ScalaQuery[A](val query: Query) {

  // value added methods


  /**
   * Returns the results of the query as a <code>Seq[A]</code>.
   */
  def findAll = getResultList()

  /**
   * Returns a single result of the query as an Option. If more than one
   * result is found, a NonUniqueResultException is thrown.
   */
  def findOne = Utils.findToOption[A](query.getSingleResult.asInstanceOf[A])

  /**
   * Sets the parameters for this query from the given name->value pairs.
   * An example is:
   *
   * <code>
   *   val query = ...
   *   query.setParams("name" -> "fred", "age" -> 12)
   * </code>
   */
  def setParams(params : Pair[String,Any]*) = {params.foreach(param => query.setParameter(param._1, param._2)); this}

  // methods defined on Query
  /**
   * Returns the results of the query as a Seq[A]
   */
  def getResultList() = Conversions.convertList[A](query.getResultList.asInstanceOf[java.util.List[A]])

  /**
   * Returns a single result of the query as an Option. If more than one
   * result is found, a NonUniqueResultException is thrown. If no
   * results are found, a NoResultException is thrown.
   */
  def getSingleResult() = query.getSingleResult.asInstanceOf[A]

  /**
   * Executes an update based on this query.
   */
  def executeUpdate() = query.executeUpdate()

  /**
   * Sets the maximum number of results that will be returned. Used for
   * pagination.
   */
  def setMaxResults(maxResult: Int) = {query.setMaxResults(maxResult);this}

  /**
   * Sets the index of the first result to be returned. Used for pagination.
   */
  def setFirstResult(startPosition: Int) = {query.setFirstResult(startPosition); this}

  /**
   * Sets the hints for this query. Hints are provider-specific.
   * @param hintName The name of the hint
   * @param value The value of the hint
   */
  def setHint(hintName: String, value: Any) = {query.setHint(hintName, value); this}

  /**
   * Sets the given named parameter for this query to the given value.
   */
  def setParameter(name: String, value: Any) = {query.setParameter(name, value); this}

  /**
   * Sets the given positional parameter for this query to the given value.
   */
  def setParameter(position: Int, value: Any) = {query.setParameter(position, value); this}

  /**
   * Sets the given time parameter to the given value, using the
   * precision specified by the TemporalType parameter.
   */
  def setParameter(name: String, value: Date, temporalType: TemporalType) = {query.setParameter(name, value, temporalType); this}

  /**
   * Sets the given time parameter to the given value, using the
   * precision specified by the TemporalType parameter.
   */
  def setParameter(position: Int, value: Date, temporalType: TemporalType) = {query.setParameter(position, value, temporalType); this}

  /**
   * Sets the given time parameter to the given value, using the
   * precision specified by the TemporalType parameter.
   */
  def setParameter(name: String, value: Calendar, temporalType: TemporalType) = {query.setParameter(name, value, temporalType); this}

  /**
   * Sets the given time parameter to the given value, using the
   * precision specified by the TemporalType parameter.
   */
  def setParameter(position: Int, value: Calendar, temporalType: TemporalType) = {query.setParameter(position, value, temporalType); this}

  /**
   * Sets the flush mode for the current query.
   */
  def setFlushMode(flushMode: FlushModeType) = {query.setFlushMode(flushMode); this}

}



