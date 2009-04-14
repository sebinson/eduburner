package eduburner.crawler.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component("logProcessor")
public class LogProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(String s) throws Exception {
		//Thread.sleep(3000);
		System.out.println("process: " + s);
		return s;
	}

}
