package torch.analysis.model;

public class Word {
	
	public static final String TYPE_WORD = "word";
	public static final String TYPE_LETTER = "letter";
	/** 字母开头的"字母或数字" */
	public static final String TYPE_LETTER_OR_DIGIT = "letter_or_digit";
	public static final String TYPE_DIGIT = "digit";
	/** 数字开头的"字母或数字" */
	public static final String TYPE_DIGIT_OR_LETTER = "digit_or_letter";
	public static final String TYPE_LETTER_NUMBER = "letter_number";
	public static final String TYPE_OTHER_NUMBER = "other_number";
	
    private char[] textData;          //整个文本片断
    private int offset;               //偏移量
    private int length = 0;           //长度
    private int frequency = 0;

    private String type = TYPE_WORD;

    /**
	 * @param startOffset word 在整个文本中的偏移位置
	 */
	public Word(char[] textData) {
		super();
		this.textData = textData;
		this.offset = 0;
		this.length = textData.length;
	}
	
	/**
	 * @param startOffset word 在整个文本中的偏移位置
	 */
	public Word(char[] textData, String wordType) {
		this(textData);
		this.type = wordType;
	}
	
	/**
	 * sen[offset] 开始的 len 个字符才是此 word
	 * @param senStartOffset sen 在整个文本中的偏移位置
	 * @param offset 词在 sen 的偏移位置
	 * @param len 词长
	 */
	public Word(char[] textData, int offset, int len) {
		super();
		this.textData = textData;
		this.offset = offset;
		this.length = len;
	}

	public String getString() {
		return new String(getTextData(), getOffset(), getLength());
	}
	
	
	public char[] getTextData() {
		return textData;
	}

	public void setTextData(char[] textData) {
		this.textData = textData;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getFrequency() {
		return frequency;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String toString() {
		return getString();
	}
}
