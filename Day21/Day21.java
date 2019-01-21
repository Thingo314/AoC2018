import java.util.LinkedHashSet;

public class Day21 {

	public static void main (String args[]) {

		int[] registers = new int[6];
		LinkedHashSet<Integer> targets = new LinkedHashSet<>();
		boolean duplicateFound = false;

		while (!duplicateFound) {
			registers[2] = registers[1] | 65536;
			registers[1] = 6663054;

			registers[4] = registers[2] & 255;
			registers[1] += registers[4];
			registers[1] = registers[1] & 16777215;
			registers[1] *= 65899;
			registers[1] = registers[1] & 16777215;

			while (256 <= registers[2]) {
				registers[4] = 0;
				registers[3] = registers[4] + 1;
				registers[3] *= 256;

				while (registers[3] <= registers[2]) {
					registers[4]++;
					registers[3] = registers[4] + 1;
					registers[3] *= 256;
				}
				registers[2] = registers[4];

				registers[4] = registers[2] & 255;
				registers[1] += registers[4];
				registers[1] = registers[1] & 16777215;
				registers[1] *= 65899;
				registers[1] = registers[1] & 16777215;
			}

			duplicateFound = !targets.add(registers[1]);
		}

		System.out.printf("Part 1: %9d%n", targets.toArray()[0]);
		System.out.printf("Part 2: %9d%n", targets.toArray()[targets.size()-1]);

	}

}
