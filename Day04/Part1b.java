import java.util.*;

public class Part1b {

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

		int[] frequency = new int[60];

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
				if (guardID == 3209) {
					for (int minute = startSleep; minute < endSleep; minute++) {
						frequency[minute] += 1;
					}
				}
			}
		}

		int mostSleptMinute = 0;
		int mostSleptFreq = 0;

		for (int index = 0; index < 60; index++) {
			if (frequency[index] > mostSleptFreq) {
				mostSleptMinute = index;
				mostSleptFreq = frequency[index];
			}
		}

		System.out.println(mostSleptMinute);

		sc.close();

	}
	
}