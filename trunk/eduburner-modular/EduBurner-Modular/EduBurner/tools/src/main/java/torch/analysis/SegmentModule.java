/*
 * Created by IntelliJ IDEA.
 * User: zhangyf
 * Date: 2009-12-3
 * Time: 21:53:38
 */
package torch.analysis;

import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import torch.analysis.rule.IRule;
import torch.analysis.rule.LargestAvgWordLenRule;
import torch.analysis.rule.LargestSumMorphemicFreedomDegreeRule;
import torch.analysis.rule.MaxMatchRule;
import torch.analysis.rule.SmallestVarianceRule;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class SegmentModule extends AbstractModule {

	private static final Logger logger = LoggerFactory.getLogger(SegmentModule.class);
	
	protected void configure() {
		try {
			Properties props = loadProperties();
			Names.bindProperties(binder(), props);
		} catch (Exception e) {
			logger.error("failed to load properties file", e);
		}
		
		bind(IRule.class).annotatedWith(Names.named("SmallestVarianceRule"))
				.to(SmallestVarianceRule.class);
		bind(IRule.class).annotatedWith(
				Names.named("LargestSumMorphemicFreedomDegreeRule")).to(
				LargestSumMorphemicFreedomDegreeRule.class);
		bind(IRule.class).annotatedWith(Names.named("LargestAvgWordLenRule"))
				.to(LargestAvgWordLenRule.class);
		bind(IRule.class).annotatedWith(Names.named("MaxMatchRule")).to(
				MaxMatchRule.class);

	}

	private static Properties loadProperties() throws Exception {
		Properties properties = new Properties();
		ClassLoader loader = SegmentModule.class.getClassLoader();
		URL url = loader.getResource("wordseg.properties");
		properties.load(url.openStream());
		return properties;
	}
}
