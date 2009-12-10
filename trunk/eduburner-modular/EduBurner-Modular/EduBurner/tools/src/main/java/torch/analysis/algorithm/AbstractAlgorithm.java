package torch.analysis.algorithm;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import torch.analysis.model.Chunk;
import torch.analysis.model.Dictionary;
import torch.analysis.model.TextFragment;
import torch.analysis.model.Word;
import torch.analysis.rule.IRule;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Created by IntelliJ IDEA. User: rockmaple Date: 2009-12-3 Time: 22:09:19
 */
public abstract class AbstractAlgorithm implements IAlgorithm {
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractAlgorithm.class);

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

	public Chunk doSegment(TextFragment textFragment) {
		Chunk[] chunks = createChunks(textFragment.getText(), textFragment
				.getOffset());
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
		textFragment.addOffset(chunk.getLength());
		return chunk;
	}

	protected abstract Chunk[] createChunks(char[] chars, int offset);

	// 从词典里找出以index开始的所有匹配的词。
	protected Word[] findMatchWords(final char[] chars, final int index) {
		// logger.debug("finding matching words for chars: " + new String(chars)
		// + " index: " + index);
		return dictionary.findMatchWords(chars, index);

	}
}
