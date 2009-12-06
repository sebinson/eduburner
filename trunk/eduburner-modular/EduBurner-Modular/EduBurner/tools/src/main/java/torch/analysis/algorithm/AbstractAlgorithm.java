package torch.analysis.algorithm;

import java.util.ArrayList;
import java.util.List;

import torch.analysis.model.Chunk;
import torch.analysis.model.Dictionary;
import torch.analysis.model.Word;
import torch.analysis.rule.IRule;

import com.google.inject.Inject;
import com.google.inject.name.Named;

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

	public Word next(char[] chars) {

		if (index >= chars.length) {
			index = 0;
			return null;
		}
		char current = chars[index];
		if (isBasicLatin(current)) {
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

	protected Word getBasicLatinWord(char[] chars, int index) {
		StringBuffer basicLatinWord = new StringBuffer();
		while ((index < chars.length) && isBasicLatin(chars[index])) {
			if (Character.isWhitespace(chars[index])) {
				if (basicLatinWord.length() == 0) {
					basicLatinWord.append(chars[index]);
					index++;
					return new Word(basicLatinWord.toString(),
							Word.BASICLATIN_WORD);
				} else {
					return new Word(basicLatinWord.toString(),
							Word.BASICLATIN_WORD);
				}
			}
			basicLatinWord.append(chars[index]);
			index++;
		}

		return new Word(basicLatinWord.toString(), Word.BASICLATIN_WORD);
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
			if (isBasicLatin(chars[i + index]))
				break;
			sb.append(chars[i + index]);
			String temp = sb.toString();
			if (dictionary.isMatched(temp)) {
				matchWords.add(dictionary.getWord(temp));
			}
		}

		if (matchWords.isEmpty()) {
			matchWords.add(new Word(wordValue, Word.UNRECOGNIZED));
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

	private boolean isBasicLatin(char c) {
		return Character.UnicodeBlock.of(c) == Character.UnicodeBlock.BASIC_LATIN;
	}
}
