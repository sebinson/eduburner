/*
 * Created by IntelliJ IDEA.
 * User: zhangyf
 * Date: 2009-12-3
 * Time: 21:53:38
 */
package torch.analysis;

import torch.analysis.rule.IRule;
import torch.analysis.rule.LagestAvgWordLenRule;
import torch.analysis.rule.LargestSumMorphemicFreedomDegreeRule;
import torch.analysis.rule.MaxMatchRule;
import torch.analysis.rule.SmallestVarianceRule;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class SegmentModule extends AbstractModule {

    protected void configure() {
        bind(IRule.class)
                .annotatedWith(Names.named("SmallestVarianceRule"))
                .to(SmallestVarianceRule.class);
        bind(IRule.class)
                .annotatedWith(Names.named("LargestSumMorphemicFreedomDegreeRule"))
                .to(LargestSumMorphemicFreedomDegreeRule.class);
        bind(IRule.class)
                .annotatedWith(Names.named("LagestAvgWordLenRule"))
                .to(LagestAvgWordLenRule.class);
        bind(IRule.class)
                .annotatedWith(Names.named("MaxMatchRule"))
                .to(MaxMatchRule.class);

        bind(String.class)
                .annotatedWith(Names.named("dictDirPath"))
                .toInstance("E:\\mmseg-v0.3\\MMSeg\\wordlist");


    }
}
