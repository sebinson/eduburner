package readerside.crawler.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.apache.commons.codec.digest.DigestUtils;

public class ShareCountAnalyzer {

	@SuppressWarnings("unchecked")
	public static void main(String... args) throws Exception {
		/*
		 * ContextLoader loader = new GenericXmlContextLoader();
		 * ApplicationContext ctx = loader.loadContext(getConfigLocations());
		 * 
		 * IDao dao = (IDao) ctx.getBean("dao");
		 * 
		 * List<GrEntry> entries = (List<GrEntry>)dao.find("from GrEntry as ge"
		 * + " where ge.cnEntry = true order by ge.sharingNum desc");
		 * 
		 * PrintWriter pw = new PrintWriter(new File("d:/sharecount.csv"));
		 * pw.flush();
		 * 
		 * for(GrEntry entry:entries){ if(entry != null){
		 * pw.print(entry.getGrEntryId()); pw.print("," +
		 * entry.getSharingNum()); pw.println(); } } pw.flush(); pw.close();
		 * 
		 * System.out.println("finished.");
		 */

		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream("d:/sharecount2.csv");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			FileOutputStream fos = new FileOutputStream("d:/sharecount5.csv");
			DataOutputStream dos = new DataOutputStream(fos);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(dos));
			String strLine;
			// Read File Line By Line
			int i = 0;
			while ((strLine = br.readLine()) != null && i < 200) {
				/*String[] arr = strLine.split(",");
				String id = arr[0];// + arr[1];
				String num = arr[1];
				System.out.println("id is: " + id);
				System.out.println("num is:" + num);
				id = DigestUtils.md5Hex(id);
				bw.write(id + "," + num);*/
				bw.write(strLine);
				bw.write("\n");
				i++;
			}
			bw.flush();
			// Close the input stream
			in.close();
			dos.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace();
		}

	}

	private static String[] getConfigLocations() {
		return new String[] { "classpath:applicationContext.xml" };
	}
}
