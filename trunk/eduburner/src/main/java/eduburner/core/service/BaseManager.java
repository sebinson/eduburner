package eduburner.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import eduburner.core.persistence.IDao;

public class BaseManager implements IManager {
	@Autowired
	@Qualifier("dao")
	protected IDao dao;

	public void setDao(IDao dao) {
		this.dao = dao;
	}
}
