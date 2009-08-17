package eduburner.util;

import java.util.Map;

import com.google.gson.Gson;

public class GsonUtils {
	
	public static String toJsonMap(Map<String, Object> map){
		return new Gson().toJson(map);
	}

}
