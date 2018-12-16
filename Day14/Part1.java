import java.util.*;

public class Part1 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		int numRecipes = sc.nextInt();

		sc.close();

		ArrayList<Integer> recipeBoard = new ArrayList<Integer>();
		recipeBoard.add(3);
		recipeBoard.add(7);

		int indexElfOne = 0;
		int indexElfTwo = 1;

		while (recipeBoard.size() < numRecipes + 10) {
			int valueOne = recipeBoard.get(indexElfOne);
			int valueTwo = recipeBoard.get(indexElfTwo);
			int sum = valueOne + valueTwo;

			for (char c : String.valueOf(sum).toCharArray()) {
				recipeBoard.add(Integer.parseInt(String.valueOf(c)));
			}

			indexElfOne = (indexElfOne + valueOne + 1) % recipeBoard.size();
			indexElfTwo = (indexElfTwo + valueTwo + 1) % recipeBoard.size();
		}

		List<Integer> subList = recipeBoard.subList(numRecipes, numRecipes+10);

		for (Integer i : subList) {
			System.out.print(i);
		}
		System.out.print("\n");

	}

}
