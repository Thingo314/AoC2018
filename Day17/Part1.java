import java.util.*;

public class Part1 {

	static final int SOURCE = -1;
	static final int SPACE = 0;
	static final int WALL = 1;
	static final int WATER = 2;
	static final int WATERSTAT = 3;

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		int[][] grid = new int[1910][250];
		// rows are constant y, columns are constant x

		int minY = Integer.MAX_VALUE;
		int maxY = 0;

		grid[0][140] = SOURCE;
		// columns are offset by 360, water source at 0,500 is at 0,140

		while (sc.hasNextLine()) {
			String line = sc.nextLine();

			char constAxis = line.charAt(0);

			int numAtConstAxis = Integer.parseInt(line.substring(2, line.indexOf(",")));
			int numBeginVarAxis = Integer.parseInt(line.substring(line.indexOf("=", 5) + 1, line.indexOf("..")));
			int numEndVarAxis = Integer.parseInt(line.substring(line.indexOf("..") + 2));

			if (constAxis == 'x') {
				if (minY > numBeginVarAxis) {
					minY = numBeginVarAxis;
				}
				if (maxY < numEndVarAxis) {
					maxY = numEndVarAxis;
				}
			} else {
				if (minY > numAtConstAxis) {
					minY = numAtConstAxis;
				}
				if (maxY < numAtConstAxis) {
					maxY = numAtConstAxis;
				}
			}

			for (int i = numBeginVarAxis; i <= numEndVarAxis; i++) {
				if (constAxis == 'x') {
					grid[i][numAtConstAxis - 360] = WALL;
				} else {
					grid[numAtConstAxis][i - 360] = WALL;
				}
			}

		}
		sc.close();

		Deque<ArrayList<Integer>> stack = new ArrayDeque<>();
		stack.push(new ArrayList<>(Arrays.asList(0, 140)));

		while (stack.size() > 0) {
			ArrayList<Integer> coordinate = stack.pop();
			int x = coordinate.get(1);
			int y = coordinate.get(0);

			if (grid[y][x] == SOURCE) {
				if (grid[y + 1][x] == SPACE) {
					grid[y + 1][x] = WATER;
					stack.push(coordinate);
					stack.push(new ArrayList<>(Arrays.asList(y + 1, x)));
				}
			} else if (grid[y][x] == WATER) {
				if (y < grid.length - 1) {
					if (grid[y + 1][x] == SPACE) {
						grid[y][x] = SPACE;
						grid[y + 1][x] = WATER;
						stack.push(new ArrayList<>(Arrays.asList(y + 1, x)));
					} else if (grid[y + 1][x] == WALL || grid[y + 1][x] == WATERSTAT) {
						boolean addedWater = false;
						int waterAdded = 0;
						if (grid[y][x - 1] == SPACE) {
							grid[y][x - 1] = WATER;
							addedWater = true;
							waterAdded++;
							stack.push(new ArrayList<>(Arrays.asList(y, x - 1)));
						} else if (grid[y][x - 1] == WALL) {
							boolean foundWall = false;
							boolean stillWet = true;
							int cursor = x + 1;
							while (stillWet) {
								if (grid[y][cursor] == WATER) {
									cursor++;
								} else if (grid[y][cursor] == WALL) {
									foundWall = true;
									break;
								} else if (grid[y][cursor] == SPACE) {
									stillWet = false;
								}
							}
							if (foundWall) {
								for (int i = x; i < cursor; i++) {
									grid[y][i] = WATERSTAT;
								}
							}
						}
						if (grid[y][x + 1] == SPACE) {
							grid[y][x + 1] = WATER;
							addedWater = true;
							waterAdded++;
							stack.push(new ArrayList<>(Arrays.asList(y, x + 1)));
						} else if (grid[y][x + 1] == WALL) {
							boolean foundWall = false;
							boolean stillWet = true;
							int cursor = x - 1;
							while (stillWet) {
								if (grid[y][cursor] == WATER) {
									cursor--;
								} else if (grid[y][cursor] == WALL) {
									foundWall = true;
									break;
								} else if (grid[y][cursor] == SPACE) {
									stillWet = false;
								}
							}
							if (foundWall) {
								for (int i = cursor + 1; i <= x; i++) {
									grid[y][i] = WATERSTAT;
								}
							}
						}
						if (addedWater) {
							for (int i = 0; i < waterAdded; i++) {
								stack.add(stack.pop());
							}
							stack.push(coordinate);
							for (int i = 0; i < waterAdded; i++) {
								stack.push(stack.pollLast());
							}
						}
					}
				}
			}

		}

		int result = 0;
		for (int rowIndex = minY; rowIndex <= maxY; rowIndex++) {
			for (int colIndex = 0; colIndex< grid[rowIndex].length; colIndex++) {
				if (grid[rowIndex][colIndex] == WATERSTAT || grid[rowIndex][colIndex] == WATER) {
					result++;
				}
			}
		}

		System.out.println(result);

	}

}
