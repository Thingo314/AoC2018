import java.util.*;

public class Part2 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		String initialState = sc.nextLine();
		String gen0State = initialState.substring(initialState.indexOf(": ")+2);

		ArrayList<String> hasPlant = new ArrayList<String>();
		int indexOfZero = 0;

		for (int i = 0; i < gen0State.length(); i++) {
			hasPlant.add(gen0State.substring(i, i+1));
		}

		HashMap<String, String> rules = new HashMap<String, String>();

		sc.nextLine();
		while (sc.hasNextLine()) {
			String[] rule = sc.nextLine().split(" => ");
			rules.put(rule[0], rule[1]);
		}
		sc.close();

		ArrayList<String> nextGenPlants = hasPlant;

		int prevResult = 0;
		int difference = 0;
		for (long i = 0; i < 2000; i++) {
			nextGenPlants = nextGeneration(nextGenPlants, rules);
			indexOfZero+=2;
			while (nextGenPlants.get(nextGenPlants.size()-1).equals(".")) {
				nextGenPlants.remove(nextGenPlants.size()-1);
			}
			while (nextGenPlants.get(0).equals(".")) {
				nextGenPlants.remove(0);
				indexOfZero--;
			}
			int result = 0;
			for (int j = 0; j < nextGenPlants.size(); j++) {
				if (nextGenPlants.get(j).equals("#")) {
					result += j-indexOfZero;
				}
			}
			difference = result - prevResult;
			prevResult = result;
		}

		long total = 50000000000l-2000l;
		total *= difference;
		total += prevResult;
		System.out.println(total);

	}

	public static ArrayList<String> nextGeneration (ArrayList<String> plantsList, HashMap<String, String> ruleMap) {
		ArrayList<String> results = new ArrayList<String>();

		for (int i = -4; i < plantsList.size(); i++) {
			String plants = "";
			for (int j = 0; j < 5; j++) {
				if (i+j < 0) {
					plants = plants.concat(".");
				} else if (i+j >= plantsList.size()) {
					plants = plants.concat(".");
				} else {
					plants = plants.concat(plantsList.get(i+j));
				}
			}
			String result = "";
			if (ruleMap.containsKey(plants)){
				result = ruleMap.get(plants);
			} else {
				result = ".";
			}
			results.add(result);
		}

		return results;
	}

}
