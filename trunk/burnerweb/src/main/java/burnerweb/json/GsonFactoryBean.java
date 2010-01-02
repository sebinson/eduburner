package burnerweb.json;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component("gson")
public class GsonFactoryBean implements FactoryBean<Gson>, InitializingBean {

	private GsonBuilder gsonBuilder;
	private Gson gson;

	@Override
	public Gson getObject() throws Exception {
		return gson;
	}

	@Override
	public Class<? extends Gson> getObjectType() {
		return Gson.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (this.gson == null) {
			if (this.gsonBuilder == null) {
				gsonBuilder = new GsonBuilder();
			}
			this.gson = gsonBuilder.excludeFieldsWithoutExposeAnnotation()
					.registerTypeAdapter(BeanPropertyBindingResult.class,
							new BindingResultSerializer()).create();
		}
	}
}
