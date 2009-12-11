package readerside.persistence;

import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import readerside.util.ReflectionUtils;

import javax.persistence.PersistenceException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-26
 * Time: 8:09:05
 */
@SuppressWarnings("unchecked")
@Transactional
public class BaseHibernateDao extends HibernateDaoSupport implements IDao {

	private static final Logger logger = LoggerFactory.getLogger(BaseHibernateDao.class);

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
	public <T> List<T> findByValueBean(String queryString, T valueBean) {
		return getHibernateTemplate().findByValueBean(queryString, valueBean);
	}

	@Override
	public <T> List<T> getAllInstances(Class<T> type) {
		return getHibernateTemplate().find(String.format("from %s", type.getName()));
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
		return getHibernateTemplate().executeWithNativeSession(
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
		return getHibernateTemplate().executeWithNativeSession(
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
        return (List<T>) getHibernateTemplate()
                .findByNamedQueryAndNamedParam(queryName, paramNames, values);
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
		return getHibernateTemplate().merge(instance);
	}

	public void remove(Object instance) {
		// merge first to avoid NonUniqueObjectException
		getHibernateTemplate().delete(getHibernateTemplate().merge(instance));
	}

	public List<Class> getAllTypes() {
		ArrayList<Class> allTypes = new ArrayList<Class>();
        for (Object o : getSessionFactory().getAllClassMetadata().values()) {
            ClassMetadata classMeta = (ClassMetadata) o;
            allTypes.add(classMeta.getMappedClass(EntityMode.POJO));
        }
		return allTypes;
	}

	public void removeAll(Collection<?> entities) {
		getHibernateTemplate().deleteAll(entities);
	}

	/************** below are taken from springside ********************/

	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 */
	@Override
	public <T> List<T> findBy(Class<T> type, final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(type, criterion);
	}

	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	@Override
	public <T> T findUniqueBy(Class type, final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName should not be null");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(type, criterion).uniqueResult();
	}

	/**
	 * 按HQL查询对象列表.
	 *
	 * @param values 命名参数,按名称绑定.
	 */
	@Override
	public <T> List<T> find(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按Criteria查询对象列表.
	 *
	 * @param criterions 数量可变的Criterion.
	 */
	@Override
	public <T> List<T> find(Class<T> type, final Criterion... criterions) {
		return createCriteria(type, criterions).list();
	}

	/**
	 * 按HQL查询唯一对象.
	 *
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	@Override
	public <T> T findUnique(final String hql, final Object... values) {
		return (T) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 按HQL查询唯一对象.
	 *
	 * @param values 命名参数,按名称绑定.
	 */
	@Override
	public <T> T findUnique(final String hql, final Map<String, Object> values) {
		return (T) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 */
	@Override
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * @return 更新记录数.
	 */
	@Override
	public int batchExecute(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 按HQL分页查询.
	 *
	 * @param page 分页参数.不支持其中的orderBy参数.
	 * @param hql hql语句.
	 * @param values 数量可变的查询参数,按顺序绑定.
	 *
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@Override
	public <T> Page<T> findPage(final Page<T> page, final String hql, final Object... values) {
		Assert.notNull(page, "page should not be null");

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);
		List result = q.list();

		page.setItems(result);
		return page;
	}

	/**
	 * 按HQL分页查询.
	 *
	 * @param page 分页参数.(不支持orderBy参数)
	 * @param hql hql语句.
	 * @param values 命名参数,按名称绑定.
	 *
	 * @return 分页查询结果, 附带结果列表及所有查询时的参数.
	 */
	@Override
	public <T> Page<T> findPage(final Page<T> page, final String hql, final Map<String, Object> values) {
		Assert.notNull(page, "page should not be null.");

		logger.debug("entering findPage method...");

		Query q = createQuery(hql, values);

		if (page.isAutoCount()) {
			long totalCount = countHqlResult(hql, values);
			page.setTotalCount(totalCount);
		}

		setPageParameter(q, page);

		List result = q.list();

		if(result == null){
			logger.warn("result is null ");
		}

		page.setItems(result);
		return page;
	}

	/**
	 * 按Criteria分页查询.
	 *
	 * @param page 分页参数.
	 * @param criterions 数量可变的Criterion.
	 *
	 * @return 分页查询结果.附带结果列表及所有查询时的参数.
	 */
	@Override
	public <T> Page<T> findPage(Class<?> type, final Page<T> page, final Criterion... criterions) {
		Assert.notNull(page, "page不能为空");

		Criteria c = createCriteria(type, criterions);

		if (page.isAutoCount()) {
			int totalCount = countCriteriaResult(c);
			page.setTotalCount(totalCount);
		}

		setPageParameter(c, page);
		List result = c.list();
		page.setItems(result);
		return page;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 *
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 *
	 * @param values 数量可变的参数,按顺序绑定.
	 */
	@Override
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString should not be null");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 *
	 * @param values 命名参数,按名称绑定.
	 */
	@Override
	public Query createQuery(final String queryString, final Map<String, Object> values) {
		Assert.hasText(queryString, "queryString should not be null");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 根据Criterion条件创建Criteria.
	 *
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 *
	 * @param criterions 数量可变的Criterion.
	 */
	@Override
	public Criteria createCriteria(Class type, final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(type);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 设置分页参数到Query对象,辅助函数.
     * @param page
     */
	protected <T> Query setPageParameter(final Query q, final Page<T> page) {
		//hibernate的firstResult的序号从0开始
		q.setFirstResult(page.getFirst() - 1);
		q.setMaxResults(page.getPageSize());
		return q;
	}

	/**
	 * 设置分页参数到Criteria对象,辅助函数.
	 */
	protected <T> Criteria setPageParameter(final Criteria c, final Page<T> page) {
		//hibernate的firstResult的序号从0开始
		c.setFirstResult(page.getFirst() - 1);
		c.setMaxResults(page.getPageSize());

		if (page.isOrderBySetted()) {
			String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
			String[] orderArray = StringUtils.split(page.getOrder(), ',');

			Assert.isTrue(orderByArray.length == orderArray.length, "分页多重排序参数中,排序字段与排序方向的个数不相等");

			for (int i = 0; i < orderByArray.length; i++) {
				if (Page.ASC.equals(orderArray[i])) {
					c.addOrder(Order.asc(orderByArray[i]));
				} else {
					c.addOrder(Order.desc(orderByArray[i]));
				}
			}
		}
		return c;
	}

	/**XXX: 这里导致hql只能用小写
	 *
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 *
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Object... values) {
		Long count = 0L;
		String fromHql = hql;
		//select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			count = findUnique(countHql, values);
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}
		return count;
	}

	/**
	 * 执行count查询获得本次Hql查询所能获得的对象总数.
	 *
	 * 本函数只能自动处理简单的hql语句,复杂的hql查询请另行编写count语句查询.
	 */
	protected long countHqlResult(final String hql, final Map<String, Object> values) {
		Long count = 0L;
		String fromHql = hql;
		//select子句与order by子句会影响count查询,进行简单的排除.
		fromHql = "from " + StringUtils.substringAfter(fromHql, "from");
		fromHql = StringUtils.substringBefore(fromHql, "order by");

		String countHql = "select count(*) " + fromHql;

		try {
			count = findUnique(countHql, values);
		} catch (Exception e) {
			throw new RuntimeException("hql can't be auto count, hql is:" + countHql, e);
		}

		return count;
	}

	/**
	 * 执行count查询获得本次Criteria查询所能获得的对象总数.
	 */
	protected int countCriteriaResult(final Criteria c) {
		CriteriaImpl impl = (CriteriaImpl) c;

		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();

		List<CriteriaImpl.OrderEntry> orderEntries = null;
		try {
			orderEntries = (List) ReflectionUtils.getFieldValue(impl, "orderEntries");
			ReflectionUtils.setFieldValue(impl, "orderEntries", new ArrayList());
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e);
		}

		// 执行Count查询
		int totalCount = (Integer) c.setProjection(Projections.rowCount()).uniqueResult();

		// 将之前的Projection,ResultTransformer和OrderBy条件重新设回去
		c.setProjection(projection);

		if (projection == null) {
			c.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		}
		if (transformer != null) {
			c.setResultTransformer(transformer);
		}
		try {
			ReflectionUtils.setFieldValue(impl, "orderEntries", orderEntries);
		} catch (Exception e) {
			logger.error("不可能抛出的异常:{}", e);
		}

		return totalCount;
	}





	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 *
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(Class type, final String propertyName, final Object newValue, final Object oldValue) {
		if (newValue == null || newValue.equals(oldValue))
			return true;
		Object object = findUniqueBy(type, propertyName, newValue);
		return (object == null);
	}


}

