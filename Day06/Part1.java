import java.util.*;

public class Part1 {

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

		for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
			for (int colIndex = 0; colIndex < grid[rowIndex].length; colIndex++) {
				int min = -1;
				int closestDataPoint = 0;
				boolean closerPointFound = false;
				for (int pointIndex = 0; pointIndex < dataPoints.length; pointIndex++) {
					int distance = Math.abs(rowIndex - dataPoints[pointIndex][1]) + Math.abs(colIndex - dataPoints[pointIndex][0]);
					if (min == -1) {
						min = distance;
						closestDataPoint = pointIndex;
						closerPointFound = true;
					} else if (min > distance) {
						min = distance;
						closestDataPoint = pointIndex;
						closerPointFound = true;
					} else if (min == distance) {
						closerPointFound = false;
					}
				}
				if (!closerPointFound) {
					grid[rowIndex][colIndex] = -1;
				} else {
					grid[rowIndex][colIndex] = closestDataPoint;
				}
			}
		}

		ArrayList<Integer> excludedDatapoints = new ArrayList<Integer>();

		for (int colIndex = 0; colIndex < grid[0].length; colIndex++) {
			if (!excludedDatapoints.contains(grid[0][colIndex])) {
				excludedDatapoints.add(grid[0][colIndex]);
			}
			if (!excludedDatapoints.contains(grid[grid.length-1][colIndex])) {
				excludedDatapoints.add(grid[grid.length-1][colIndex]);
			}
		}
		for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
			if (!excludedDatapoints.contains(grid[rowIndex][0])) {
				excludedDatapoints.add(grid[rowIndex][0]);
			}
			if (!excludedDatapoints.contains(grid[rowIndex][grid[rowIndex].length-1])) {
				excludedDatapoints.add(grid[rowIndex][grid[rowIndex].length-1]);
			}
		}

		HashMap<Integer, Integer> areasOfDatapoints = new HashMap<Integer, Integer>();

		for (int rowIndex = 0; rowIndex < grid.length; rowIndex++) {
			for (int colIndex = 0; colIndex < grid[rowIndex].length; colIndex++) {
				int currentSquare = grid[rowIndex][colIndex];
				if (!excludedDatapoints.contains(currentSquare)) {
					if (areasOfDatapoints.containsKey(currentSquare)) {
						areasOfDatapoints.put(currentSquare, areasOfDatapoints.get(currentSquare)+1);
					} else {
						areasOfDatapoints.put(currentSquare, 1);
					}
				}
			}
		}

		int maxArea = 0;

		for (Integer val : areasOfDatapoints.values()) {
			if (maxArea < val) {
				maxArea = val;
			}
		}

		System.out.println(maxArea);
		sc.close();
	}
}