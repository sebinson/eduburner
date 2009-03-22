package eduburner.json;

import java.lang.reflect.Type;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BindingResultSerializer implements JsonSerializer<BindingResult>{
	
	private static final Logger logger = LoggerFactory.getLogger(BindingResultSerializer.class);

	@Override
	public JsonElement serialize(BindingResult src, Type typeOfSrc,
			JsonSerializationContext context) {
		
		logger.debug("begin to serialize binding result...");
		
		JsonObject jsonObj = new JsonObject();
		List<FieldError> fieldErrors = src.getFieldErrors();
		
		for(FieldError error : fieldErrors){
			String name = error.getField();
			String message = error.getDefaultMessage();
			jsonObj.addProperty(name, message);
		}
		
		List<ObjectError> globalErrors = src.getGlobalErrors();
		
		for(ObjectError error: globalErrors){
			String name = error.getObjectName();
			String message = error.getDefaultMessage();
			jsonObj.addProperty(name, message);
		}
		
		return jsonObj;
	}

}
