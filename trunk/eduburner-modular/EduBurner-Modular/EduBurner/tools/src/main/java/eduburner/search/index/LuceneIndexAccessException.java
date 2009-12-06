package eduburner.search.index;

public class LuceneIndexAccessException extends RuntimeException {

	private static final long serialVersionUID = -4539980507607037318L;

	public LuceneIndexAccessException(String msg) {
		super(msg);
	}

	public LuceneIndexAccessException(String msg, Exception ex) {
		super(msg, ex);
	}

}
