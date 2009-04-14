package eduburner.crawler.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component("logWriter")
public class LogWriter implements ItemWriter<String> {

	@Override
	public void write(List<? extends String> sList) throws Exception {
		for(String s : sList){
			System.out.println("write: " + s);
		}
	}

}
