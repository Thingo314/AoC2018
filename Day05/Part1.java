import java.util.*;

public class Part1 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		String polymer = sc.nextLine();

		polymer = polymerReact(polymer);
		System.out.println(polymer.length());

		sc.close();

	}

	public static String polymerReact (String polymer) {
		int initialLength = polymer.length();
		int index = 0;

		while (index < polymer.length()-1) {
			boolean sameChar = (Character.toLowerCase(polymer.charAt(index)) == Character.toLowerCase(polymer.charAt(index+1)));

			boolean alternatingCase = (Character.isLowerCase(polymer.charAt(index)) && Character.isUpperCase(polymer.charAt(index+1)))
									|| (Character.isUpperCase(polymer.charAt(index)) && Character.isLowerCase(polymer.charAt(index+1)));

			if (sameChar && alternatingCase) {
				polymer = polymer.substring(0, index).concat(polymer.substring(index+2, polymer.length()));
			} else {
				index++;
			}
		}

		if (index >= initialLength-1) {
			return polymer;
		} else {
			return polymerReact(polymer);
		}
	}
	
}