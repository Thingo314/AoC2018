import java.util.*;

public class Part2 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		String[] parameters = sc.nextLine().trim().split(" ");

		int players = Integer.parseInt(parameters[0]);
		int highestMarbleScore = Integer.parseInt(parameters[6])*100;

		long[] playerScores = new long[players];
		int currentPlayer = 0;
		int marbleScore = 1;
		int marbleIndex = 1;

		LinkedList<Integer> marblesInCircle = new LinkedList<Integer>();
		marblesInCircle.add(0);

		while (marbleScore <= highestMarbleScore) {
			if (marbleScore % 23 == 0) {
				playerScores[currentPlayer] += marbleScore;

				for (int i = 0; i < 7; i++) {
					marblesInCircle.add(marblesInCircle.pollFirst());
				}

				playerScores[currentPlayer] += marblesInCircle.pollFirst();
				marblesInCircle.push(marblesInCircle.pollLast());
			} else {
				marblesInCircle.push(marblesInCircle.pollLast());
				marblesInCircle.push(marbleScore);
			}

			marbleScore++;
			currentPlayer++;

			if (currentPlayer >= players) {
				currentPlayer = 0;
			}

		}

		long maxScore = 0;

		for (int i = 0; i < playerScores.length; i++) {
			if (maxScore < playerScores[i]) {
				maxScore = playerScores[i];
			}
		}

		System.out.println(maxScore);
		sc.close();

	}

}
