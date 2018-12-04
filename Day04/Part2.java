import java.util.*;

public class Part2 {

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

		ArrayList<Integer> guardIDRecord = new ArrayList<Integer>();

		int[][] frequency = new int[20][60];

		for (int i = 0; i < 1088; i++) {
			if (timeRecord[i].contains("Guard")) {
				int indexHash = timeRecord[i].indexOf("#");
				int indexSpace = timeRecord[i].indexOf(" ", indexHash);
				guardID = Integer.parseInt(timeRecord[i].substring(indexHash+1, indexSpace));
				if (!guardIDRecord.contains(guardID)) {
					guardIDRecord.add(guardID);
				}

			} else if (timeRecord[i].contains("falls")) {
				int indexRightSquareBracket = timeRecord[i].indexOf("]");
				startSleep = Integer.parseInt(timeRecord[i].substring(indexRightSquareBracket-2, indexRightSquareBracket));
			
			} else if (timeRecord[i].contains("wakes")) {
				int indexRightSquareBracket = timeRecord[i].indexOf("]");
				endSleep = Integer.parseInt(timeRecord[i].substring(indexRightSquareBracket-2, indexRightSquareBracket));
				int guardIDIndex = guardIDRecord.indexOf(guardID);
					for (int minute = startSleep; minute < endSleep; minute++) {
						frequency[guardIDIndex][minute] += 1;
					}
				
			}
		}

		int mostSleptMinute = 0;
		int mostSleptFreq = 0;
		int mostSleptGuardIndex = 0;
		for (int guardIndex = 0; guardIndex < 20; guardIndex++) {
			for (int minuteIndex = 0; minuteIndex < 60; minuteIndex++) {
				if (frequency[guardIndex][minuteIndex] > mostSleptFreq) {
					mostSleptMinute = minuteIndex;
					mostSleptFreq = frequency[guardIndex][minuteIndex];
					mostSleptGuardIndex = guardIndex;
				}
			}			
		}

		System.out.println(guardIDRecord.get(mostSleptGuardIndex)*mostSleptMinute);

		sc.close();

	}
	
}