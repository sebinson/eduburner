package eduburner.persistence;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.dao.DataAccessException;

public interface IDao {

	public <T> T getInstanceById(Class<T> type, Serializable id);

	public List<?> getAllInstances(Class<?> type);

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

	public void reattach(Object model);

	/**
	 * 
	 * @return a List containing all the classes this persistence service knows
	 *         about
	 */
	public List<?> getAllTypes();

	public <T> List<T> findByValueBean(String queryString, T valueBean);
}
