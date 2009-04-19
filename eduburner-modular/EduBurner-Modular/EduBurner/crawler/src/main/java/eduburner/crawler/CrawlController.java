package eduburner.crawler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

import eduburner.crawler.processor.IProcessor;

/**
 * crawler module for eduburner. 
 * 
 * 包eduburner.crawler下的代码，实现原理和命名方式参考 heritrix <a href="http://crawler.archive.org/">http://crawler.archive.org/</a>
 * 
 * 部分代码也来自于 heritrix
 * 
 * @author zhangyf@gmail.com
 *
 */
@Component("crawlController")
public class CrawlController implements ICrawlController {

	public static final int DEFAULT_MAX_TOE_THREAD_SIZE = 10;

	private ExecutorService toeThreadPool;
	private int maxToeThreadSize;
	
	private List<IProcessor> processors = Lists.newArrayList();
	
	@Autowired
	@Qualifier("workQueueFrontier")
	private ICrawlFrontier crawlFrontier;

	public CrawlController() {
		maxToeThreadSize = DEFAULT_MAX_TOE_THREAD_SIZE;
		toeThreadPool = Executors
				.newFixedThreadPool(DEFAULT_MAX_TOE_THREAD_SIZE);
		init();
	}

	private void init() {
		for (int i = 0; i < maxToeThreadSize; i++) {
			toeThreadPool.execute(new ToeThread(this));
		}
	}
	
	public List<IProcessor> getProcessors(){
		return processors;
	}

	@Override
	public void acquireContinuePermission() {
		// TODO Auto-generated method stub

	}

	@Override
	public ICrawlFrontier getFrontier() {
		return crawlFrontier;
	}

	@Override
	public void initTasks() {
		// TODO Auto-generated method stub

	}

	@Override
	public void releaseContinuePermission() {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestCrawlCheckpoint() throws IllegalStateException {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestCrawlPause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestCrawlResume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestCrawlStart() {
		// TODO Auto-generated method stub

	}

	@Override
	public void requestCrawlStop() {
		// TODO Auto-generated method stub

	}

}
