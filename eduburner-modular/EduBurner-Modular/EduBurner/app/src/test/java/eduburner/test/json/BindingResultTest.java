package eduburner.test.json;

import org.springframework.validation.FieldError;
import org.testng.annotations.Test;

import eduburner.json.JsonHelper;
import eduburner.test.mock.MockBindingResult;

public class BindingResultTest {
	
	@Test
	public void testBindingResult(){
		MockBindingResult mbr = new MockBindingResult("test");
		
		mbr.addError(new FieldError("User", "name", "Null"));
		
		System.out.println("val: " + JsonHelper.toJson(mbr));
		
	}
}
