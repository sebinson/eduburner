package eduburner.persistence.user;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import eduburner.entity.Entry;
import eduburner.entity.user.UserData;
import eduburner.persistence.BaseHibernateDao;
import eduburner.persistence.Page;

@Transactional
public class UserDao extends BaseHibernateDao implements IUserDao {

	@Override
	public Page<Entry> getHomePageEntriesForUser(final UserData user, final int pageNo) {
		
		return getHibernateTemplate().executeWithNativeSession(new HibernateCallback<Page<Entry>>() {
			@Override
			public Page<Entry> doInHibernate(Session session)
					throws HibernateException, SQLException {
				List<String> userIds = Lists.newArrayList();
				userIds.add(user.getId());
				for(UserData ud : user.getFriends()){
					userIds.add(ud.getId());
				}
				Query countQuery = session.createQuery("select count(*) from Entry as e where e.user.id in (:userIds)");
				countQuery.setParameterList("userIds", userIds);
				long totalCount = (Long)countQuery.uniqueResult();
				
				Page<Entry> page = new Page<Entry>();
				page.setPageNo(pageNo);
				page.setTotalCount(totalCount);
				
				Query pageQuery = session.createQuery("from Entry as e where e.user.id in (:userIds) order by e.published desc");
				pageQuery.setParameterList("userIds", userIds);
				
				pageQuery.setFirstResult(page.getFirst() - 1);
				pageQuery.setMaxResults(page.getPageSize());
				
				List l = pageQuery.list();
				page.setItems(l);
				
				return page;
			}
		});
	}
}
