package torch.analysis.test;

import torch.analysis.SegmentModule;
import torch.analysis.algorithm.ComplexAlgorithm;
import torch.analysis.model.Word;

import com.google.inject.Guice;
import com.google.inject.Injector;

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

        String content = "很久以前，有一片江湖，有位黑老大，他负责专门制定江湖游戏规则，所有的手下都必须遵守。老大一般不亲自出面，所有打打杀杀、抢钱掠物的事情都是手下那些小弟们干。不过，所得利益绝大多数是要上缴到老大手里，小弟们只能分点残羹冷炙";

        Iterable<Word> iter = sa.segment(content.toCharArray());

        for(Word w : iter){
            System.out.println("word: " + w);
        }

    }
}
