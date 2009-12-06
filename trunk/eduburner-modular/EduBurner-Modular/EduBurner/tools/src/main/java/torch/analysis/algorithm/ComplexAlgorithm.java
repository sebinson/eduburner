package torch.analysis.algorithm;

import java.util.ArrayList;
import java.util.List;

import torch.analysis.model.Chunk;
import torch.analysis.model.Word;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-3
 * Time: 22:36:54
 */
public class ComplexAlgorithm extends AbstractAlgorithm{


	protected Chunk[] createChunks(char[] chars, int index) {

		List chunkList = new ArrayList(5);

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
							//chunkList.add(new Chunk(new IWord[] { words0[i],
							//		words1[j], words2[k] }));
							List wordList = new ArrayList(3);
							wordList.add( words0[i]);
							wordList.add(words1[j]);
							if(words2[k].getType()!=Word.Type.UNRECOGNIZED){
								wordList.add(words2[k]);
							}
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
