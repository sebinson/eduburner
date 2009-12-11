package readerside.test.model;

import org.testng.annotations.Test;
import readerside.model.GrEntry;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-11-29
 * Time: 18:25:14
 */
public class TestGrEntry {

    @Test
    public void doTest(){
        GrEntry entry = new GrEntry();
        entry.addLikingUserId("abcde");
        entry.addLikingUserId("def");

        System.out.println("liking user ids: " + entry.getLikingUserIds());
    }
}
