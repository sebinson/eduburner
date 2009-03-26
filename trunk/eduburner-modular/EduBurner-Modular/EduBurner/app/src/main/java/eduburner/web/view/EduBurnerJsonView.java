package eduburner.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		//TODO: remove binding result first, should find a way to transform binding result to errors in json format
		Map<String, Object> mapToRender = Maps.filterKeys(model,
				new Predicate<String>() {
					@Override
					public boolean apply(String input) {
						return (!input.contains(BindingResult.MODEL_KEY_PREFIX));
					}
				});
		
		ApplicationContext ctx = getApplicationContext();
		JsonHelper jsonHelper = (JsonHelper)ctx.getBean("jsonHelper");

		String jsonValue = jsonHelper.toJson(mapToRender);
		response.getWriter().write(jsonValue);
	}

}
