package torch.analysis.algorithm;

import torch.analysis.model.Chunk;
import torch.analysis.model.Word;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-3
 * Time: 22:38:43
 */
public class SimpleAlgorithm extends AbstractAlgorithm{

	protected Chunk[] createChunks(char[] chars, int index) {

		Word[] words = findMatchWords(chars, index);
		Chunk[] chunks = new Chunk[words.length];
		for (int i = 0; i < words.length; i++) {
			chunks[i] = new Chunk(new Word[] { words[i] });
		}
		return chunks;
	}
}
