import java.util.*;

public class Part1 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		int countOfTwo = 0;
		int countOfThree = 0;

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			Map<Character, Integer> frequency = new HashMap<Character, Integer>();

			for (char ch : line.toCharArray()) {
				if (frequency.containsKey(ch)) {
					frequency.put(ch, frequency.get(ch)+1);
				} else {
					frequency.put(ch, 1);
				}
			}

			if (frequency.containsValue(2)) {
				countOfTwo++;
			}
			if (frequency.containsValue(3)) {
				countOfThree++;
			}
		}

		System.out.println(countOfTwo*countOfThree);

		sc.close();

	}

}