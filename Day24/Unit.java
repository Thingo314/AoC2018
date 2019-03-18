import java.util.ArrayList;

public class Unit implements Comparable<Unit> {
	private int units, hitPoints, attackDamage, initiative;
	private String attackType;
	private ArrayList<String> weaknesses, immunities;

	public Unit(int unitNum, int hp, ArrayList<String> weakness, ArrayList<String> immunity, int attDmg, String attType, int init) {
		units = unitNum;
		hitPoints = hp;
		weaknesses = weakness;
		immunities = immunity;
		attackDamage = attDmg;
		attackType = attType;
		initiative = init;
	}

	public void addBoost(int boost) {
		attackDamage += boost;
	}

	public Unit clone() {
		return new Unit(units, hitPoints, weaknesses, immunities,attackDamage, attackType, initiative);
	}

	@Override
	public int compareTo(Unit o) {
		int myEffPower = getEffectivePower();
		int theirEffPower = o.getEffectivePower();
		if (myEffPower > theirEffPower) {
			return -1;
		} else if (myEffPower < theirEffPower) {
			return 1;
		}

		int myInitiative = getInitiative();
		int theirInitiative = o.getInitiative();
		if (myInitiative > theirInitiative) {
			return -1;
		} else if (myInitiative < theirInitiative) {
			return 1;
		}
		return 0;
	}

	public String getAttType() {
		return attackType;
	}

	public int getEffectivePower() {
		return units * attackDamage;
	}

	public int getInitiative() {
		return initiative;
	}

	public int getPotentialDmg(Unit attUnit) {
		if (isImmuneTo(attUnit.getAttType()))
			return 0;

		int dmgMul = 1;
		int potDmg = attUnit.getEffectivePower();

		if (isWeakTo(attUnit.getAttType())) {
			dmgMul = 2;
		}

		potDmg = (potDmg * dmgMul);

		return potDmg;
	}

	public int getUnits() {
		return units;
	}

	boolean isImmuneTo(String attType) {
		return immunities.contains(attType);
	}

	boolean isWeakTo(String attType) {
		return weaknesses.contains(attType);
	}

	public int loseUnits(int dmg) {
		int unitsKilled = Math.min(dmg / hitPoints, units);
		units -= (dmg / hitPoints);
		if (units < 0) {
			units = 0;
		}
		return unitsKilled;
	}

	public String toString() {
		String name = String.valueOf(initiative);
		return name;
	}


}
