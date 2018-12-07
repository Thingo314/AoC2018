import java.util.*;

public class Part1 {

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
		String steps = "";

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
			steps = steps.concat(possibleSteps.get(0));
			adjList.remove(possibleSteps.get(0));

			dependencies.clear();
			possibleSteps.clear();
		}

		System.out.println(steps);
		sc.close();

	}
	
}
