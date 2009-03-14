package eduburner.util;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtils {
	private static final Logger logger = LoggerFactory
			.getLogger(FreeMarkerUtils.class);

	@SuppressWarnings("unchecked")
	public static String renderContent(String freemarkerText, Map contextMap) {
		String returnString = null;
		try {
			StringTemplateLoader stringLoader = new StringTemplateLoader();
			stringLoader.putTemplate("textTemplate", freemarkerText);
			Configuration cfg = new Configuration();
			cfg.setTemplateLoader(stringLoader);
			Template t = cfg.getTemplate("textTemplate");
			Writer out = new StringWriter();
			t.process(contextMap, out);
			returnString = out.toString();
			out.close();
		} catch (IOException ioe) {
			logger
					.error("IOException occors when renderint freemarker string content");
			ioe.printStackTrace();
		} catch (TemplateException te) {
			logger
					.error("TemplateException occors when rendering freemarker string content");
			te.printStackTrace();
		}
		return returnString;
	}

}
