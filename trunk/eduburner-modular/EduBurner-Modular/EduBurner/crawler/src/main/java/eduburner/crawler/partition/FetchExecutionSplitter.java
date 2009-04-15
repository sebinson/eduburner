package eduburner.crawler.partition;

import java.util.Set;

import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.StepExecutionSplitter;

public class FetchExecutionSplitter implements StepExecutionSplitter {
	
	private String stepName;

	@Override
	public String getStepName() {
		// TODO Auto-generated method stub
		return stepName;
	}

	@Override
	public Set<StepExecution> split(StepExecution stepExecution, int gridSize)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		return null;
	}

}
