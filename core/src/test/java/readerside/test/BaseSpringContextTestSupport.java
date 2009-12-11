package readerside.test;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-29
 * Time: 2:28:25
 */

@ContextConfiguration(locations = { "classpath:applicationContext-dao.xml" })
@TransactionConfiguration(defaultRollback = true)
public class BaseSpringContextTestSupport extends AbstractTransactionalTestNGSpringContextTests {

}
