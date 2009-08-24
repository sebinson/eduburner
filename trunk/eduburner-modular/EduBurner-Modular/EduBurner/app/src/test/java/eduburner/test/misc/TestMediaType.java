package eduburner.test.misc;

import org.springframework.http.MediaType;

public class TestMediaType {
	
	//@Test
	public void testMediaType(){
		MediaType type1 = MediaType.parseMediaType("application/json;charset=UTF-8");
		MediaType type2 = MediaType.parseMediaType("application/json;charset=UTF-8");
		
		System.out.println(type1.includes(type2));
	}
}
