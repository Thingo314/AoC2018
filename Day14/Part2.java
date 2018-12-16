import java.util.*;

public class Part2 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		int numRecipes = sc.nextInt();

		sc.close();

		ArrayList<Integer> recipeList = new ArrayList<Integer>();

		for (char c : String.valueOf(numRecipes).toCharArray()) {
			recipeList.add(Integer.parseInt(String.valueOf(c)));
		}

		ArrayList<Integer> recipeBoard = new ArrayList<Integer>();
		recipeBoard.add(3);
		recipeBoard.add(7);

		int indexElfOne = 0;
		int indexElfTwo = 1;

		for (int i = 0; i < numRecipes*25; i++) {
			int valueOne = recipeBoard.get(indexElfOne);
			int valueTwo = recipeBoard.get(indexElfTwo);
			int sum = valueOne + valueTwo;

			for (char c : String.valueOf(sum).toCharArray()) {
				recipeBoard.add(Integer.parseInt(String.valueOf(c)));
			}

			indexElfOne = (indexElfOne + valueOne + 1) % recipeBoard.size();
			indexElfTwo = (indexElfTwo + valueTwo + 1) % recipeBoard.size();
		}

		int indexOfRecipeList = Collections.indexOfSubList(recipeBoard, recipeList);

		System.out.println(indexOfRecipeList);

	}

}
