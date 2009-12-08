package torch.analysis.model;

import torch.util.StringUtil;

public class Word {
	
    private String value = StringUtil.EMPTY_STRING;

    private int frequency = 0;
    private int length = 0;

    private char[] fragment;  //当前句子
    private int position = 0; //在整个文本里的偏移量
    private int offset = 0;   //在当前句子里的偏移量

    private Type type;
    
    public enum Type{
		UNRECOGNIZED, BASICLATIN_WORD, CJK_WORD  
	}

    public Word(String value, Type type) {
		this.value = value;
		this.type = type;
		this.length = value.length();
	}

	public Word(String value, Type type, int frequency) {
		this(value, type);
		this.frequency = frequency;
	}

	public int getLength() {
		return length;
	}

	public String getValue() {
		return value;
	}

	public int getFrequency() {
		return frequency;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (frequency != word.frequency) return false;
        if (length != word.length) return false;
        if (type != word.type) return false;
        if (value != null ? !value.equals(word.value) : word.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + frequency;
        result = 31 * result + length;
        return result;
    }

    @Override
    public String toString() {
        return "Word{" +
                "value='" + value + '\'' +
                ", frequency=" + frequency +
                ", length=" + length +
                ", type=" + type +
                '}';
    }
}
