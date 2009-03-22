package eduburner.test.misc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.annotations.Test;

public class TestDateFormat {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	@Test
	public void testDateFormat(){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		//String s = simpleDateFormat.format(new Date());
		//System.out.println(s);
		try {
			Date date = simpleDateFormat.parse("2009-3-22");
			System.out.println("dat: " + date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
