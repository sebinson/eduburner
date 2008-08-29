package eduburner.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;

@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class BaseSpringContextTestSupport extends
		AbstractTransactionalTestNGSpringContextTests {

}
