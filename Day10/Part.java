import java.util.*;

public class Part {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		int[][] pos = new int[367][2];
		int[][] vel = new int[367][2];
		int counter = 0;

		while (sc.hasNextLine()) {
			String[] line = sc.nextLine().replace("position=<", "").replace(",", "").replace("> velocity=<", " ").replace(">", "").trim().split("\\s+");
			pos[counter][0] = Integer.parseInt(line[0]);
			pos[counter][1] = Integer.parseInt(line[1]);
			vel[counter][0] = Integer.parseInt(line[2]);
			vel[counter][1] = Integer.parseInt(line[3]);
			counter++;
		}

		int prevRangeY = findRangeY(pos);
		updatePositions(pos, vel);
		int nextRangeY = findRangeY(pos);
		int time = 0;
		while (nextRangeY < prevRangeY) {
			prevRangeY = nextRangeY;
			updatePositions(pos, vel);
			nextRangeY = findRangeY(pos);
			time++;
		}

		stepBackPositions(pos, vel);
		int rangeY = findRangeY(pos);
		int rangeX = findRangeX(pos);

		int[] mins = findMins(pos);

		String[][] grid = new String[rangeY-mins[1]+1][rangeX-mins[0]+1];
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = ".";
			}
		}

		for (int[] posVec : pos) {
			grid[posVec[1]-mins[1]][posVec[0]-mins[0]] = "#";
		}

		for (String[] gridArr : grid) {
			System.out.println(Arrays.toString(gridArr));
		}
		System.out.println(time);

		sc.close();

	}

	public static int findRangeY (int[][] positionArray) {
		int minY = 0;
		int maxY = 0;

		for (int[] posVec : positionArray) {
			if (posVec[1] > maxY) {
				maxY = posVec[1];
			} else if (posVec[1] < minY) {
				minY = posVec[1];
			}
		}

		return maxY - minY;
	}

	public static void updatePositions (int[][] positionArray, int[][] velocityArray) {
		for (int i = 0; i < positionArray.length; i++) {
			positionArray[i][0] += velocityArray[i][0];
			positionArray[i][1] += velocityArray[i][1];
		}
	}

	public static int findRangeX (int[][] positionArray) {
		int minX = 0;
		int maxX = 0;

		for (int[] posVec : positionArray) {
			if (posVec[0] > maxX) {
				maxX = posVec[0];
			} else if (posVec[0] < minX) {
				minX = posVec[0];
			}
		}

		return maxX-minX;
	}

	public static int[] findMins (int[][] positionArray) {
		int minX = 314195;
		int minY = 314195;
		for (int[] posVec : positionArray) {
			if (posVec[0] < minX) {
				minX = posVec[0];
			}
			if (posVec[1] < minY) {
				minY = posVec[1];
			}
		}
		int[] arr = {minX, minY};
		return arr;
	}

	public static void stepBackPositions (int[][] positionArray, int[][] velocityArray) {
		for (int i = 0; i < positionArray.length; i++) {
			positionArray[i][0] -= velocityArray[i][0];
			positionArray[i][1] -= velocityArray[i][1];
		}
	}

}
