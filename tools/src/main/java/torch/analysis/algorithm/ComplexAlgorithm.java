package torch.analysis.algorithm;

import java.util.List;

import torch.analysis.model.Chunk;
import torch.analysis.model.Word;

import com.google.common.collect.Lists;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-3
 * Time: 22:36:54
 */
public class ComplexAlgorithm extends AbstractAlgorithm{


	protected Chunk[] createChunks(char[] chars, int index) {

		List<Chunk> chunkList = Lists.newArrayList();

		Word[] words0 = findMatchWords(chars, index);

        for (Word aWords0 : words0) {
            int index0 = index + aWords0.getLength();
            if (index0 < chars.length) {
                Word[] words1 = findMatchWords(chars, index0);
                for (Word aWords1 : words1) {
                    int index1 = index0 + aWords1.getLength();
                    if (index1 < chars.length) {
                        Word[] words2 = findMatchWords(chars, index1);
                        for (int k = 0; k < words2.length; k++) {
                            List<Word> wordList = Lists.newArrayList();
                            wordList.add(aWords0);
                            wordList.add(aWords1);
                            Word[] words = new Word[wordList.size()];
                            wordList.toArray(words);
                            wordList.clear();
                            chunkList.add(new Chunk(words));
                        }
                    } else if (index1 == chars.length) {
                        chunkList.add(new Chunk(new Word[]{aWords0,
                                aWords1}));
                    }
                }

            } else if (index0 == chars.length) {
                chunkList.add(new Chunk(new Word[]{aWords0}));
            }
        }
        
		Chunk[] chunks = new Chunk[chunkList.size()];
		chunkList.toArray(chunks);
		chunkList.clear();

		return chunks;
	}
}
