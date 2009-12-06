package torch.analysis.test;

import analysis.SegmentModule;
import analysis.algorithm.ComplexAlgorithm;
import analysis.algorithm.SimpleAlgorithm;
import analysis.model.Word;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-6
 * Time: 11:52:26
 */
public class SimpleSegTest {

    @Test
    public void testSimpleSeg(){

        Injector injector = Guice.createInjector(new SegmentModule());

        ComplexAlgorithm sa = injector.getInstance(ComplexAlgorithm.class);

        String content = "研究生命起源";

        Word word = sa.next(content.toCharArray());
        System.out.println("word: " + word);
        while(word != null){
            word = sa.next(content.toCharArray());
            System.out.println("word: " + word);
        }
    }
}
