package eduburner.crawler.partition;

import java.util.Map;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

public class FetchPartitioner implements Partitioner {
	
	

	@Override
	public Map<String, ExecutionContext> partition(int grid) {
		// TODO Auto-generated method stub
		return null;
	}

}
