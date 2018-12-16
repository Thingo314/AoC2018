import java.util.*;

public class Part2 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		String[][] tracks = new String[150][150];
		int[][] cartPositions = new int[150][150];
		int[][] cartIntersectionChoice = new int[150][150];

		int lineCounter = 0;
		int cartTotal = 0;

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			int charCounter = 0;

			for (char c : line.toCharArray()) {
				if (c == 'v' || c == '^') {
					tracks[lineCounter][charCounter] = "|";
					cartTotal++;
					if (c == '^') {
						cartPositions[lineCounter][charCounter] = 1;
					} else {
						cartPositions[lineCounter][charCounter] = 3;
					}
				} else if (c == '<' || c == '>') {
					tracks[lineCounter][charCounter] = "-";
					cartTotal++;
					if (c == '>') {
						cartPositions[lineCounter][charCounter] = 2;
					} else {
						cartPositions[lineCounter][charCounter] = 4;
					}
				} else {
					tracks[lineCounter][charCounter] = String.valueOf(c);
				}
				charCounter++;
			}

			lineCounter++;

		}
		sc.close();

		int[][] cartPositionsBuffer = new int[150][150];

		while (cartTotal != 1) {
			for (int i = 0; i < cartPositions.length; i++) {
				for (int j = 0; j < cartPositions[i].length; j++) {
					if (cartPositions[i][j] == 1 && i > 0) {
						if (cartPositionsBuffer[i-1][j] != 0 || (cartPositionsBuffer[i][j] != 0 && cartPositionsBuffer[i][j] != 1)) {
							cartPositions[i-1][j] = 0;
							cartPositionsBuffer[i-1][j] = 0;
							cartPositionsBuffer[i][j] = 0;
							cartIntersectionChoice[i-1][j] = 0;
							cartIntersectionChoice[i][j] = 0;
						} else {
							cartPositionsBuffer[i-1][j] = 1;
							cartPositionsBuffer[i][j] = 0;
							int temp = cartIntersectionChoice[i-1][j];
							cartIntersectionChoice[i-1][j] = cartIntersectionChoice[i][j];
							cartIntersectionChoice[i][j] = temp;
						}
					} else if (cartPositions[i][j] == 2 && j < cartPositions[i].length - 1) {
						if (cartPositionsBuffer[i][j+1] != 0 || (cartPositionsBuffer[i][j] != 0 && cartPositionsBuffer[i][j] != 2)) {
							cartPositions[i][j+1] = 0;
							cartPositionsBuffer[i][j+1] = 0;
							cartPositionsBuffer[i][j] = 0;
							cartIntersectionChoice[i][j+1] = 0;
							cartIntersectionChoice[i][j] = 0;
						} else {
							cartPositionsBuffer[i][j+1] = 2;
							cartPositionsBuffer[i][j] = 0;
							int temp = cartIntersectionChoice[i][j+1];
							cartIntersectionChoice[i][j+1] = cartIntersectionChoice[i][j];
							cartIntersectionChoice[i][j] = temp;
						}
					} else if (cartPositions[i][j] == 3 && i < cartPositions.length - 1) {
						if (cartPositionsBuffer[i+1][j] != 0 || (cartPositionsBuffer[i][j] != 0 && cartPositionsBuffer[i][j] != 3)) {
							cartPositions[i+1][j] = 0;
							cartPositionsBuffer[i+1][j] = 0;
							cartPositionsBuffer[i][j] = 0;
							cartIntersectionChoice[i+1][j] = 0;
							cartIntersectionChoice[i][j] = 0;
						} else {
							cartPositionsBuffer[i+1][j] = 3;
							cartPositionsBuffer[i][j] = 0;
							int temp = cartIntersectionChoice[i+1][j];
							cartIntersectionChoice[i+1][j] = cartIntersectionChoice[i][j];
							cartIntersectionChoice[i][j] = temp;
						}
					} else if (cartPositions[i][j] == 4 && j > 0) {
						if (cartPositionsBuffer[i][j-1] != 0 || (cartPositionsBuffer[i][j] != 0 && cartPositionsBuffer[i][j] != 4)) {
							cartPositions[i][j-1] = 0;
							cartPositionsBuffer[i][j-1] = 0;
							cartPositionsBuffer[i][j] = 0;
							cartIntersectionChoice[i][j-1] = 0;
							cartIntersectionChoice[i][j] = 0;
						} else {
							cartPositionsBuffer[i][j-1] = 4;
							cartPositionsBuffer[i][j] = 0;
							int temp = cartIntersectionChoice[i][j-1];
							cartIntersectionChoice[i][j-1] = cartIntersectionChoice[i][j];
							cartIntersectionChoice[i][j] = temp;
						}
					}

				}
			}

			for (int i = 0; i < cartPositionsBuffer.length; i++) {
				for (int j = 0; j < cartPositionsBuffer[i].length; j++) {
					if (cartPositionsBuffer[i][j] == 1) {
						if (tracks[i][j].equals("/")) {
							cartPositionsBuffer[i][j] = 2;
						} else if (tracks[i][j].equals("\\")) {
							cartPositionsBuffer[i][j] = 4;
						} else if (tracks[i][j].equals("+")) {
							if (cartIntersectionChoice[i][j] == 0) {
								cartPositionsBuffer[i][j] = 4;
								cartIntersectionChoice[i][j] = 1;
							} else if (cartIntersectionChoice[i][j] == 1) {
								cartPositionsBuffer[i][j] = 1;
								cartIntersectionChoice[i][j] = 2;
							} else if (cartIntersectionChoice[i][j] == 2) {
								cartPositionsBuffer[i][j] = 2;
								cartIntersectionChoice[i][j] = 0;
							}
						}
					} else if (cartPositionsBuffer[i][j] == 2) {
						if (tracks[i][j].equals("/")) {
							cartPositionsBuffer[i][j] = 1;
						} else if (tracks[i][j].equals("\\")) {
							cartPositionsBuffer[i][j] = 3;
						} else if (tracks[i][j].equals("+")) {
							if (cartIntersectionChoice[i][j] == 0) {
								cartPositionsBuffer[i][j] = 1;
								cartIntersectionChoice[i][j] = 1;
							} else if (cartIntersectionChoice[i][j] == 1) {
								cartPositionsBuffer[i][j] = 2;
								cartIntersectionChoice[i][j] = 2;
							} else if (cartIntersectionChoice[i][j] == 2) {
								cartPositionsBuffer[i][j] = 3;
								cartIntersectionChoice[i][j] = 0;
							}
						}
					} else if (cartPositionsBuffer[i][j] == 3) {
						if (tracks[i][j].equals("/")) {
							cartPositionsBuffer[i][j] = 4;
						} else if (tracks[i][j].equals("\\")) {
							cartPositionsBuffer[i][j] = 2;
						} else if (tracks[i][j].equals("+")) {
							if (cartIntersectionChoice[i][j] == 0) {
								cartPositionsBuffer[i][j] = 2;
								cartIntersectionChoice[i][j] = 1;
							} else if (cartIntersectionChoice[i][j] == 1) {
								cartPositionsBuffer[i][j] = 3;
								cartIntersectionChoice[i][j] = 2;
							} else if (cartIntersectionChoice[i][j] == 2) {
								cartPositionsBuffer[i][j] = 4;
								cartIntersectionChoice[i][j] = 0;
							}
						}
					} else if (cartPositionsBuffer[i][j] == 4) {
						if (tracks[i][j].equals("/")) {
							cartPositionsBuffer[i][j] = 3;
						} else if (tracks[i][j].equals("\\")) {
							cartPositionsBuffer[i][j] = 1;
						} else if (tracks[i][j].equals("+")) {
							if (cartIntersectionChoice[i][j] == 0) {
								cartPositionsBuffer[i][j] = 3;
								cartIntersectionChoice[i][j] = 1;
							} else if (cartIntersectionChoice[i][j] == 1) {
								cartPositionsBuffer[i][j] = 4;
								cartIntersectionChoice[i][j] = 2;
							} else if (cartIntersectionChoice[i][j] == 2) {
								cartPositionsBuffer[i][j] = 1;
								cartIntersectionChoice[i][j] = 0;
							}
						}
					}
				}
			}
			cartTotal = 0;
			for (int i = 0; i < cartPositions.length; i++) {
				for (int j = 0; j < cartPositions[i].length; j++) {
					cartPositions[i][j] = cartPositionsBuffer[i][j];
					if (cartPositions[i][j] != 0) {
						cartTotal++;
					}
				}
			}

		}

		int xCoord = 0;
		int yCoord = 0;
		for (int i = 0; i < cartPositions.length; i++) {
			for (int j = 0; j < cartPositions[i].length; j++) {
				if (cartPositions[i][j] != 0) {
					xCoord = j;
					yCoord = i;
				}
			}
		}
		System.out.println(xCoord+","+yCoord);
	}

}
