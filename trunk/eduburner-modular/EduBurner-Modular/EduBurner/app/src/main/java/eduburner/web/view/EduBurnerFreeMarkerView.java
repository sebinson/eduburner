package eduburner.web.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.freemarker.FreeMarkerView;


/**
 * add base and securityHelper to model, so it can be accessed. e.g.
 * ${securityHelper.principal?default("")}
 * 
 * @author rockmaple
 * 
 */
public class EduBurnerFreeMarkerView extends FreeMarkerView {

	@Override
	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request)
			throws Exception {
		ViewHelper viewHepler = (ViewHelper)getApplicationContext().getBean("viewHelper");
		model.put("principal", viewHepler.getPrincipal());
		model.put("user", viewHepler.getUser());
		model.put("req", request);
	}
}
