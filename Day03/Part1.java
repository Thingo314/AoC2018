import java.util.*;

public class Part1 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		int[][] claims = new int[1000][1000];
		int overlaps = 0;

		while (sc.hasNextLine()) {
			String[] claimParameters = sc.nextLine().split(" ");

			String[] topLeft = new String[2];

			topLeft[0] = claimParameters[2].substring(0, claimParameters[2].indexOf(","));
			topLeft[1] = claimParameters[2].substring(claimParameters[2].indexOf(",")+1, claimParameters[2].length()-1);
			
			String[] dimensions = claimParameters[3].split("x");

			for (int i = 0; i < Integer.parseInt(dimensions[0]); i++) {
				for (int j = 0; j < Integer.parseInt(dimensions[1]); j++) {
					claims[Integer.parseInt(topLeft[1])+j][Integer.parseInt(topLeft[0])+i]++;
				}
			}

		}

		for (int i = 0; i < claims.length; i++) {
			for (int j = 0; j < claims[i].length; j++) {
				if (claims[i][j] >= 2) {
					overlaps++;
				}
			}
		}
		System.out.println(overlaps);

		sc.close();

	}
	
}