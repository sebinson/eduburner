package eduburner.json;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * json helper，可以用类似userToJson, courseToJson的方法
 * 
 * @author zhangyf@gmail.com
 * 
 */

@Component("jsonHelper")
public class JsonHelper {
	
	@Autowired
	@Qualifier("gson")
	private Gson gson;

	public String toJson(Object obj) {
		return gson.toJson(obj);
	}

	public <K, V> String toJsonMap(K k1, V v1) {
		return gson.toJson(ImmutableMap.of(k1, v1));
	}

	public <K, V> String toJsonMap(K k1, V v1, K k2, V v2) {
		return gson.toJson(ImmutableMap.of(k1, v1, k2, v2));
	}

	public <K, V> String toJsonMap(K k1, V v1, K k2, V v2, K k3, V v3) {
		return gson.toJson(ImmutableMap.of(k1, v1, k2, v2, k3, v3));
	}

	public <E> String toJsonArray(E e1) {
		return gson.toJson(ImmutableList.of(e1));
	}

	public <E> String toJsonArray(E e1, E e2) {
		return gson.toJson(ImmutableList.of(e1, e2));
	}

	public <E> String toJsonArray(E e1, E e2, E e3) {
		return gson.toJson(ImmutableList.of(e1, e2, e3));
	}
}
