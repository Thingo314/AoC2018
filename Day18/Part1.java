import java.util.*;

public class Part1 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		char[][] grid = new char[50][50];

		int rowCounter = 0;
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			grid[rowCounter] = line.toCharArray();
			rowCounter++;
		}

		// debug print grid
		// for (char[] charArr : grid) {
		// 	System.out.println(Arrays.toString(charArr));
		// }

		int[][] directions = new int[][]{{-1,-1}, {-1,0}, {-1,1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1,1}};

		char[][] gridBuffer = new char[50][50];

		for (int i = 0; i < 10; i++) {
			for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
				for (int colIndex = 0; colIndex < grid[rowIndex].length; colIndex++) {
					HashMap<Character, Integer> surrChars = new HashMap<Character, Integer>();

					for (int[] direction : directions) {
						int rowSurr = rowIndex + direction[0];
						int colSurr = colIndex + direction[1];

						if(rowSurr >= 0 && rowSurr < grid.length) {
							if (colSurr >= 0 && colSurr < grid[rowSurr].length) {
								Integer count = surrChars.get(grid[rowSurr][colSurr]);
								if (surrChars.containsKey(grid[rowSurr][colSurr])) {
									surrChars.put(grid[rowSurr][colSurr], surrChars.get(grid[rowSurr][colSurr]) + 1);
								} else {
									surrChars.put(grid[rowSurr][colSurr], 1);
								}
							}
						}
					}

					if (grid[rowIndex][colIndex] == '.') {
						int numOfTrees = (surrChars.get('|') == null) ? 0 : surrChars.get('|');
						if (numOfTrees >= 3) {
							gridBuffer[rowIndex][colIndex] = '|';
						} else {
							gridBuffer[rowIndex][colIndex] = '.';
						}
					} else if (grid[rowIndex][colIndex] == '|') {
						int numOfLumYards = (surrChars.get('#') == null) ? 0 : surrChars.get('#');
						if (numOfLumYards >= 3) {
							gridBuffer[rowIndex][colIndex] = '#';
						} else {
							gridBuffer[rowIndex][colIndex] = '|';
						}
					} else if (grid[rowIndex][colIndex] == '#') {
						int numOfTrees = (surrChars.get('|') == null) ? 0 : surrChars.get('|');
						int numOfLumYards = (surrChars.get('#') == null) ? 0 : surrChars.get('#');
						if (numOfLumYards >= 1 && numOfTrees >= 1) {
							gridBuffer[rowIndex][colIndex] = '#';
						} else {
							gridBuffer[rowIndex][colIndex] = '.';
						}
						
					}
				}
			}

			for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
				for (int colIndex = 0; colIndex < grid[rowIndex].length; colIndex++) {
					grid[rowIndex][colIndex] = gridBuffer[rowIndex][colIndex];
				}
			}

		}

		int woodAcres = 0;
		int lumYardAcres = 0;

		for (char[] charArr : grid) {
			for (char ch : charArr) {
				if (ch == '|') {
					woodAcres++;
				}
				if (ch == '#') {
					lumYardAcres++;
				}
			}
		}

		System.out.println(woodAcres*lumYardAcres);

		sc.close();

	}

}
