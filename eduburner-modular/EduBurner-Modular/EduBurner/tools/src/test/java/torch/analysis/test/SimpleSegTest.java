package torch.analysis.test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import torch.analysis.SegmentModule;
import torch.analysis.algorithm.ComplexAlgorithm;
import torch.analysis.model.Word;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-6
 * Time: 11:52:26
 */
public class SimpleSegTest {

    @org.junit.Test
    public void testSimpleSeg(){

        Injector injector = Guice.createInjector(new SegmentModule());

        ComplexAlgorithm sa = injector.getInstance(ComplexAlgorithm.class);
        //SimpleAlgorithm sa = injector.getInstance(SimpleAlgorithm.class);

        String content = "研究生命起源";

        Iterable<Word> iter = sa.segment(content.toCharArray());

        for(Word w : iter){
            System.out.println("word: " + w);
        }

    }
}
