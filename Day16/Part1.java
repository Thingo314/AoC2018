import java.util.*;

public class Part1 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		int samples = 0;

		while (sc.hasNextLine()) {
			String regBefore = sc.nextLine();
			String[] regArrayBefore = regBefore.substring(9,regBefore.length()-1).split(", ");
			String[] opCode = sc.nextLine().trim().split("\\s+");
			String regAfter = sc.nextLine();
			String[] regArrayAfter = regAfter.substring(9,regAfter.length()-1).split(", ");
			sc.nextLine();

			int possibleOpCodes = 0;

			int aVal = Integer.parseInt(opCode[1]);
			int aReg = Integer.parseInt(regArrayBefore[aVal]);
			int bVal = Integer.parseInt(opCode[2]);
			int bReg = Integer.parseInt(regArrayBefore[bVal]);
			int cReg = Integer.parseInt(regArrayAfter[Integer.parseInt(opCode[3])]);

			//test addr
			if (cReg == aReg + bReg) {
				possibleOpCodes++;
			}

			//test addi
			if (cReg == aReg + bVal) {
				possibleOpCodes++;
			}

			//test mulr
			if (cReg == aReg * bReg) {
				possibleOpCodes++;
			}

			//test muli
			if (cReg == aReg * bVal) {
				possibleOpCodes++;
			}

			//test banr
			if (cReg == (aReg & bReg)) {
				possibleOpCodes++;
			}

			//test bani
			if (cReg == (aReg & bVal)) {
				possibleOpCodes++;
			}

			//test borr
			if (cReg == (aReg | bReg)) {
				possibleOpCodes++;
			}

			//test bori
			if (cReg == (aReg | bVal)) {
				possibleOpCodes++;
			}

			//test setr
			if (cReg == aReg) {
				possibleOpCodes++;
			}

			//test seti
			if (cReg == aVal) {
				possibleOpCodes++;
			}

			//test gtir
			if ((cReg == 1 && aVal > bReg) || (cReg == 0 && aVal <= bReg)) {
				possibleOpCodes++;
			}

			//test gtri
			if ((cReg == 1 && aReg > bVal) || (cReg == 0 && aReg <= bVal)) {
				possibleOpCodes++;
			}

			//test gtir
			if ((cReg == 1 && aReg > bReg) || (cReg == 0 && aReg <= bReg)) {
				possibleOpCodes++;
			}

			//test eqir
			if ((cReg == 1 && aVal == bReg) || (cReg == 0 && aVal != bReg)) {
				possibleOpCodes++;
			}

			//test eqri
			if ((cReg == 1 && aReg == bVal) || (cReg == 0 && aReg != bVal)) {
				possibleOpCodes++;
			}

			//test eqrr
			if ((cReg == 1 && aReg == bReg) || (cReg == 0 && aReg != bReg)) {
				possibleOpCodes++;
			}

			if (possibleOpCodes > 2) {
				samples++;
			}
		}

		System.out.println(samples);
		sc.close();

	}

}
