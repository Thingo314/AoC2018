import java.util.*;

public class Part2 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		HashMap<String, String> opCodeMapping = new HashMap<String, String>();

		String line = sc.nextLine();

		while (!line.equals("")) {
			String regBefore = line;
			String[] regArrayBefore = regBefore.substring(9, regBefore.length() - 1).split(", ");
			String[] opCode = sc.nextLine().trim().split("\\s+");
			String regAfter = sc.nextLine();
			String[] regArrayAfter = regAfter.substring(9, regAfter.length() - 1).split(", ");
			sc.nextLine();
			line = sc.nextLine();

			int numPossibleOpCodes = 0;

			int aVal = Integer.parseInt(opCode[1]);
			int aReg = Integer.parseInt(regArrayBefore[aVal]);
			int bVal = Integer.parseInt(opCode[2]);
			int bReg = Integer.parseInt(regArrayBefore[bVal]);
			int cReg = Integer.parseInt(regArrayAfter[Integer.parseInt(opCode[3])]);

			boolean[] possibleOpCodes = new boolean[16];

			//test addr
			if (cReg == aReg + bReg && !opCodeMapping.containsValue("addr")) {
				numPossibleOpCodes++;
				possibleOpCodes[0] = true;
			}
			//test addi
			if (cReg == aReg + bVal && !opCodeMapping.containsValue("addi")) {
				numPossibleOpCodes++;
				possibleOpCodes[1] = true;
			}
			//test mulr
			if (cReg == aReg * bReg && !opCodeMapping.containsValue("mulr")) {
				numPossibleOpCodes++;
				possibleOpCodes[2] = true;
			}
			//test muli
			if (cReg == aReg * bVal && !opCodeMapping.containsValue("muli")) {
				numPossibleOpCodes++;
				possibleOpCodes[3] = true;
			}
			//test banr
			if (cReg == (aReg & bReg) && !opCodeMapping.containsValue("banr")) {
				numPossibleOpCodes++;
				possibleOpCodes[4] = true;
			}
			//test bani
			if (cReg == (aReg & bVal) && !opCodeMapping.containsValue("bani")) {
				numPossibleOpCodes++;
				possibleOpCodes[5] = true;
			}
			//test borr
			if (cReg == (aReg | bReg) && !opCodeMapping.containsValue("borr")) {
				numPossibleOpCodes++;
				possibleOpCodes[6] = true;
			}
			//test bori
			if (cReg == (aReg | bVal) && !opCodeMapping.containsValue("bori")) {
				numPossibleOpCodes++;
				possibleOpCodes[7] = true;
			}
			//test setr
			if (cReg == aReg && !opCodeMapping.containsValue("setr")) {
				numPossibleOpCodes++;
				possibleOpCodes[8] = true;
			}
			//test seti
			if (cReg == aVal && !opCodeMapping.containsValue("seti")) {
				numPossibleOpCodes++;
				possibleOpCodes[9] = true;
			}
			//test gtir
			if (((cReg == 1 && aVal > bReg) || (cReg == 0 && aVal <= bReg)) && !opCodeMapping.containsValue("gtir")) {
				numPossibleOpCodes++;
				possibleOpCodes[10] = true;
			}
			//test gtri
			if (((cReg == 1 && aReg > bVal) || (cReg == 0 && aReg <= bVal)) && !opCodeMapping.containsValue("gtri")) {
				numPossibleOpCodes++;
				possibleOpCodes[11] = true;
			}
			//test gtrr
			if (((cReg == 1 && aReg > bReg) || (cReg == 0 && aReg <= bReg)) && !opCodeMapping.containsValue("gtrr")) {
				numPossibleOpCodes++;
				possibleOpCodes[12] = true;
			}
			//test eqir
			if (((cReg == 1 && aVal == bReg) || (cReg == 0 && aVal != bReg)) && !opCodeMapping.containsValue("eqir")) {
				numPossibleOpCodes++;
				possibleOpCodes[13] = true;
			}
			//test eqri
			if (((cReg == 1 && aReg == bVal) || (cReg == 0 && aReg != bVal)) && !opCodeMapping.containsValue("eqri")) {
				numPossibleOpCodes++;
				possibleOpCodes[14] = true;
			}
			//test eqrr
			if (((cReg == 1 && aReg == bReg) || (cReg == 0 && aReg != bReg)) && !opCodeMapping.containsValue("eqrr")) {
				numPossibleOpCodes++;
				possibleOpCodes[15] = true;
			}

			if (numPossibleOpCodes == 1) {
				int index = 0;
				for (int i = 0; i < possibleOpCodes.length; i++) {
					if (possibleOpCodes[i]) {
						index = i;
					}
				}
				switch (index) {
					case 0:
						opCodeMapping.put(opCode[0], "addr");
						break;

					case 1:
						opCodeMapping.put(opCode[0], "addi");
						break;

					case 2:
						opCodeMapping.put(opCode[0], "mulr");
						break;

					case 3:
						opCodeMapping.put(opCode[0], "muli");
						break;

					case 4:
						opCodeMapping.put(opCode[0], "banr");
						break;

					case 5:
						opCodeMapping.put(opCode[0], "bani");
						break;

					case 6:
						opCodeMapping.put(opCode[0], "borr");
						break;

					case 7:
						opCodeMapping.put(opCode[0], "bori");
						break;

					case 8:
						opCodeMapping.put(opCode[0], "setr");
						break;

					case 9:
						opCodeMapping.put(opCode[0], "seti");
						break;

					case 10:
						opCodeMapping.put(opCode[0], "gtir");
						break;

					case 11:
						opCodeMapping.put(opCode[0], "gtri");
						break;

					case 12:
						opCodeMapping.put(opCode[0], "gtrr");
						break;

					case 13:
						opCodeMapping.put(opCode[0], "eqir");
						break;

					case 14:
						opCodeMapping.put(opCode[0], "eqri");
						break;

					case 15:
						opCodeMapping.put(opCode[0], "eqrr");
						break;
				}
			}
		}

		sc.nextLine();
		int[] register = new int[4];

		while (sc.hasNextLine()) {
			String[] opCode = sc.nextLine().split("\\s+");
			String instruction = opCodeMapping.get(opCode[0]);
			
			int aVal = Integer.parseInt(opCode[1]);
			int aReg = register[aVal];
			int bVal = Integer.parseInt(opCode[2]);
			int bReg = register[bVal];
			int cVal = Integer.parseInt(opCode[3]);
			
			int value = 0;

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
					// int value = 0;
					if (aReg == bReg) {
						value = 1;
					}
					register[cVal] = value;
					break;
			}
		}

		System.out.println(register[0]);
		sc.close();

	}

}
