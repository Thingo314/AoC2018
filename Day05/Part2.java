import java.util.*;

public class Part2 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		String polymer = sc.nextLine();

		String alphabet = "abcdefghijklmnopqrstuvwxyz";

		int minLength = -1;
		
		for (char ch : alphabet.toCharArray()) {
			String enhancedPolymer = polymerRemove(polymer, ch);
			enhancedPolymer = polymerReact(enhancedPolymer);

			if (minLength == -1) {
				minLength = enhancedPolymer.length();
			} else if (minLength > enhancedPolymer.length()) {
				minLength = enhancedPolymer.length();
			}
		}


		System.out.println(minLength);

		sc.close();

	}

	public static String polymerReact (String polymer) {
		int initialLength = polymer.length();

		int index = 0;

		while (index < polymer.length()-1) {
			boolean alternatingCase = (Character.isLowerCase(polymer.charAt(index)) && Character.isUpperCase(polymer.charAt(index+1))) || (Character.isUpperCase(polymer.charAt(index)) && Character.isLowerCase(polymer.charAt(index+1)));

			if (Character.toLowerCase(polymer.charAt(index)) == Character.toLowerCase(polymer.charAt(index+1)) && alternatingCase) {
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

	public static String polymerRemove (String polymer, char ch) {
		int index = 0;

		while (index < polymer.length()) {
			if (Character.toLowerCase(polymer.charAt(index)) == ch) {
				polymer = polymer.substring(0, index).concat(polymer.substring(index+1, polymer.length()));
			} else  {
				index++;
			}
		}

		return polymer;
	}
	
}