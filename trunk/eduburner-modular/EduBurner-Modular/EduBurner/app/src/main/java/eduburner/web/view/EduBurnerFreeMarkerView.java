package eduburner.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import eduburner.util.RequestUtils;
import eduburner.util.SecurityHelper;

/**
 * add base and securityHelper to model, so it can be accessed. e.g.
 * ${securityHelper.principal?default("")}
 * 
 * @author rockmaple
 * 
 */
public class EduBurnerFreeMarkerView extends FreeMarkerView {

	@SuppressWarnings("unchecked")
	@Override
	protected void exposeHelpers(Map model, HttpServletRequest request)
			throws Exception {
		model.put("base", RequestUtils.getResourceBase(request));
		model.put("securityHelper", SecurityHelper.getInstance());
		model.put("req", request);
	}
}
