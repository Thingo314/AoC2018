import java.util.*;

public class Part1 {

	public static void main(String args[]) {
		
		Scanner sc = new Scanner(System.in);
		
		int result = 0;
		
		while (sc.hasNextLine()) {
		
			String line = sc.nextLine();

			if (line.charAt(0) == '+') {
				result += Integer.parseInt(line.substring(1));
			} else {
				result -= Integer.parseInt(line.substring(1));
			}
		
		}
		
		System.out.println(result);
		
		sc.close();
	}
}