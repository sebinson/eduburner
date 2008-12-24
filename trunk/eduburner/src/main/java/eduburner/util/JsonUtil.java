package eduburner.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json helper，可以用类似userToJson, courseToJson的方法
 * @author zhangyf@gmail.com
 *
 */
public class JsonUtil {
	
	public static String toJson(Object obj){
		
		/**
		 * By default, if you mark a field as transient, it will be excluded. If you are not willing to mark the field as transient, you can use the @Expose annotation instead. To use this annotation
		 */
		GsonBuilder gsonBuilder = new GsonBuilder();
		//gson.registerTypeAdapter(MyType.class, new MySerializer());
		Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation().create();
		
		return gson.toJson(obj);
	}
	
}
