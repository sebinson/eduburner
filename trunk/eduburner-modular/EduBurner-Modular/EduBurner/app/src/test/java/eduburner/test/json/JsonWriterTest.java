package eduburner.test.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.testng.annotations.Test;

import com.google.gson.annotations.Expose;

import eduburner.enumerations.CourseStatus;
import eduburner.json.JsonHelper;
import eduburner.test.service.BaseServiceTestSupport;

public class JsonWriterTest extends BaseServiceTestSupport{
	
	@Autowired
	@Qualifier("jsonHelper")
	private JsonHelper jsonHelper;

	private static final Logger logger = LoggerFactory
			.getLogger(JsonWriterTest.class);

	//@Test
	public void testJsonWriter() {
		Car car = new Car();
		car.setName("name");
		car.setType("type");
		car.setStatus(CourseStatus.Closed);

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

		logger.debug("josn: " + jsonHelper.toJson(model));
		logger.debug("json1: " + jsonHelper.toJson(car));
		logger.debug("json2: " + jsonHelper.toJson(wheel));
	}

	public class Car {
		@Expose
		private String name;
		@Expose
		private String type;
		@Expose
		private Wheel wheel;
		@Expose
		private CourseStatus status;

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

		public CourseStatus getStatus() {
			return status;
		}

		public void setStatus(CourseStatus status) {
			this.status = status;
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
