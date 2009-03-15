package eduburner.persistence;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.persistence.PersistenceException;

import org.hibernate.Criteria;
import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * @author zhangyf@gmail.com
 */
@SuppressWarnings("unchecked")
public class BaseHibernateDao extends HibernateDaoSupport implements IDao {
	
	@Override
	public List<?> find(String queryString) throws DataAccessException {
		return getHibernateTemplate().find(queryString);
	}

	@Override
	public List<?> find(String queryString, Object value)
			throws DataAccessException {
		return getHibernateTemplate().find(queryString, value);
	}

	@Override
	public List<?> find(String queryString, Object... values)
			throws DataAccessException {
		return getHibernateTemplate().find(queryString, values);
	}

	@Override
	public List<?> getAllInstances(Class<?> type) {
		return getHibernateTemplate().find("from " + type.getName());
	}

	@Override
	public <T> T getInstanceById(Class<T> type, Serializable id) {
		return (T) getHibernateTemplate().get(type.getName(), id);
	}

	@Override
	public List<?> getInstancesByDetachedCriteria(
			final DetachedCriteria criteria) {
		return (List<?>) getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return criteria.getExecutableCriteria(session).list();
					}
				});
	}

	@Override
	public Object getUniqueInstanceByDetachedCriteria(
			final DetachedCriteria criteria) {
		return (Object) getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return criteria.getExecutableCriteria(session)
								.uniqueResult();
					}
				});
	}

	public List<?> getInstancesByDetachedCriteria(
			final DetachedCriteria criteria, final int firstResult,
			final int maxResults) {
		return (List<?>) getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Criteria criteria1 = criteria
								.getExecutableCriteria(session);
						criteria1.setFirstResult(firstResult);
						criteria1.setMaxResults(maxResults);
						return criteria1.list();
					}
				});
	}

	@Override
	public Object getUniqueInstanceByExample(final Object example) {
		return (Object) getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {
					@Override
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						// create Criteria instance
						Criteria searchCriteria = session
								.createCriteria(example.getClass());
						// loop over the example object's PropertyDescriptors
						searchCriteria
								.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
						return searchCriteria.uniqueResult();
					}
				});
	}

	public List<?> getInstancesByExample(final Object example) {
		return (List) getHibernateTemplate().executeWithNativeSession(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						// create Criteria instance
						Criteria searchCriteria = session
								.createCriteria(example.getClass());
						// loop over the example object's PropertyDescriptors
						searchCriteria
								.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
						return searchCriteria.list();
					}
				});
	}

	public <T> List<T> getInstancesByNamedQueryAndNamedParam(
			Class<T> entityClass, String queryName, String[] paramNames,
			Object[] values) throws DataAccessException {
		List<T> results = (List<T>) getHibernateTemplate()
				.findByNamedQueryAndNamedParam(queryName, paramNames, values);
		return results;
	}

	public <T> void save(T instance) {
		try {
			getHibernateTemplate().save(instance);
		} catch (DataAccessException dex) {
			throw new PersistenceException(dex);
		}
	}

	public <T> void update(T instance) {
		try {
			getHibernateTemplate().update(instance);
		} catch (DataAccessException dex) {
			throw new PersistenceException(dex);
		}
	}

	public <T> void saveOrUpdate(T instance) {
		try {
			getHibernateTemplate().saveOrUpdate(instance);
		} catch (DataAccessException dex) {
			throw new PersistenceException(dex);
		}
	}

	public <T> T merge(T instance) {
		return (T) getHibernateTemplate().merge(instance);
	}

	public void remove(Object instance) {
		// merge first to avoid NonUniqueObjectException
		getHibernateTemplate().delete(getHibernateTemplate().merge(instance));
	}

	public void reattach(Object model) {
		getSession().lock(model, LockMode.NONE);
	}

	public List<Class> getAllTypes() {
		ArrayList<Class> allTypes = new ArrayList<Class>();
		for (Iterator iter = getSessionFactory().getAllClassMetadata().values()
				.iterator(); iter.hasNext();) {
			ClassMetadata classMeta = (ClassMetadata) iter.next();
			allTypes.add(classMeta.getMappedClass(EntityMode.POJO));
		}
		return allTypes;
	}

	public void removeAll(Collection<?> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	@Override
	public <T> List<T> findByValueBean(String queryString, T valueBean) {
		return getHibernateTemplate().findByValueBean(queryString, valueBean);
	}
}
