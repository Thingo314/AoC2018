import java.util.*;

public class Part2 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		HashMap<String, ArrayList<String>> adjList = new HashMap<String, ArrayList<String>>();

		while (sc.hasNextLine()) {
			String edge = sc.nextLine();

			String startNode = edge.substring(5, 6);
			String endNode = edge.substring(36, 37);

			if (!adjList.containsKey(endNode)) {
				adjList.put(endNode, new ArrayList<String>());
			}
			if (adjList.containsKey(startNode)) {
				ArrayList<String> list = adjList.get(startNode);
				list.add(endNode);
				adjList.put(startNode, list);
			} else {
				adjList.put(startNode, new ArrayList<String>(Arrays.asList(endNode)));
			}
		}

		ArrayList<String> dependencies = new ArrayList<String>();
		ArrayList<String> possibleSteps = new ArrayList<String>();
		HashMap<String, Integer> working = new HashMap<String, Integer>();
		int seconds = 0;

		while (adjList.size() != 0) {
			for (ArrayList list : adjList.values()) {
				for (Object str : list) {
					if (!dependencies.contains(str)) {
						dependencies.add((String)str);
					}
				}

			}
			for (String key : adjList.keySet()) {
				if (!dependencies.contains(key)) {
					possibleSteps.add(key);
				}
			}

			Collections.sort(possibleSteps);
			int index = 0;

			while (index < 5) {
				String nextStep = possibleSteps.get(index);
				if (working.containsKey(nextStep)) {
					working.put(nextStep, working.get(nextStep)-1);
				} else {
					int timeToComplete = Character.getNumericValue(nextStep.charAt(0))+50;
					working.put(nextStep, timeToComplete);
				}
				index++;
				if (index >= possibleSteps.size()) {
					break;
				}
			}

			Set<String> finishedSteps = new HashSet<String>();
			for (Map.Entry entry : working.entrySet()) {
				if (Objects.equals(0, entry.getValue())) {
					finishedSteps.add(String.valueOf(entry.getKey()));
				}
			}

			for (String str : finishedSteps) {
				adjList.remove(str);
			}
			dependencies.clear();
			possibleSteps.clear();
			seconds++;
		}

		System.out.println(seconds);
		sc.close();

	}
	
}
