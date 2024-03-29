package torch.analysis.test;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import torch.analysis.model.Chunk;
import torch.analysis.model.Word;
import torch.analysis.rule.IRule;
import torch.analysis.rule.LargestAvgWordLenRule;

/**
 * Created by IntelliJ IDEA.
 * User: zhangyf
 * Date: 2009-12-3
 * Time: 22:48:53
 *
 * 
 */
public class LAWLRuleTest {

   /* Chunk chunk1;
    Chunk chunk2;
    Chunk chunk3;

    @BeforeTest
    public void setUp() {
        Word word1 = new Word("国际化");
        Word word2 = new Word("国际", Word.Type.CJK_WORD);
        Word word3 = new Word("国", Word.Type.CJK_WORD);
        Word word4 = new Word("际", Word.Type.CJK_WORD);
        Word word5 = new Word("化", Word.Type.CJK_WORD);

        chunk1 = new Chunk(new Word[]{word1});
        chunk2 = new Chunk(new Word[]{word2, word5});
        chunk3 = new Chunk(new Word[]{word3, word4, word5});
    }

    @Test
    public final void testInvoke() {
		IRule lawlRule = new LargestAvgWordLenRule();
		Chunk[] chunks = new Chunk[] { chunk1, chunk2, chunk3 };
		Chunk[] lawlChunks = lawlRule.applyRule(chunks);
		Assert.assertEquals(chunk1, lawlChunks[0]);
	}*/

}
