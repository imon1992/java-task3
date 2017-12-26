package grep;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
//import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

//import org.hamcrest.Matcher;
//import org.junit.experimental.theories.Theories;

public class Grep {

	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		Grep grep = new Grep();
		System.out.println(grep.grepStrNumber(in.next()));
	}
	
	private Map<Integer, String> grepStrNumber(String s) {
		s = s.toLowerCase();
		Map<Integer, String> staff = new HashMap<Integer, String>(); 
		try {
			File file = new File("grep.txt");
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			int i = 1;
			Pattern pattern = Pattern.compile(s+".*");
			while((line = reader.readLine()) != null) {
				line = line.toLowerCase();
				java.util.regex.Matcher matcher = pattern.matcher(line);
				if(matcher.find()) {
					staff.put(i,line); 
				}
				i++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return staff;
	}

}
