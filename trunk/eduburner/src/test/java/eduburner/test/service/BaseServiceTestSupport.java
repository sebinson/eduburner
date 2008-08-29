package eduburner.test.service;

import eduburner.test.BaseSpringContextTestSupport;


public class BaseServiceTestSupport extends BaseSpringContextTestSupport {

	public int getCount(String sql) {
		return simpleJdbcTemplate.queryForInt(sql);
	}

}
