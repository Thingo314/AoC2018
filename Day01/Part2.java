import java.util.*;

public class Part2 {

	public static int result = 0;

	public static void main(String args[]) {
		
		Scanner sc = new Scanner(System.in);

		boolean earlyBreak = false;
		Set<Integer> results = new HashSet<Integer>();
		ArrayList<String> changes = new ArrayList<String>();

		while (sc.hasNextLine()) {

			String line = sc.nextLine();
			changes.add(line);

			int change = Integer.parseInt(line.substring(1));

			if (line.charAt(0) == '+') {
				result += change;
			} else {
				result -= change;
			}

			if (results.contains(result)) {
				earlyBreak = true;
				break;
			} else {
				results.add(result);
			}

		}

		if (!earlyBreak) {
			traceArray(results, changes);
		}

		System.out.println(result);

		sc.close();
	}

	public static void traceArray(Set<Integer> resultsSet, ArrayList<String> arrayList) {

		for (int element = 0; element < arrayList.size(); element++) {
			String line = arrayList.get(element);
			int change = Integer.parseInt(line.substring(1));

			if (line.charAt(0) == '+') {
				result += change;
			} else {
				result -= change;
			}

			if (resultsSet.contains(result)) {
				return;
			} else {
				resultsSet.add(result);
			}
		}
		
		traceArray(resultsSet, arrayList);		
	}
	
}