package eduburner.json;

import org.springframework.validation.BeanPropertyBindingResult;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * json helper，可以用类似userToJson, courseToJson的方法
 * 
 * @author zhangyf@gmail.com
 * 
 */
public class JsonHelper {

	public static String toJson(Object obj) {

		/**
		 * By default, if you mark a field as transient, it will be excluded. If
		 * you are not willing to mark the field as transient, you can use the @Expose
		 * annotation instead. To use this annotation
		 */
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation()
				.registerTypeAdapter(BeanPropertyBindingResult.class, new BindingResultSerializer())
				.create();

		return gson.toJson(obj);
	}

	public static <K, V> String toJsonMap(K k1, V v1) {
		return new Gson().toJson(ImmutableMap.of(k1, v1));
	}

	public static <K, V> String toJsonMap(K k1, V v1, K k2, V v2) {
		return new Gson().toJson(ImmutableMap.of(k1, v1, k2, v2));
	}

	public static <K, V> String toJsonMap(K k1, V v1, K k2, V v2, K k3, V v3) {
		return new Gson().toJson(ImmutableMap.of(k1, v1, k2, v2, k3, v3));
	}

	public static <E> String toJsonArray(E e1) {
		return new Gson().toJson(ImmutableList.of(e1));
	}

	public static <E> String toJsonArray(E e1, E e2) {
		return new Gson().toJson(ImmutableList.of(e1, e2));
	}

	public static <E> String toJsonArray(E e1, E e2, E e3) {
		return new Gson().toJson(ImmutableList.of(e1, e2, e3));
	}
}
