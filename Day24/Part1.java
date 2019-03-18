import java.util.*;

public class Part1 {

	static Comparator<Unit> initSort = new Comparator<Unit>() {
		public int compare(Unit u1, Unit u2) {
			int u1Init = u1.getInitiative();
			int u2Init = u2.getInitiative();
			if (u1Init > u2Init) {
				return -1;
			} else if (u1Init < u2Init) {
				return 1;
			}
			return 0;
		}
	};

	public static void main(String[] args) {

		ArrayList<Unit> immuneUnits = new ArrayList<>();
		ArrayList<Unit> infectorUnits = new ArrayList<>();

		Scanner sc = new Scanner(System.in);

		boolean takingImmuneUnits = true;
		sc.nextLine();
		while (sc.hasNextLine()) {
			String nextLine = sc.nextLine().replaceAll("[;,()]", "");
			if (nextLine.equals("")) {
				if (takingImmuneUnits) {
					continue;
				} else {
					break;
				}
			} else if (nextLine.equals("Infection:")) {
				takingImmuneUnits = false;
				continue;
			}

			ArrayList<String> unitDetails = new ArrayList<String>(Arrays.asList(nextLine.trim().split("\\s+")));
			int unitNum = Integer.parseInt(unitDetails.get(0));
			int hp = Integer.parseInt(unitDetails.get(4));
			int attDmg = Integer.parseInt(unitDetails.get(unitDetails.size() - 6));
			String attType = unitDetails.get(unitDetails.size() - 5);
			int init = Integer.parseInt(unitDetails.get(unitDetails.size() - 1));

			ArrayList<String> weaknesses = new ArrayList<>();
			ArrayList<String> immunities = new ArrayList<>();
			int indexOfWeaknesses = unitDetails.indexOf("weak");
			int indexOfImmunities = unitDetails.indexOf("immune");
			int indexOfEndOfMods = unitDetails.lastIndexOf("with");

			if (indexOfWeaknesses != -1) {
				int i = indexOfWeaknesses + 2;
				while (i != indexOfImmunities && i < indexOfEndOfMods) {
					weaknesses.add(unitDetails.get(i));
					i++;
				}
			}

			if (indexOfImmunities != -1) {
				int i = indexOfImmunities + 2;
				while (i != indexOfWeaknesses && i < indexOfEndOfMods) {
					immunities.add(unitDetails.get(i));
					i++;
				}
			}

			if (takingImmuneUnits) {
				immuneUnits.add(new Unit(unitNum, hp, weaknesses, immunities, attDmg, attType, init));
			} else {
				infectorUnits.add(new Unit(unitNum, hp, weaknesses, immunities, attDmg, attType, init));
			}
		}

		sc.close();

		while (immuneUnits.size() != 0 && infectorUnits.size() != 0) {
			ArrayList<Unit> armies = new ArrayList<>();
			armies.addAll(infectorUnits);
			armies.addAll(immuneUnits);
			armies.sort(null);

			@SuppressWarnings("unchecked")
			ArrayList<Unit> immuneTargets = (ArrayList<Unit>) immuneUnits.clone();
			@SuppressWarnings("unchecked")
			ArrayList<Unit> infectorTargets = (ArrayList<Unit>) infectorUnits.clone();

			HashMap<Unit, Unit> attackTargets = new HashMap<>();

			for (int i = 0; i < armies.size(); i++) {
				Unit selectingUnit = armies.get(i);

				ArrayList<Unit> enemyList;
				if (immuneUnits.contains(selectingUnit)) {
					enemyList = infectorTargets;
				} else {
					enemyList = immuneTargets;
				}

				Unit selectedTarget = null;
				int maxDmg = 0;

				for (int j = 0; j < enemyList.size(); j++) {
					Unit potentialUnit = enemyList.get(j);
					int potDmg = potentialUnit.getPotentialDmg(selectingUnit);
					if (maxDmg < potDmg) {
						maxDmg = potDmg;
						selectedTarget = potentialUnit;
					} else if (maxDmg == potDmg) {
						if (maxDmg == 0)
							continue;
						int selectedEffPow = selectedTarget.getEffectivePower();
						int potentialEffPow = potentialUnit.getEffectivePower();
						if (selectedEffPow < potentialEffPow) {
							selectedTarget = potentialUnit;
						} else if (selectedEffPow == potentialEffPow) {
							int selectedInit = selectedTarget.getInitiative();
							int potentialInit = potentialUnit.getInitiative();
							if (selectedInit < potentialInit) {
								selectedTarget = potentialUnit;
							}
						}
					}
				}
				if (selectedTarget != null) {
					attackTargets.put(selectingUnit, selectedTarget);
					enemyList.remove(selectedTarget);
				}

			}

			armies.sort(initSort);

			for (int i = 0; i < armies.size(); i++) {
				Unit attackingUnit = armies.get(i);
				if (attackingUnit.getUnits() > 0 && attackTargets.containsKey(attackingUnit)) {
					Unit attackedUnit = attackTargets.get(attackingUnit);
					attackedUnit.loseUnits(attackedUnit.getPotentialDmg(attackingUnit));
				}
			}

			for (int i = 0; i < immuneUnits.size(); i++) {
				if (immuneUnits.get(i).getUnits() <= 0) {
					immuneUnits.remove(i);
					i--;
				}
			}

			for (int i = 0; i < infectorUnits.size(); i++) {
				if (infectorUnits.get(i).getUnits() <= 0) {
					infectorUnits.remove(i);
					i--;
				}
			}

		}

		ArrayList<Unit> survivingUnits;
		if (immuneUnits.isEmpty()) {
			survivingUnits = infectorUnits;
		} else {
			survivingUnits = immuneUnits;
		}

		int result = 0;
		for (Unit unit : survivingUnits) {
			result += unit.getUnits();
		}
		System.out.println(result);

	}

}
