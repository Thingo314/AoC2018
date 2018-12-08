import java.util.*;

public class Part2 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		System.out.println(getNodes(sc));
		sc.close();

	}

	public static int getNodes(Scanner scanner) {
		int sum = 0;
		ArrayList<Integer> values = new ArrayList<Integer>();
		int nextNodes = scanner.nextInt();
		int metadataEntries = scanner.nextInt();

		if (nextNodes == 0) {
			for (int i = 0; i < metadataEntries; i++) {
				sum += scanner.nextInt();
			}
		} else {
			for (int i = 0; i < nextNodes ; i++) {
				values.add(getNodes(scanner));
			}
			for (int i = 0; i < metadataEntries; i++) {
				int index = scanner.nextInt();
				if (index > 0 && index < values.size()+1) {
					sum += values.get(index-1);
				}
			}
		}
		return sum;
	}
	
}
