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
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-8
 * Time: 23:21:25
 */
public class Segmentation {

    private PushbackReader reader;
    private IAlgorithm algorithm;

    private StringBuilder textBuffer = new StringBuilder(256);  //存储一个一个的文本片断
    private TextFragment textFragment;    //当前的文本片断
    private Queue<Word> wordBuffer; // word 缓存, 因为有 chunk 分析三个以上.
    private int index = 0;          //当前字符指针

    public Segmentation(Reader input){
         init(input);
    }

    public void init(Reader input) {
        this.reader = new PushbackReader(new BufferedReader(input), 20);
        textFragment = null;
        wordBuffer = Lists.newLinkedList();
        textBuffer.setLength(0);
        index = -1;
    }

    //TODO: return Iterable<Word> instead of Word
    public Word next() throws IOException {
        Word word = wordBuffer.poll();
        if (word == null) {
            textBuffer.setLength(0);
            int data = -1;
            boolean read = true;
            while (read && (data = readNext()) != -1) {
                read = false; // 默认一次可以读出同一类字符,就可以分词内容
                int type = Character.getType(data);
                switch (type) {
                    //字母或字母后加数字，无需分词，直接创建Word
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
                        handleNationalLetter(data, nl);
                        break;
                    case Character.OTHER_LETTER:
                        handlOtherLetter(data);
                        break;
                    case Character.DECIMAL_DIGIT_NUMBER:
                        handleDecimalDigitNumber(data);
                        break;
                    case Character.LETTER_NUMBER:
                        hanleLetterNumber(data);
                        break;
                    case Character.OTHER_NUMBER:
                        handleOtherNumber(data);
                        break;
                    default:
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

    private void handleNationalLetter(int data, NationLetter nl) throws IOException {
        String wordType = Word.TYPE_LETTER;
        textBuffer.appendCodePoint(data);
        switch (nl) {
            case EN:
                // 字母后面的数字,如: VH049PA
                ReadCharByAsciiOrDigit rcad = new ReadCharByAsciiOrDigit();
                readChars(textBuffer, rcad);
                if (rcad.hasDigit()) {
                    wordType = Word.TYPE_LETTER_OR_DIGIT;
                }
                break;
            case RA:
                readChars(textBuffer, new ReadCharByRussia());
                break;
            case GE:
                readChars(textBuffer, new ReadCharByGreece());
                break;
        }
        wordBuffer.add(createWord(textBuffer, wordType));
        textBuffer.setLength(0);
    }

    private void handleOtherNumber(int data) throws IOException {
        //①⑩㈠㈩⒈⒑⒒⒛⑴⑽⑾⒇ 连着用
        textBuffer.appendCodePoint(data);
        readChars(textBuffer, new ReadCharByType(Character.OTHER_NUMBER));
        wordBuffer.add(createWord(textBuffer, Word.TYPE_OTHER_NUMBER));
        textBuffer.setLength(0);
    }

    private void hanleLetterNumber(int data) throws IOException {
        // ⅠⅡⅢ 单分
        textBuffer.appendCodePoint(data);
        readChars(textBuffer, new ReadCharByType(Character.LETTER_NUMBER));
        for (int i = 0; i < textBuffer.length(); i++) {
            wordBuffer.add(new Word(new char[]{textBuffer.charAt(i)}, Word.TYPE_LETTER_NUMBER));
        }
        textBuffer.setLength(0);    //缓存的字符清除
    }

    private void handleDecimalDigitNumber(int data) throws IOException {
        String wordType;
        textBuffer.appendCodePoint(toAscii(data));
        readChars(textBuffer, new ReadCharDigit());    //读后面的数字, AsciiLetterOr
        wordType = Word.TYPE_DIGIT;
        int d = readNext();
        if (d > -1) {
            /*if(seg.isUnit(d)) {	//单位,如时间
							bufWord.add(createWord(bufSentence, startIdx(bufSentence)-1, Word.TYPE_DIGIT));	//先把数字添加(独立)

							bufSentence.setLength(0);

							bufSentence.appendCodePoint(d);
							wordType = Word.TYPE_WORD;	//单位是 word
						} else {	//后面可能是字母和数字
							pushBack(d);
							if(readChars(bufSentence, new ReadCharByAsciiOrDigit()) > 0) {	//如果有字母或数字都会连在一起.
								wordType = Word.TYPE_DIGIT_OR_LETTER;
							}
						}*/
            pushBack(d);
            if (readChars(textBuffer, new ReadCharByAsciiOrDigit()) > 0) {    //如果有字母或数字都会连在一起.
                wordType = Word.TYPE_DIGIT_OR_LETTER;
            }
        }
        wordBuffer.add(createWord(textBuffer, wordType));
        textBuffer.setLength(0);    //缓存的字符清除
    }

    private void handlOtherLetter(int data) throws IOException {
         /*
          * 1. 0x3041-0x30f6 -> ぁ-ヶ	//日文(平|片)假名
		  * 2. 0x3105-0x3129 -> ㄅ-ㄩ	//注意符号
		  */
        textBuffer.appendCodePoint(data);
        readChars(textBuffer, new ReadCharByType(Character.OTHER_LETTER));
        textFragment = creageTextFragment(textBuffer);
        textBuffer.setLength(0);
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

    private TextFragment creageTextFragment(StringBuilder bufSentence) {
        return new TextFragment(toChars(bufSentence),
                startIndex(bufSentence));
    }

    /**
     * 取得 bufSentence 的第一个字符在整个文本中的位置
     */
    private int startIndex(StringBuilder bufSentence) {
        return index - bufSentence.length() + 1;
    }

    /**
     * 从 StringBuilder 里复制出 char[]
     */
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

    /**
     * 读取数字
     */
    private static class ReadCharDigit extends ReadChar {

        boolean isRead(int codePoint) {
            int type = Character.getType(codePoint);
            return isDigit(type);
        }

        int transform(int codePoint) {
            return toAscii(codePoint);
        }

    }

    /**
     * 读取字母或数字
     */
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

    /**
     * 读取字母
     */
    @SuppressWarnings("unused")
    private static class ReadCharByAscii extends ReadCharDigit {
        boolean isRead(int codePoint) {
            return LanguageUtil.isAsciiLetter(codePoint);
        }
    }

    /**
     * 读取俄语
     */
    private static class ReadCharByRussia extends ReadCharDigit {
        boolean isRead(int codePoint) {
            return LanguageUtil.isRussiaLetter(codePoint);
        }
    }

    /**
     * 读取希腊
     */
    private static class ReadCharByGreece extends ReadCharDigit {
        boolean isRead(int codePoint) {
            return LanguageUtil.isGreeceLetter(codePoint);
        }
    }

    /**
     * 读取指定类型的字符
     */
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
    }

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
