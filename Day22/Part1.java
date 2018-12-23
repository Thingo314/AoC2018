import java.util.*;

public class Part1 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		int depth = Integer.parseInt(sc.nextLine().substring(7));
		String[] target = sc.nextLine().substring(8).split(",");
		int targetX = Integer.parseInt(target[0]);
		int targetY = Integer.parseInt(target[1]);

		sc.close();

		int[][] caveSystemGeoIndex = new int[depth][targetX+2];
		int[][] caveSystemEroIndex = new int[depth][targetX+2];

		for (int rowIndex = 0; rowIndex < caveSystemGeoIndex.length; rowIndex++) {
			for (int colIndex = 0; colIndex < caveSystemGeoIndex[rowIndex].length; colIndex++) {
				if (rowIndex == 0) {
					caveSystemGeoIndex[rowIndex][colIndex] = 16807 * colIndex;
				} else if (colIndex == 0) {
					caveSystemGeoIndex[rowIndex][colIndex] = 48271 * rowIndex;
				} else if (rowIndex == targetY && colIndex == targetX) {
					caveSystemGeoIndex[rowIndex][colIndex] = 0;
				} else {
					caveSystemGeoIndex[rowIndex][colIndex] = caveSystemEroIndex[rowIndex][colIndex-1]*caveSystemEroIndex[rowIndex-1][colIndex];
				}
				caveSystemEroIndex[rowIndex][colIndex] = ((caveSystemGeoIndex[rowIndex][colIndex]+depth)%20183);
			}
		}

		int risk = 0;

		for (int rowIndex = 0; rowIndex <= targetY; rowIndex++) {
			for (int colIndex = 0; colIndex <= targetX; colIndex++) {
				risk += caveSystemEroIndex[rowIndex][colIndex]%3;
			}
		}

		System.out.println(risk);

	}
	
}