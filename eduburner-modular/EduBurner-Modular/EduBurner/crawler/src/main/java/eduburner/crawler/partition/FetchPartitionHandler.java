package eduburner.crawler.partition;

import java.util.Collection;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.PartitionHandler;
import org.springframework.batch.core.partition.StepExecutionSplitter;

public class FetchPartitionHandler implements PartitionHandler {

	@Override
	public Collection<StepExecution> handle(StepExecutionSplitter splitter,
			StepExecution execution) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
