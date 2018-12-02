import java.util.*;

public class Part2 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		ArrayList<String> lines = new ArrayList<String>();
		String line1="", line2="";

		while (sc.hasNextLine()) {

			String line = sc.nextLine();
			lines.add(line);

			if (lines.size() > 1) {
				for (int i = lines.size()-2; i >= 0; i--) {
					String comparedString = lines.get(i);
					if (stringCompare(line, comparedString)) {
						line1 = line;
						line2 = comparedString;
						break;
					}
				}
			}

			if (line1.length() != 0 && line2.length() != 0) {
				break;
			}

		}

		System.out.println(line1);
		System.out.println(line2);

		sc.close();

	}

	public static boolean stringCompare(String s1, String s2){

		int stringLength = Math.min(s1.length(), s2.length());

		for (int i = 0; i < stringLength; i++) {
			String newS1 = s1.substring(0,i).concat(s1.substring(i+1));
			String newS2 = s2.substring(0,i).concat(s2.substring(i+1));
			if (newS1.equals(newS2)) {
				return true;
			}
		}

		return false;

	}

}