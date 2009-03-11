package eduburner.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import eduburner.persistence.IDao;

public class BaseManager implements IManager {
	@Autowired
	@Qualifier("dao")
	protected IDao dao;

	public void setDao(IDao dao) {
		this.dao = dao;
	}
}
