import java.util.*;

public class Day25 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);
		List<List<Integer>> stars = new ArrayList<>();

		while(sc.hasNextLine()) {
			List<Integer> star = new ArrayList<>();
			String[] starCoords = sc.nextLine().split(",");
			for (String coord : starCoords) {
				star.add(Integer.parseInt(coord));
			}
			stars.add(star);
		}
		sc.close();

		// System.out.println(stars);
		DisjointSet ds = new DisjointSet(stars.size());
		for (int i = 0; i < stars.size(); i++) {
			for (int j = i; j < stars.size(); j++) {
				if (findManHattDist(stars.get(i), stars.get(j)) <= 3) {
					ds.union(i,j);
				}
			}
		}

		System.out.printf("Part 1: %22d%n", ds.getSetNum());
		System.out.println("Part 2: Have a happy new year!");

	}

	static int findManHattDist(List<Integer> starA, List<Integer> starB) {
		if (starA.size() != starB.size()) {
			return -1;
		}
		int result = 0;
		for (int i = 0; i < starA.size(); i++) {
			result += Math.abs(starA.get(i) - starB.get(i));
		}
		return result;
	}

}
