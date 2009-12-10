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

		for (int i = 0; i < words0.length; i++) {
			int index0 = index + words0[i].getLength();
			if (index0 < chars.length) {
				Word[] words1 = findMatchWords(chars, index0);
				for (int j = 0; j < words1.length; j++) {
					int index1 = index0 + words1[j].getLength();
					if (index1 < chars.length) {
						Word[] words2 = findMatchWords(chars, index1);
						for (int k = 0; k < words2.length; k++) {
							List<Word> wordList = Lists.newArrayList();
							wordList.add( words0[i]);
							wordList.add(words1[j]);
							Word[] words = new Word[wordList.size()];
							wordList.toArray(words);
							wordList.clear();
							chunkList.add(new Chunk(words));
						}
					} else if (index1 == chars.length) {
						chunkList.add(new Chunk(new Word[] { words0[i],
								words1[j] }));
					}
				}
			} else if (index0 == chars.length) {
				chunkList.add(new Chunk(new Word[] { words0[i] }));
			}
		}

		Chunk[] chunks = new Chunk[chunkList.size()];
		chunkList.toArray(chunks);
		chunkList.clear();

		return chunks;
	}
}
