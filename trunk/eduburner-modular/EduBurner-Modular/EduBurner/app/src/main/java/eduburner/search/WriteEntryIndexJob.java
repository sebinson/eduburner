package eduburner.search;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import eduburner.entity.Entry;
import eduburner.service.user.IUserManager;

/**
 * 定时写索引
 * @author zhangyf@gmail.com
 *
 */
public class WriteEntryIndexJob extends QuartzJobBean{
	
	private static final Logger logger = LoggerFactory.getLogger(WriteEntryIndexJob.class);
	
	private IIndexService indexService;
	
	private IUserManager userManager;
	
	private AtomicBoolean indexing = new AtomicBoolean(false);
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		
		if(indexing.get()){
			return;
		}
		
		indexing.set(true);
		
		logger.debug("execute cron job");
		
		indexService.purgeIndex();
		
		List<Entry> entries = userManager.getAllEntries();
		
		indexService.indexEntries(entries);
		
		indexing.set(false);
	}

	public void setIndexService(IIndexService indexService) {
		this.indexService = indexService;
	}
	
	public void setUserManager(IUserManager userManager) {
		this.userManager = userManager;
	}
	
}
