package torch.analysis.model;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-7
 * Time: 22:08:11
 */
public class TextFragment implements CharSequence {
    /**
     * 文本字符数组
     */
    private final char[] value;
    /**
     * 字符开始位置，即charAt(i)返回value[offset+i]字符
     */
    private int offset;
    /**
     * 从offset位置开始的字符数
     */
    private int count;
    /**
     * Cache the hash code for the beef
     */
    private int hash; // Default to 0

    /**
     * @param value  被本对象中直接拥有的文本字符数组
     * @param offset 字符开始位置，即get(i)返回body[offset+i]字符
     * @param count  从offset位置开始的字符数
     */
    public TextFragment(char[] value, int offset, int count) {
        this.value = value;
        set(offset, count);
    }


    public void set(int offset, int count) {
        if (offset < 0) {
            throw new StringIndexOutOfBoundsException(offset);
        }
        if (count < 0) {
            throw new StringIndexOutOfBoundsException(count);
        }
        if (offset > value.length - count) {
            throw new StringIndexOutOfBoundsException(offset + count);
        }
        this.offset = offset;
        this.count = count;
    }

    public char[] getValue() {
        return value;
    }


    public int getCount() {
        return count;
    }

    public int getOffset() {
        return offset;
    }

    // -------------------------------------------------

    /**
     * 获取指定位置的字符。返回之前将被预处理：1)toLowerCase，2)全角转半角等
     */
    public char charAt(int index) {
        if (index >= 0 && index < count) {
            char src = value[offset + index];
            if (src > 65280 && src < 65375) {
                src = (char) (src - 65248);
                value[offset + index] = src;
            }
            if (src >= 'A' && src <= 'Z') {
                src += 32;
                value[offset + index] = src;
            } else if (src == 12288) {
                src = 32;
                value[offset + index] = 32;
            }
            return src;
        }
        return (char) -1;
    }

    public int length() {
        return count;
    }

    public CharSequence subSequence(int start, int end) {
        return new String(value, offset + start, end - start);
    }

    public String toString() {
        return new String(value, offset, count);
    }

    public int hashCode() {
        int h = hash;
        if (h == 0) {
            int off = offset;
            char val[] = value;
            int len = count;

            for (int i = 0; i < len; i++) {
                h = 31 * h + val[off++];
            }
            hash = h;
        }
		return h;
	}
}
