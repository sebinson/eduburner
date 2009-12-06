package torch.analysis.model;

public class Word {

    public static int UNRECOGNIZED = 0;
	public static int BASICLATIN_WORD = 1;
	public static int CJK_WORD = 2;

    private String value = "";

    private int frequency = 0;
    private int length = 0;
    private int type;

    public Word(String value, int type) {
		this.value = value;
		this.type = type;
		this.length = value.length();
	}

	public Word(String value, int frequency, int type) {
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
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
        result = 31 * result + type;
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
