package torch.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.Queue;

import torch.analysis.algorithm.IAlgorithm;
import torch.analysis.model.Chunk;
import torch.analysis.model.TextFragment;
import torch.analysis.model.Word;
import torch.util.LanguageUtil;

import com.google.common.collect.Lists;

/**
 * Created by IntelliJ IDEA. User: rockmaple Date: 2009-12-8 Time: 23:21:25
 */
public class Segmentation {

	private PushbackReader reader;
	private IAlgorithm algorithm;

	private StringBuilder fragBuffer = new StringBuilder(256);
	private TextFragment textFragment;
	private Queue<Word> wordBuffer; // word 缓存, 因为有 chunk 分析三个以上.
	private int index = 0;

	public void reset(Reader input) {
		this.reader = new PushbackReader(new BufferedReader(input), 20);
		textFragment = null;
		wordBuffer = Lists.newLinkedList();
		fragBuffer.setLength(0);
		index = -1;
	}

	private int readNext() throws IOException {
		int d = reader.read();
		if (d > -1) {
			index++;
			d = Character.toLowerCase(d);
		}
		return d;
	}

	private void pushBack(int data) throws IOException {
		index--;
		reader.unread(data);
	}

	public Word next() throws IOException {
		Word word = wordBuffer.poll();
		if (word == null) {
			fragBuffer.setLength(0);
			int data = -1;
			boolean read = true;
			while (read && (data = readNext()) != -1) {
				read = false; // 默认一次可以读出同一类字符,就可以分词内容
				int type = Character.getType(data);
				String wordType = Word.TYPE_WORD;
				switch (type) {
				case Character.UPPERCASE_LETTER:
				case Character.LOWERCASE_LETTER:
				case Character.TITLECASE_LETTER:
				case Character.MODIFIER_LETTER:
					data = toAscii(data);
					NationLetter nl = getNation(data);
					if (nl == NationLetter.UNKNOW) {
						read = true;
						break;
					}
					wordType = Word.TYPE_LETTER;
					fragBuffer.appendCodePoint(data);
					switch (nl) {
					case EN:
						// 字母后面的数字,如: VH049PA
						ReadCharByAsciiOrDigit rcad = new ReadCharByAsciiOrDigit();
						readChars(fragBuffer, rcad);
						if (rcad.hasDigit()) {
							wordType = Word.TYPE_LETTER_OR_DIGIT;
						}
						break;
					case RA:
						readChars(fragBuffer, new ReadCharByRussia());
						break;
					case GE:
						readChars(fragBuffer, new ReadCharByGreece());
						break;
					}
					wordBuffer.add(createWord(fragBuffer, wordType));
					fragBuffer.setLength(0);
				case Character.OTHER_LETTER:
					fragBuffer.appendCodePoint(data);
					readChars(fragBuffer, new ReadCharByType(Character.OTHER_LETTER));
					textFragment = createSentence(fragBuffer);
					fragBuffer.setLength(0);
					break;
				case Character.DECIMAL_DIGIT_NUMBER:
					fragBuffer.appendCodePoint(toAscii(data));
					readChars(fragBuffer, new ReadCharDigit());	//读后面的数字, AsciiLetterOr
					wordType = Word.TYPE_DIGIT;
					int d = readNext();
					if(d > -1) {
						pushBack(d);
						if(readChars(fragBuffer, new ReadCharByAsciiOrDigit()) > 0) {	//如果有字母或数字都会连在一起.
							wordType = Word.TYPE_DIGIT_OR_LETTER;
						}
					}
					wordBuffer.add(createWord(fragBuffer, wordType));
					fragBuffer.setLength(0);	//缓存的字符清除
					break;
				case Character.LETTER_NUMBER:
					fragBuffer.appendCodePoint(data);
					readChars(fragBuffer, new ReadCharByType(Character.LETTER_NUMBER));

					int startIdx = calStartIndex(fragBuffer);
					for(int i=0; i<fragBuffer.length(); i++) {
						wordBuffer.add(new Word(new char[] {fragBuffer.charAt(i)}, Word.TYPE_LETTER_NUMBER));
					}
					fragBuffer.setLength(0);	//缓存的字符清除
					break;
				case Character.OTHER_NUMBER:
					fragBuffer.appendCodePoint(data);
					readChars(fragBuffer, new ReadCharByType(Character.OTHER_NUMBER));
					wordBuffer.add(createWord(fragBuffer, Word.TYPE_OTHER_NUMBER));
					fragBuffer.setLength(0);
					break;
				default :
					//其它认为无效字符
					read = true;
				}//switch
			}
			// 中文分词
			fillWordBuffer();
			word = wordBuffer.poll();
		}
		return word;
	}

	private void fillWordBuffer() {
		if (textFragment != null) {
			do {
				Chunk chunk = algorithm.doSegment(textFragment);
				for (Word word : chunk.getWords()) {
					wordBuffer.add(word);
				}
			} while (!textFragment.isFinish());
			textFragment = null;
		}
	}

	private int readChars(StringBuilder fragBuffer, ReadChar readChar)
			throws IOException {
		int num = 0;
		int data = -1;
		while ((data = readNext()) != -1) {
			int d = readChar.transform(data);
			if (readChar.isRead(d)) {
				fragBuffer.appendCodePoint(d);
				num++;
			} else { // 不是数字回压,要下一步操作
				pushBack(data);
				break;
			}
		}
		return num;
	}

	private Word createWord(StringBuilder bufSentence, String type) {
		return new Word(toChars(bufSentence), type);
	}

	private TextFragment createSentence(StringBuilder bufSentence) {
		return new TextFragment(toChars(bufSentence),
				calStartIndex(bufSentence));
	}

	/** 取得 bufSentence 的第一个字符在整个文本中的位置 */
	private int calStartIndex(StringBuilder bufSentence) {
		return index - bufSentence.length() + 1;
	}

	/** 从 StringBuilder 里复制出 char[] */
	private static char[] toChars(StringBuilder bufSentence) {
		char[] chs = new char[bufSentence.length()];
		bufSentence.getChars(0, bufSentence.length(), chs, 0);
		return chs;
	}

	/**
	 * 双角转单角
	 */
	private static int toAscii(int codePoint) {
		if ((codePoint >= 65296 && codePoint <= 65305) // ０-９
				|| (codePoint >= 65313 && codePoint <= 65338) // Ａ-Ｚ
				|| (codePoint >= 65345 && codePoint <= 65370) // ａ-ｚ
		) {
			codePoint -= 65248;
		}
		return codePoint;
	}

	private static boolean isDigit(int type) {
		return type == Character.DECIMAL_DIGIT_NUMBER;
	}

	private static abstract class ReadChar {
		/**
		 * 这个字符是否读取, 不读取也不会读下一个字符.
		 */
		abstract boolean isRead(int codePoint);

		int transform(int codePoint) {
			return codePoint;
		}
	}

	/** 读取数字 */
	private static class ReadCharDigit extends ReadChar {

		boolean isRead(int codePoint) {
			int type = Character.getType(codePoint);
			return isDigit(type);
		}

		int transform(int codePoint) {
			return toAscii(codePoint);
		}

	}

	/** 读取字母或数字 */
	private static class ReadCharByAsciiOrDigit extends ReadCharDigit {

		private boolean hasDigit = false;

		boolean isRead(int codePoint) {
			boolean isRead = super.isRead(codePoint);
			hasDigit |= isRead;
			return LanguageUtil.isAsciiLetter(codePoint) || isRead;
		}

		boolean hasDigit() {
			return hasDigit;
		}
	}

	/** 读取字母 */
	@SuppressWarnings("unused")
	private static class ReadCharByAscii extends ReadCharDigit {
		boolean isRead(int codePoint) {
			return LanguageUtil.isAsciiLetter(codePoint);
		}
	}

	/** 读取俄语 */
	private static class ReadCharByRussia extends ReadCharDigit {

		boolean isRead(int codePoint) {
			return LanguageUtil.isRussiaLetter(codePoint);
		}

	}

	/** 读取希腊 */
	private static class ReadCharByGreece extends ReadCharDigit {

		boolean isRead(int codePoint) {
			return LanguageUtil.isGreeceLetter(codePoint);
		}

	}

	/** 读取指定类型的字符 */
	private static class ReadCharByType extends ReadChar {
		int charType;

		public ReadCharByType(int charType) {
			this.charType = charType;
		}

		boolean isRead(int codePoint) {
			int type = Character.getType(codePoint);
			return type == charType;
		}

	}

	private static enum NationLetter {
		EN, RA, GE, UNKNOW
	};

	private NationLetter getNation(int codePoint) {
		if (LanguageUtil.isAsciiLetter(codePoint)) {
			return NationLetter.EN;
		}
		if (LanguageUtil.isRussiaLetter(codePoint)) {
			return NationLetter.RA;
		}
		if (LanguageUtil.isGreeceLetter(codePoint)) {
			return NationLetter.GE;
		}
		return NationLetter.UNKNOW;
	}
}
