import java.util.*;
import java.util.regex.*;

public class Day23 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		ArrayList<Bot> bots = new ArrayList<>();
		int index = 0;
		int i = 0;
		long largestSize = 0;

		long[] minimums = new long[3];
		Arrays.fill(minimums, Long.MAX_VALUE);
		long[] maximums = new long[3];
		Arrays.fill(maximums, Long.MIN_VALUE);

		Pattern p = Pattern.compile("-?\\d+");
		while (sc.hasNextLine()) {

			String line = sc.nextLine();
			Matcher m = p.matcher(line);

			ArrayList<Long> parameters = new ArrayList<>();
			while (m.find()) {
				parameters.add(Long.parseLong(m.group()));
			}

			Bot bot = new Bot(parameters);

			if (bot.r > largestSize) {
				largestSize = bot.r;
				index = i;
			}

			for (int j = 0; j < 3; j++) {
				minimums[j] = Math.min(minimums[j], bot.pos[j]);
				maximums[j] = Math.max(maximums[j], bot.pos[j]);
			}

			bots.add(bot);
			i++;
		}
		sc.close();

		Bot targetBot = bots.get(index);

		int inRange = 0;
		long range = targetBot.r;

		for (int j = 0; j < bots.size(); j++) {
			if (targetBot.manhattanDistance(bots.get(j)) <= range)
				inRange++;
		}

		System.out.println("Part 1: " + inRange);

		ArrayList<Long> initialParameters = new ArrayList<>();
		for (int j = 0; j < minimums.length; j++) {
			initialParameters.add(minimums[j]);
		}
		for (int j = 0; j < maximums.length; j++) {
			initialParameters.add(maximums[j]);
		}
		SearchRegion initialRegion = new SearchRegion(initialParameters);
		initialRegion.checkOverlapWith(bots);

		PriorityQueue<SearchRegion> pq = new PriorityQueue<>();
		pq.add(initialRegion);

		while (pq.size() != 0) {
			SearchRegion sr = pq.poll();

			if (sr.calculateSize() == 1){
				System.out.println("Part 2: " + sr.distanceToOrigin());
				break;
			}

			double[] halves = new double[3];
			for (int j = 0; j < 3; j++) {
				halves[j] = sr.max[j] + (double)sr.min[j];
				halves[j] /= 2;
			}

			for (int x = 0; x < 2; x++) {
				long newMinX = x == 0 ? sr.min[0] : (long) Math.ceil(halves[0]);
				long newMaxX = x == 0 ? (long) Math.floor(halves[0]) : sr.max[0];
				for (int y = 0; y < 2; y++) {
					long newMinY = y == 0 ? sr.min[1] : (long) Math.ceil(halves[1]);
					long newMaxY = y == 0 ? (long) Math.floor(halves[1]) : sr.max[1];
					for (int z = 0; z < 2; z++) {
						long newMinZ = z == 0 ? sr.min[2] : (long) Math.ceil(halves[2]);
						long newMaxZ = z == 0 ? (long) Math.floor(halves[2]) : sr.max[2];
						ArrayList<Long> newParameters = new ArrayList<>(
							Arrays.asList(newMinX, newMinY, newMinZ, newMaxX, newMaxY, newMaxZ));
						SearchRegion newRegion = new SearchRegion(newParameters);
						newRegion.checkOverlapWith(bots);
						if (newRegion.botsOverlapping != 0 && !pq.contains(newRegion))
								pq.add(newRegion);
					}
				}
			}

		}
	}
}

class Bot {
	public long[] pos = new long[3];
	public long r;

	public Bot(ArrayList<Long> p) {
		pos[0] = p.get(0);
		pos[1] = p.get(1);
		pos[2] = p.get(2);
		r = p.get(3);
	}

	public long manhattanDistance(Bot that) {
		return Math.abs(pos[0] - that.pos[0])
			+ Math.abs(pos[1] - that.pos[1])
			+ Math.abs(pos[2] - that.pos[2]);
	}

	@Override
	public String toString() {
		return "Bot at (" + pos[0] + ", " + pos[1] + ", " + pos[2]
			+ ") with range " + r;
	}
}

class SearchRegion implements Comparable<SearchRegion> {
	public long[] min = new long[3];
	public long[] max = new long[3];
	public long botsOverlapping = 0;

	public SearchRegion(ArrayList<Long> p) {
		min[0] = p.get(0);
		min[1] = p.get(1);
		min[2] = p.get(2);
		max[0] = p.get(3);
		max[1] = p.get(4);
		max[2] = p.get(5);
	}

	public long calculateSize() {
		long result = 1;
		for (int i = 0; i < 3; i++) {
			result *= max[i] - min[i] + 1;
		}
		return result;
	}

	public void checkOverlapWith(ArrayList<Bot> bots) {
		for (Bot b : bots) {
			checkOverlapWith(b);
		}
	}

	public void checkOverlapWith(Bot b) {
		long manhattanDistance = 0;
		for (int i = 0; i < 3; i++) {
			long d = Math.max(0, min[i] - b.pos[i]);
			d = Math.max(d, b.pos[i] - max[i]);
			manhattanDistance += d;
		}

		if (manhattanDistance <= b.r)
			botsOverlapping++;
	}

	@Override
	public int compareTo(SearchRegion that) {
		if (botsOverlapping > that.botsOverlapping)
			return -1;
		if (botsOverlapping < that.botsOverlapping)
			return 1;

		if (distanceToOrigin() < that.distanceToOrigin())
			return -1;
		if (distanceToOrigin() > that.distanceToOrigin())
			return 1;

		if (calculateSize() < that.calculateSize())
			return -1;
		if (calculateSize() > that.calculateSize())
			return 1;
		
		return 0;
	}

	public long distanceToOrigin() {
		long result = 0;
		for (int i = 0; i < 3; i++) {
			result += Math.max(min[i], -max[i]);
		}
		return result;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof SearchRegion))
			return false;

		SearchRegion that = (SearchRegion) o;

		return Arrays.equals(min, that.min)
			&& Arrays.equals(max, that.max)
			&& botsOverlapping == that.botsOverlapping;
	}

	@Override
	public String toString() {
		return "Region from (" + min[0] + ", " + min[1] + ", "+ min[2] + ") to ("
			+ max[0] + ", " + max[1] + ", "+ max[2]
			+ ") with " + botsOverlapping + " bot(s) overlapping";
	}
}
