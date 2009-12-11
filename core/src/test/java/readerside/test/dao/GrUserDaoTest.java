package readerside.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;
import readerside.model.EntryData;
import readerside.model.GrEntry;
import readerside.model.GrFeed;
import readerside.model.GrUser;
import readerside.persistence.IDao;
import readerside.test.BaseSpringContextTestSupport;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-29
 * Time: 2:29:41
 */
public class GrUserDaoTest extends BaseSpringContextTestSupport{

    @Autowired
    @Qualifier("dao")
    private IDao dao;

    @Test
    public void testSaveUser(){
        GrUser user = new GrUser();

        user.setGrUserId("adadadfadfa");
        user.setLastFetchTime(new Date());

        dao.save(user);
    }

    //@Test
    public void testFeed(){
        GrFeed grFeed = dao.getInstanceById(GrFeed.class, "40288184253cc1c701253cc1d9880001");

        List<GrEntry> grEntries = dao.findBy(GrEntry.class, "grFeedId", grFeed.getGrFeedId());



        for(GrEntry e : grEntries){
            EntryData es = dao.findUniqueBy(EntryData.class, "grEntryId", e.getGrEntryId());

            System.out.println("summary: " + es.getSummary());
        }
    }
}
