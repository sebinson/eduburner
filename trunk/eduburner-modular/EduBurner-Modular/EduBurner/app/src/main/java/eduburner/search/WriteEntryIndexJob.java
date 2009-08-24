package eduburner.search;

import java.util.List;

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
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		logger.debug("execute cron job");
		
		List<Entry> entries = userManager.getAllEntries();
		
		for(Entry entry : entries){
			indexService.addEntryDocument(entry);
		}
		
	}

	public void setIndexService(IIndexService indexService) {
		this.indexService = indexService;
	}
	
	public void setUserManager(IUserManager userManager) {
		this.userManager = userManager;
	}
	
}
