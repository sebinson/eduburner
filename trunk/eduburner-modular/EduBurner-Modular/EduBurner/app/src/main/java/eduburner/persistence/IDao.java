package eduburner.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;

public interface IDao {
	
	public List<?> find(String queryString) throws DataAccessException;
	
	public List<?> find(String queryString, Object value) throws DataAccessException;
	
	public List<?> find(final String queryString, final Object... values) throws DataAccessException;
	
	<T> List<T> find(String hql, Map<String, Object> values);

	<T> List<T> find(Class<T> type, Criterion... criterions);

	<T> List<T> findBy(Class<T> type, String propertyName, Object value);

	public <T> T getInstanceById(Class<T> type, Serializable id);

	public <T> List<T> getAllInstances(Class<T> type);

	public List<?> getInstancesByDetachedCriteria(DetachedCriteria criteria);

	public Object getUniqueInstanceByDetachedCriteria(DetachedCriteria criteria);

	public List<?> getInstancesByDetachedCriteria(DetachedCriteria criteria,
			int startIndex, int maxResults);

	public Object getUniqueInstanceByExample(Object example);

	public List<?> getInstancesByExample(Object example);

	public <T> List<T> getInstancesByNamedQueryAndNamedParam(
			Class<T> entityClass, String queryName, String[] paramNames,
			Object[] values) throws DataAccessException;

	public <T> void save(T instance);

	public <T> void update(T instance);

	public <T> void saveOrUpdate(T instance);

	public <T> T merge(T instance);

	public void remove(Object instance);

	public void removeAll(Collection<?> entities);

	/**
	 * 
	 * @return a List containing all the classes this persistence service knows
	 *         about
	 */
	public List<?> getAllTypes();

	public <T> List<T> findByValueBean(String queryString, T valueBean);

	<T> T findUnique(String hql, Object... values);

	<T> T findUnique(String hql, Map<String, Object> values);

	int batchExecute(String hql, Object... values);

	int batchExecute(String hql, Map<String, Object> values);

	<T> Page<T> findPage(Page<T> page, String hql, Object... values);

	<T> Page<T> findPage(Page<T> page, String hql, Map<String, Object> values);

	<T> Page<T> findPage(Class<?> type, Page<T> page, Criterion... criterions);

	Query createQuery(String queryString, Object... values);

	Query createQuery(String queryString, Map<String, Object> values);

	<T> T findUniqueBy(Class type, String propertyName, Object value);

	Criteria createCriteria(Class type, Criterion... criterions);

}
