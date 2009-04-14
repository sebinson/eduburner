package eduburner.crawler.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Crawler implements ICrawler{
	
	private static final Logger logger = LoggerFactory.getLogger(Crawler.class);

	@Autowired
	@Qualifier("launcher")
	private JobLauncher launcher;
	@Autowired
	@Qualifier("job")
	private Job job;
	
	private JobParameters jobParameters = new JobParameters();
	
	@Override
	public void launchJob(){
		try {
			launcher.run(job, jobParameters);
		} catch (JobExecutionAlreadyRunningException e) {
			logger.warn("failed to run job", e);
		} catch (JobRestartException e) {
			logger.warn("failed to run job", e);
		} catch (JobInstanceAlreadyCompleteException e) {
			logger.warn("failed to run job", e);
		}
	}
}
