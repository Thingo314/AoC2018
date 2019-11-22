import java.util.*;
import java.util.regex.*;

public class Part1 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		ArrayList<ArrayList<Long>> bots = new ArrayList<>();
		int index = 0;
		int i = 0;
		long largestSize = 0;
		while (sc.hasNextLine()) {
			ArrayList<Long> bot = new ArrayList<>();

			String line = sc.nextLine();
			Pattern p = Pattern.compile("-?\\d+");
			Matcher m = p.matcher(line);

			while (m.find()) {
				bot.add(Long.parseLong(m.group()));
			}

			if (bot.get(bot.size() - 1) > largestSize) {
				largestSize = bot.get(bot.size() - 1);
				index = i;
			}

			bots.add(bot);
			i++;
		}

		ArrayList<Long> targetBot = bots.get(index);

		int inRange = 0;
		long range = targetBot.get(targetBot.size() - 1);

		for (int j = 0; j < bots.size(); j++) {
			if (manhattanDistance(targetBot, bots.get(j)) <= range)
				inRange++;
		}

		System.out.println(inRange);
		sc.close();
	}

	static long manhattanDistance(ArrayList<Long> a, ArrayList<Long> b) {
		if (a == null || b == null || a.size() != b.size()) {
			return -1;
		}

		long result = 0;

		for (int i = 0; i < a.size() - 1; i++) {
			result += Math.abs(a.get(i) - b.get(i));
		}
		return result;
	}

}
