package torch.analysis.algorithm;

import com.google.common.base.Charsets;
import com.google.common.collect.AbstractIterator;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import torch.analysis.model.Chunk;
import torch.analysis.model.Dictionary;
import torch.analysis.model.Word;
import torch.analysis.rule.IRule;
import torch.util.LanguageUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by IntelliJ IDEA. User: rockmaple Date: 2009-12-3 Time: 22:09:19
 */
public abstract class AbstractAlgorithm {
    private static final Logger logger = LoggerFactory.getLogger(AbstractAlgorithm.class);

    @Inject
    @Named("MaxMatchRule")
    protected IRule mmRule;
    @Inject
    @Named("LargestAvgWordLenRule")
    protected IRule lawlRule;
    @Inject
    @Named("LargestAvgWordLenRule")
    protected IRule svwlRule;
    @Inject
    @Named("LargestAvgWordLenRule")
    protected IRule lsdmfocwRule;
    @Inject
    private Dictionary dictionary;
    @Inject
    @Named("maxWordLength")
    private int maxWordLength;

    protected AtomicInteger index = new AtomicInteger(0);

    public Iterable<Word> segment(final char[] chars) {
        final Iterator<Word> iter = new WordIterator(chars);
        return new Iterable<Word>() {
            @Override
            public Iterator<Word> iterator() {
                return iter;
            }
        };

    }

    protected abstract Chunk[] createChunks(char[] chars, int index2);

    //从词典里找出以index开始的所有匹配的词。
    protected Word[] findMatchWords(final char[] chars, final int index) {
        logger.debug("finding matching words for chars: " + new String(chars) + " index: " + index);
        return dictionary.findMatchWords(new String(chars), index);

    }

    protected Word getBasicLatinWord(char[] chars, int index) {
        StringBuffer basicLatinWord = new StringBuffer();
        while ((index < chars.length) && LanguageUtil.isBasicLatin(chars[index])) {
            if (Character.isWhitespace(chars[index])) {
                if (basicLatinWord.length() == 0) {
                    basicLatinWord.append(chars[index]);
                    index++;
                    return new Word(basicLatinWord.toString(),
                            Word.Type.BASICLATIN_WORD);
                } else {
                    return new Word(basicLatinWord.toString(),
                            Word.Type.BASICLATIN_WORD);
                }
            }
            basicLatinWord.append(chars[index]);
            index++;
        }

        return new Word(basicLatinWord.toString(), Word.Type.BASICLATIN_WORD);
    }

    protected Word getCJKWord(Chunk[] chunks) {
        Chunk[] chunkList = mmRule.applyRule(chunks);

        if (chunkList.length >= 2) {
            chunkList = lawlRule.applyRule(chunkList);
            if (chunkList.length >= 2) {
                chunkList = svwlRule.applyRule(chunkList);
                if (chunkList.length >= 2) {
                    chunkList = lsdmfocwRule.applyRule(chunkList);
                }
            }
        }

        Chunk chunk = chunkList[0];
        return chunk.getWords()[0];
    }


    private class WordIterator extends AbstractIterator<Word> {
        private char[] chars;

        public WordIterator(char[] chars) {
            this.chars = chars;
        }

        @Override
        protected Word computeNext() {
            if (index.get() >= chars.length) {
                index.set(0);
                return endOfData();
            }
            char current = chars[index.get()];
            if (LanguageUtil.isBasicLatin(current)) {
                Word word = getBasicLatinWord(chars, index.get());
                index.getAndAdd((word.getLength()));
                return word;
            } else {
                Chunk[] chunks = createChunks(chars, index.get());
                Word word = getCJKWord(chunks);
                index.getAndAdd((word.getLength()));
                return word;
            }

        }
    }


}
