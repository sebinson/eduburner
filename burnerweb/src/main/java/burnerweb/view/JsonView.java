package burnerweb.view;

import burnerweb.json.GsonUtil;
import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class JsonView extends AbstractView {

	public JsonView() {
		setContentType("application/json;charset=UTF-8");
	}

    @Override
	protected void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, Object> mapToRender = filterModel(model);
		
		ApplicationContext ctx = getApplicationContext();
		GsonUtil jsonHelper = (GsonUtil)ctx.getBean("gsonUtil");

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
