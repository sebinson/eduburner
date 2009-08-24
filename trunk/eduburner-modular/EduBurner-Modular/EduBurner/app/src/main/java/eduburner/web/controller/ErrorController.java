package eduburner.web.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class ErrorController {
	private static final String ERROR_VIEW = "error";
	
	@ExceptionHandler(IOException.class)
	public String handleIOException(){
		return ERROR_VIEW;
	}
}
