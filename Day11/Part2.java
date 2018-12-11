import java.util.*;

public class Part2 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		int[][] grid = new int[300][300];

		int powIncrease = sc.nextInt();
		sc.close();

		for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
			for (int colIndex = 0; colIndex < grid[rowIndex].length; colIndex++) {
				int power = (colIndex + 11) * (rowIndex + 1);
				power += powIncrease;
				power *= colIndex + 11;
				power /= 100;
				grid[rowIndex][colIndex] = power % 10 - 5;
			}
		}

		int[][][] prevSums = new int[300][300][300];

		int max = 0;
		int topLeftRow = 0;
		int topLeftCol = 0;
		int maxSize = 0;

		for (int size = 1; size <= 300; size++) {
			for (int rowIndex = 0; rowIndex < grid.length - size; rowIndex++) {
				for (int colIndex = 0; colIndex < grid[rowIndex].length - size; colIndex++) {
					int sum = 0;

					if (prevSums[rowIndex][colIndex][size-1] == 0) {
						for (int i = 0; i < size; i++) {
							for (int j = 0; j < size; j++) {
								sum += grid[rowIndex+i][colIndex+j];
							}
						}
					} else {
						sum = prevSums[rowIndex][colIndex][size-1];

						for (int i = 0; i < size; i++) {
							sum += grid[rowIndex+i][colIndex+size-1];
							sum += grid[rowIndex+size-1][colIndex+i];
						}

						sum -= grid[rowIndex+size-1][colIndex+size-1];
					}

					prevSums[rowIndex][colIndex][size] = sum;

					if (max < sum) {
						max = sum;
						topLeftRow = rowIndex+1;
						topLeftCol = colIndex+1;
						maxSize = size;
					}
				}
			}
		}

		System.out.println(topLeftCol+ "," + topLeftRow + "," + maxSize);

	}

}
