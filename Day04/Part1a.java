import java.util.*;

public class Part1a {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		String[] timeRecord = new String[1088];

		int counter = 0;

		while (sc.hasNextLine()) {
			timeRecord[counter] = sc.nextLine();
			counter++;
		}

		Arrays.sort(timeRecord);

		Map<Integer, Integer> sleepRecord = new HashMap<Integer, Integer>();

		int guardID = 0;
		int startSleep = 0;
		int endSleep = 0;

		for (int i = 0; i < 1088; i++) {
			if (timeRecord[i].contains("Guard")) {
				int indexHash = timeRecord[i].indexOf("#");
				int indexSpace = timeRecord[i].indexOf(" ", indexHash);
				guardID = Integer.parseInt(timeRecord[i].substring(indexHash+1, indexSpace));
			
			} else if (timeRecord[i].contains("falls")) {
				int indexRightSquareBracket = timeRecord[i].indexOf("]");
				startSleep = Integer.parseInt(timeRecord[i].substring(indexRightSquareBracket-2, indexRightSquareBracket));
			
			} else if (timeRecord[i].contains("wakes")) {
				int indexRightSquareBracket = timeRecord[i].indexOf("]");
				endSleep = Integer.parseInt(timeRecord[i].substring(indexRightSquareBracket-2, indexRightSquareBracket));
				
				int timeSlept = endSleep - startSleep;
				if (sleepRecord.containsKey(guardID)) {
					sleepRecord.put(guardID, sleepRecord.get(guardID)+timeSlept);
				} else {
					sleepRecord.put(guardID, timeSlept);
				}
			}
		}

		int maxSlept = 0;
		int guardIDMaxSlept = 0;

		for (Map.Entry<Integer, Integer> pair : sleepRecord.entrySet()) {
			if (pair.getValue() > maxSlept) {
				maxSlept = pair.getValue();
				guardIDMaxSlept = pair.getKey();
			}
		}

		System.out.println(guardIDMaxSlept);

		sc.close();

	}
	
}