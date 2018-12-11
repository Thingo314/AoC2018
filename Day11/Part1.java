import java.util.*;

public class Part1 {

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

		int max = 0;
		int topLeftRow = 0;
		int topLeftCol = 0;

		for (int rowIndex = 0; rowIndex < grid.length - 3; rowIndex++) {
			for (int colIndex = 0; colIndex < grid[rowIndex].length - 3; colIndex++) {
				int sum = 0;

				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						sum += grid[rowIndex+i][colIndex+j];
					}
				}

				if (max < sum) {
					max = sum;
					topLeftRow = rowIndex+1;
					topLeftCol = colIndex+1;
				}
			}
		}

		System.out.println(topLeftCol+ "," + topLeftRow);

	}

}
