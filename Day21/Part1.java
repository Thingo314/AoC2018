import java.util.*;

public class Part1 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		String[] ipLine = sc.nextLine().split("\\s+");
		int ipRegister = Integer.parseInt(ipLine[1]);

		int[] register = new int[6];

		ArrayList<String> opCodes = new ArrayList<String>();

		while (sc.hasNextLine()) {
			opCodes.add(sc.nextLine());
		}

		while (register[ipRegister] < opCodes.size()) {
			String[] opCode = opCodes.get(register[ipRegister]).split("\\s+");
			String instruction = opCode[0];

			int value = 0;
			int aVal = Integer.parseInt(opCode[1]);
			int aReg = 0;
			if (aVal < 6) {
				aReg = register[aVal];
			}
			int bVal = Integer.parseInt(opCode[2]);
			int bReg = 0;
			if (bVal < 6) {
				bReg = register[bVal];
			}
			int cVal = Integer.parseInt(opCode[3]);

			switch (instruction) {
				case "addr":
					register[cVal] = aReg + bReg;
					break;

				case "addi":
					register[cVal] = aReg + bVal;
					break;

				case "mulr":
					register[cVal] = aReg * bReg;
					break;

				case "muli":
					register[cVal] = aReg * bVal;
					break;

				case "banr":
					register[cVal] = aReg & bReg;
					break;

				case "bani":
					register[cVal] = aReg & bVal;
					break;

				case "borr":
					register[cVal] = aReg | bReg;
					break;

				case "bori":
					register[cVal] = aReg | bVal;
					break;

				case "setr":
					register[cVal] = aReg;
					break;

				case "seti":
					register[cVal] = aVal;
					break;

				case "gtir":
					if (aVal > bReg) {
						value = 1;
					}
					register[cVal] = value;
					break;

				case "gtri":
					if (aReg > bVal) {
						value = 1;
					}
					register[cVal] = value;
					break;

				case "gtrr":
					if (aReg > bReg) {
						value = 1;
					}
					register[cVal] = value;
					break;

				case "eqir":
					if (aVal == bReg) {
						value = 1;
					}
					register[cVal] = value;
					break;

				case "eqri":
					if (aReg == bVal) {
						value = 1;
					}
					register[cVal] = value;
					break;

				case "eqrr":
					if (aReg == bReg) {
						value = 1;
					}
					register[cVal] = value;
					break;
			}

			register[ipRegister] = register[ipRegister]+1;
			System.out.println(Arrays.toString(register));

		}

		System.out.println(register[0]);

		sc.close();

	}

}
