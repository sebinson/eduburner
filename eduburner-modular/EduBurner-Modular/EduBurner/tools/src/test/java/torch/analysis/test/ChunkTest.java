package torch.analysis.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import torch.analysis.model.Chunk;
import torch.analysis.model.Word;

/**
 * Created by IntelliJ IDEA.
 * User: zhangyf
 * Date: 2009-12-3
 * Time: 22:41:50
 */
public class ChunkTest {

    @Test
    public final void testGetAverageLength() {
        Word word1 = new Word("国际化", Word.CJK_WORD);
        Word word2 = new Word("国际", Word.CJK_WORD);
        Word word3 = new Word("国", Word.CJK_WORD);
        Word word4 = new Word("际", Word.CJK_WORD);
        Word word5 = new Word("化", Word.CJK_WORD);

        Chunk chunk1 = new Chunk(new Word[]{word1});
        Chunk chunk2 = new Chunk(new Word[]{word2, word5});
        Chunk chunk3 = new Chunk(new Word[]{word3, word4, word5});

        Assert.assertTrue(3D == chunk1.getAverageLength());
        Assert.assertTrue(1.5D == chunk2.getAverageLength());
        Assert.assertTrue(1D == chunk3.getAverageLength());
    }

    @Test
    public final void testGetVariance() {
        Word word1 = new Word("研究", Word.CJK_WORD);
        Word word2 = new Word("生命", Word.CJK_WORD);
        Word word3 = new Word("起源", Word.CJK_WORD);
        Word word4 = new Word("研究生", Word.CJK_WORD);
        Word word5 = new Word("命", Word.CJK_WORD);

        Chunk chunk1 = new Chunk(new Word[]{word1, word2, word3});
        Chunk chunk2 = new Chunk(new Word[]{word4, word5, word3});

        Assert.assertTrue(0D == chunk1.getVariance());
        Assert.assertTrue(Math.sqrt(2D / 3) == chunk2.getVariance());
    }

    @Test
    public final void testGetLength() {
        Word word1 = new Word("国际化", Word.CJK_WORD);
        Word word2 = new Word("国际", Word.CJK_WORD);
        Word word3 = new Word("国", Word.CJK_WORD);
        Word word4 = new Word("际", Word.CJK_WORD);
        Word word5 = new Word("化", Word.CJK_WORD);

        Chunk chunk1 = new Chunk(new Word[]{word1});
        Chunk chunk2 = new Chunk(new Word[]{word2, word5});
        Chunk chunk3 = new Chunk(new Word[]{word3, word4, word5});

        Assert.assertEquals(3, chunk1.getLength());
        Assert.assertEquals(3, chunk2.getLength());
        Assert.assertEquals(3, chunk3.getLength());
    }

    @Test
    public final void testGetDegreeOfMorphemicFreedom() {
        Word word1 = new Word("主要", Word.CJK_WORD);
        Word word2 = new Word("是", 3200626, Word.CJK_WORD);
        Word word3 = new Word("因为", Word.CJK_WORD);
        Word word4 = new Word("主", 224073, Word.CJK_WORD);
        Word word5 = new Word("要是", Word.CJK_WORD);

        Chunk chunk1 = new Chunk(new Word[]{word1, word2, word3});
        Chunk chunk2 = new Chunk(new Word[]{word4, word5, word3});

        Assert.assertTrue(Math.log((double) 3200626) == chunk1
                .getDegreeOfMorphemicFreedom());
        Assert.assertTrue(Math.log((double) 224073) == chunk2
                .getDegreeOfMorphemicFreedom());
    }

}
