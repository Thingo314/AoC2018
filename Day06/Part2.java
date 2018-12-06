import java.util.*;

public class Part2 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		int counter = 0;
		int[][] grid = new int[360][360];
		int[][] dataPoints = new int[50][2];

		while (sc.hasNextLine()) {
			String[] datapoint = sc.nextLine().replace(",", "").split(" ");
			dataPoints[counter][0] = Integer.parseInt(datapoint[0]);
			dataPoints[counter][1] = Integer.parseInt(datapoint[1]);
			counter++;
			grid[Integer.parseInt(datapoint[1])][Integer.parseInt(datapoint[0])] = counter;
		}

		int regionArea = 0;
		int threshold = 10000;
		
		for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
			for (int colIndex = 0; colIndex < grid[rowIndex].length; colIndex++) {
				int totalManhattan = 0;
				for (int pointIndex = 0; pointIndex < dataPoints.length; pointIndex++) {
					totalManhattan += Math.abs(rowIndex - dataPoints[pointIndex][1]) + Math.abs(colIndex - dataPoints[pointIndex][0]);
				}
				if (totalManhattan < threshold) {
					regionArea++;
				}

			}
		}

		System.out.println(regionArea);

		sc.close();

	}
	
}