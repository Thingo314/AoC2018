import java.util.*;

public class Part1 {

	public static int sum = 0;

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		getNodes(sc, 1);

		System.out.println(sum);
		sc.close();

	}

	public static void getNodes(Scanner scanner, int numChildNodes) {
		for (int i = 0; i < numChildNodes; i++) {
			int childNodes = scanner.nextInt();
			int metadataEntries = scanner.nextInt();
			getNodes(scanner, childNodes);
			for (int j = 0; j < metadataEntries; j++) {
				sum += scanner.nextInt();
			}
		}
	}
	
}
