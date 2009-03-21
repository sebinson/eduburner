package eduburner.test.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.Expose;

import eduburner.util.JsonUtils;

public class JsonWriterTest extends TestCase{

	private static final Logger logger = LoggerFactory
			.getLogger(JsonWriterTest.class);

	public void testJsonWriter() {
		Car car = new Car();
		car.setName("name");
		car.setType("type");

		Wheel wheel = new Wheel();
		wheel.setName("名称");
		wheel.setValue("轮子");
		
		car.setWheel(wheel);
		wheel.setCar(car);

		Map model = new HashMap();
		//model.put("car", car);
		
		List list = new ArrayList();
		
		list.add(car);
		list.add(wheel);
		
		model.put("cars", list);
		model.put("wheel", wheel);

		logger.debug("josn: " + JsonUtils.toJson(model));
	}

	public class Car {
		@Expose
		private String name;
		@Expose
		private String type;
		@Expose
		private Wheel wheel;

		public Wheel getWheel() {
			return wheel;
		}

		public void setWheel(Wheel wheel) {
			this.wheel = wheel;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}

	public class Wheel {
		@Expose
		private String name;
		@Expose
		private String value;
		private Car car;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public Car getCar() {
			return car;
		}

		public void setCar(Car car) {
			this.car = car;
		}
		
	}

}
