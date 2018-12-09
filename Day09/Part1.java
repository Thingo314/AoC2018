import java.util.*;

public class Part1 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		String[] parameters = sc.nextLine().trim().split(" ");

		int players = Integer.parseInt(parameters[0]);
		int highestMarbleScore = Integer.parseInt(parameters[6]);

		int[] playerScores = new int[players];
		int currentPlayer = 0;
		int marbleScore = 1;
		int marbleIndex = 1;

		ArrayList<Integer> marblesInCircle = new ArrayList<Integer>();
		marblesInCircle.add(0);

		while (marbleScore <= highestMarbleScore) {
			if (marbleScore % 23 == 0) {
				playerScores[currentPlayer] += marbleScore;

				marbleIndex = ((marbleIndex-7) % marblesInCircle.size() + marblesInCircle.size()) % marblesInCircle.size();
				playerScores[currentPlayer] += marblesInCircle.get(marbleIndex);
				marblesInCircle.remove(marbleIndex);
			} else {
				marbleIndex = (marbleIndex+2) % marblesInCircle.size();
				marblesInCircle.add(marbleIndex, marbleScore);
			}

			marbleScore++;
			currentPlayer++;

			if (currentPlayer >= players) {
				currentPlayer = 0;
			}
		}

		int maxScore = 0;

		for (int i = 0; i < playerScores.length; i++) {
			if (maxScore < playerScores[i]) {
				maxScore = playerScores[i];
			}
		}

		System.out.println(maxScore);
		sc.close();

	}

}
