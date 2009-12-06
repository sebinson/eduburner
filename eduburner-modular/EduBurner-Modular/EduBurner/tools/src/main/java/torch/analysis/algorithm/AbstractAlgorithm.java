package torch.analysis.algorithm;

import com.google.common.collect.AbstractIterator;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import torch.analysis.model.Chunk;
import torch.analysis.model.Dictionary;
import torch.analysis.model.Word;
import torch.analysis.rule.IRule;
import torch.util.LanguageUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: rockmaple Date: 2009-12-3 Time: 22:09:19
 */
public abstract class AbstractAlgorithm {

    @Inject
    @Named("MaxMatchRule")
    protected IRule mmRule;
    @Inject
    @Named("LagestAvgWordLenRule")
    protected IRule lawlRule;
    @Inject
    @Named("LagestAvgWordLenRule")
    protected IRule svwlRule;
    @Inject
    @Named("LagestAvgWordLenRule")
    protected IRule lsdmfocwRule;
    @Inject
    private Dictionary dictionary;
    @Inject
    @Named("maxWordLength")
    private int maxWordLength;

    protected int index = 0;

    public Iterable<Word> segment(final char[] chars) {
        final Iterator<Word> iter = new WordIterator(AbstractAlgorithm.this, chars);
        return new Iterable<Word>(){
            @Override
            public Iterator<Word> iterator() {
                 return iter;
            }
        };


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

    protected abstract Chunk[] createChunks(char[] chars, int index2);

    protected Word[] findMatchWords(char[] chars, int index) {

        char c = chars[index];

        List matchWords = new ArrayList();

        StringBuilder sb = new StringBuilder();
        sb.append(c);

        String wordValue = sb.toString();
        if (dictionary.isMatched(wordValue)) {
            matchWords.add(dictionary.getWord(wordValue));
        }

        for (int i = 1; i < maxWordLength && ((i + index) < chars.length); i++) {
            if (LanguageUtil.isBasicLatin(chars[i + index]))
                break;
            sb.append(chars[i + index]);
            String temp = sb.toString();
            if (dictionary.isMatched(temp)) {
                matchWords.add(dictionary.getWord(temp));
            }
        }

        if (matchWords.isEmpty()) {
            matchWords.add(new Word(wordValue, Word.Type.UNRECOGNIZED));
        }

        Word[] words = new Word[matchWords.size()];
        matchWords.toArray(words);

        matchWords.clear();

        return words;
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


    private class WordIterator extends AbstractIterator<Word>{

        private AbstractAlgorithm algorithm;
        private char[] chars;

        public WordIterator(AbstractAlgorithm algorithm, char[] chars){
            this.algorithm = algorithm;
            this.chars = chars;
        }

        @Override
        protected Word computeNext() {
            if (index >= chars.length) {
                index = 0;
                return endOfData();
            }
            char current = chars[index];
            if (LanguageUtil.isBasicLatin(current)) {
                Word word = getBasicLatinWord(chars, index);
                index += word.getLength();
                return word;
            } else {
                Chunk[] chunks = createChunks(chars, index);
                Word word = getCJKWord(chunks);
                index += word.getLength();
                return word;
            }

        }
    }

    
}
