package eduburner.web.controller.user;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import eduburner.entity.user.UserData;
import eduburner.enumerations.Message;
import eduburner.web.controller.BaseController;

@Controller
public class UserSettingsController extends BaseController {
	
	private static final String SETTINGS_VIEW = "settings";

	@RequestMapping(value = "/account/settings", method=RequestMethod.GET)
	public String show(Model model) {
		UserData ud = getRemoteUserDataObj();
		model.addAttribute("user", ud);
		return SETTINGS_VIEW;
	}
	
	@RequestMapping(value="/account/settings", method=RequestMethod.PUT)
	public String update(@ModelAttribute("user") UserData user, BindingResult br, Model model){
		return null;
	}
	
	@RequestMapping(value="/account/profilepicture", method=RequestMethod.POST)
	public String uploadProfilePicture(HttpServletRequest request, Model model) throws Exception{
		
		logger.debug("entering uploadProfilePicture method...");

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// Filedata is the default name for file in YUI uploader
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest
				.getFile("Filedata");

		String uploadDir = request.getSession().getServletContext()
				.getRealPath("/")
				+ "/static/profiles/";

		logger.debug("upload dir is: " + uploadDir);

		// retrieve the file data
		InputStream stream = file.getInputStream();
		// write the file to the file specified
		OutputStream bos = new FileOutputStream(uploadDir
				+ file.getOriginalFilename());
		int bytesRead;
		byte[] buffer = new byte[8192];
		while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
			bos.write(buffer, 0, bytesRead);
		}
		bos.close();
		// close the stream
		stream.close();
		
		setReturnMsg(model, Message.OK);
		
		return JSON_VIEW;
		
	}
}
