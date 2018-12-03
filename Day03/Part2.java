import java.util.*;

public class Part2 {
	
	public static String[] claimList = new String[1287];
	public static int[][] claimGrid = new int[1000][1000];
	public static boolean[] claimOverlap = new boolean[1287];
	public static int counter = 0;

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		while (sc.hasNextLine()) {
			String claim = sc.nextLine();
			claimList[counter] = claim;

			String[] claimParameters = claim.split(" ");

			String[] topLeft = new String[2];
			topLeft[0] = claimParameters[2].substring(0, claimParameters[2].indexOf(","));
			topLeft[1] = claimParameters[2].substring(claimParameters[2].indexOf(",")+1, claimParameters[2].length()-1);
			
			String[] dimensions = claimParameters[3].split("x");

			for (int i = 0; i < Integer.parseInt(dimensions[0]); i++) {
				for (int j = 0; j < Integer.parseInt(dimensions[1]); j++) {
					claimGrid[Integer.parseInt(topLeft[1])+j][Integer.parseInt(topLeft[0])+i]++;
				}
			}

			counter++;
		}

		counter = 0;

		while (counter < 1287) {
			claimOverlap[counter] = traceArray();
			if (!claimOverlap[counter]) {
				System.out.println(counter+1);
			}
			counter++;
		}

		sc.close();

	}

	public static boolean traceArray() {
		String claim = claimList[counter];
		String[] claimParameters = claim.split(" ");

		String[] topLeft = new String[2];
		topLeft[0] = claimParameters[2].substring(0, claimParameters[2].indexOf(","));
		topLeft[1] = claimParameters[2].substring(claimParameters[2].indexOf(",")+1, claimParameters[2].length()-1);
		
		String[] dimensions = claimParameters[3].split("x");

		for (int i = 0; i < Integer.parseInt(dimensions[0]); i++) {
			for (int j = 0; j < Integer.parseInt(dimensions[1]); j++) {
				if (claimGrid[Integer.parseInt(topLeft[1])+j][Integer.parseInt(topLeft[0])+i]>1) {
					return true;
				}
			}
		}

		return false;
	}
	
}
