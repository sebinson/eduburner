package eduburner.crawler.batch.tasklet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import eduburner.crawler.batch.ICrawlUriProducer;
import eduburner.crawler.model.CrawlUri;

@Component("fetchTasklet")
public class FetchTasklet implements Tasklet, InitializingBean {
	
	private static final Logger logger = LoggerFactory.getLogger(FetchTasklet.class);
	
	@Autowired
	@Qualifier("crawUriQueue")
	private ICrawlUriProducer queue;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext context)
			throws Exception {
		int count = 0;
		while(true){
			CrawlUri uri = queue.next();
			logger.debug("begin to craw uri");
			count ++;
			if(count > 10){
				break;
			}
		}
		return RepeatStatus.FINISHED;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(queue, "queue should not be null");
	}

}
