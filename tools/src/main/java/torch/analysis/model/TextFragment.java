package torch.analysis.model;

/**
 * Created by IntelliJ IDEA.
 * User: rockmaple
 * Date: 2009-12-7
 * Time: 22:08:11
 */
public class TextFragment {
	private char[] text;
	private int textOffset; // 片断在文本中的偏移
	private int offset;     // 待分词起点的偏移量

	public TextFragment() {
		text = new char[0];
	}

	public TextFragment(char[] text, int textOffset) {
		init(text, textOffset);
	}

	public void init(char[] text, int textOffset) {
		this.text = text;
		this.textOffset = textOffset;
		offset = 0;
	}

	public char[] getText() {
		return text;
	}

	/** 句子开始处理的偏移位置 */
	public int getOffset() {
		return offset;
	}

	/** 句子开始处理的偏移位置 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	public void addOffset(int inc) {
		offset += inc;
	}

	/** 句子处理完成 */
	public boolean isFinish() {
		return offset >= text.length;
	}

	/** 句子在文本中的偏移位置 */
	public int getTextOffset() {
		return textOffset;
	}

	/** 句子在文本中的偏移位置 */
	public void setTextOffset(int startOffset) {
		this.textOffset = startOffset;
	}
}
