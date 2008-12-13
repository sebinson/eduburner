package eduburner.crawler;


public interface IFetchTask {
	
	/**
	 * 具体的抓取动作
	 */
	public FetchResult doFetch();
	
	public long getMinFetchDelay();
}
