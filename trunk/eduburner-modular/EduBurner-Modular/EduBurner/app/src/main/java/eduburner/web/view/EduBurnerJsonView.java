package eduburner.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;

import eduburner.json.JsonHelper;

public class EduBurnerJsonView extends AbstractView {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(EduBurnerJsonView.class);

	public EduBurnerJsonView() {
		setContentType("application/json;charset=UTF-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOGGER.debug("begin to render json");

		Map<String, Object> mapToRender = filterModel(model);
		
		ApplicationContext ctx = getApplicationContext();
		JsonHelper jsonHelper = (JsonHelper)ctx.getBean("jsonHelper");

		String jsonValue = jsonHelper.toJson(mapToRender);
		
		IOUtils.write(jsonValue, response.getOutputStream(), "UTF-8");
	}
	
	protected Map<String, Object> filterModel(Map<String, Object> model) {
		return Maps.filterValues(model, new Predicate<Object>() {
			@Override
			public boolean apply(Object input) {
				return !(input instanceof BindingResult);
			}
		});
	}

}
