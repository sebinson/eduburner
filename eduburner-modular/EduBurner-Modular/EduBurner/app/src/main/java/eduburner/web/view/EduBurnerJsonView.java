package eduburner.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.view.AbstractView;

import eduburner.json.JsonHelper;

public class EduBurnerJsonView extends AbstractView {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EduBurnerJsonView.class);

	public EduBurnerJsonView() {
		setContentType("application/json;charset=UTF-8");
	}

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LOGGER.debug("begin to render json");
		String jsonValue = JsonHelper.toJson(model);
		response.getWriter().write(jsonValue);
	}

}
