import java.util.*;

public class Day22 {

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		int depth = Integer.parseInt(sc.nextLine().substring(7));
		String[] target = sc.nextLine().substring(8).split(",");
		int targetX = Integer.parseInt(target[0]);
		int targetY = Integer.parseInt(target[1]);

		sc.close();

		int extraCells = 100;

		int[][] caveSystemGeoIndex = new int[targetY + extraCells][targetX + extraCells];
		int[][] caveSystemEroIndex = new int[targetY + extraCells][targetX + extraCells];

		for (int rowIndex = 0; rowIndex < caveSystemGeoIndex.length; rowIndex++) {
			for (int colIndex = 0; colIndex < caveSystemGeoIndex[rowIndex].length; colIndex++) {
				if (rowIndex == 0) {
					caveSystemGeoIndex[rowIndex][colIndex] = 16807 * colIndex;
				} else if (colIndex == 0) {
					caveSystemGeoIndex[rowIndex][colIndex] = 48271 * rowIndex;
				} else if (rowIndex == targetY && colIndex == targetX) {
					caveSystemGeoIndex[rowIndex][colIndex] = 0;
				} else {
					caveSystemGeoIndex[rowIndex][colIndex] = caveSystemEroIndex[rowIndex][colIndex-1]*caveSystemEroIndex[rowIndex-1][colIndex];
				}
				caveSystemEroIndex[rowIndex][colIndex] = ((caveSystemGeoIndex[rowIndex][colIndex]+depth)%20183);
			}
		}

		int risk = 0;

		for (int rowIndex = 0; rowIndex <= targetY; rowIndex++) {
			for (int colIndex = 0; colIndex <= targetX; colIndex++) {
				risk += caveSystemEroIndex[rowIndex][colIndex]%3;
			}
		}

		System.out.println("Part 1: " + risk);

		int[][][] distance = new int[targetY + extraCells][targetX + extraCells][3];
		for (int i = 0; i < distance.length; i++) {
			for (int j = 0; j < distance[0].length; j++) {
				for (int k = 0; k < distance[0][0].length; k++) {
					distance[i][j][k] = Integer.MAX_VALUE;
				}
			}
		}
		distance[0][0][1] = 0;

		HashSet<Node> settled = new HashSet<Node>();
		HashSet<Node> unsettled = new HashSet<Node>();

		Node origin = new Node(0, 0, 1);
		unsettled.add(origin);

		Node targetNode = new Node(targetX, targetY, 1);

		int[][] directions = {{1, 0}, {0, -1}, {-1, 0}, {0, 1}};

		while (unsettled.size() != 0) {
			Node node = getNextNode(unsettled, distance, targetNode);
			unsettled.remove(node);

			if (settled.contains(node))
				continue;

			settled.add(node);
			// System.out.println("chose " + node.toString());
			int currentDistance = distance[node.y][node.x][node.equipment];

			if (node.equals(targetNode))
				break;

			// check the other equipment in the current position
			int nodeTerrain = caveSystemEroIndex[node.y][node.x] % 3;
			int otherPossibleEquipment = 3 - nodeTerrain - node.equipment;

			Node otherNode = new Node(node.x, node.y, otherPossibleEquipment);
			if (!settled.contains(otherNode)) {
				unsettled.add(otherNode);
				int dist = node.distance(otherNode) + currentDistance;
				if (dist < distance[node.y][node.x][otherPossibleEquipment]) {
					distance[node.y][node.x][otherPossibleEquipment] = dist;
				}
			}

			// check the surrounding nodes and the distance from the current node
			for (int[] dir : directions) {
				int nextX = node.x + dir[0];
				int nextY = node.y + dir[1];

				if (nextX < 0 || nextY < 0 || nextY >= distance.length || nextX >= distance[0].length)
					continue;

				int nextNodeTerrain = caveSystemEroIndex[nextY][nextX] % 3;
				if (node.equipment == nextNodeTerrain)
					continue;

				Node nextNode = new Node(nextX, nextY, node.equipment);
				if (!settled.contains(nextNode)) {
					unsettled.add(nextNode);
					int dist = node.distance(nextNode) + currentDistance;
					if (dist < distance[nextY][nextX][node.equipment]) {
						distance[nextY][nextX][node.equipment] = dist;
					}
				}
			}
		}

		System.out.println("Part 2: " + distance[targetY][targetX][1]);
	}

	static Node getNextNode(HashSet<Node> set, int[][][] dist, Node t) {
		Node nextNode = null;
		int minDist = Integer.MAX_VALUE;
		int cost = Integer.MAX_VALUE;
		for (Node n : set) {
			if (nextNode == null) {
				minDist = dist[n.y][n.x][n.equipment];
				nextNode = n;
				cost = t.distance(n);
			} else if (minDist > dist[n.y][n.x][n.equipment]) {
				minDist = dist[n.y][n.x][n.equipment];
				nextNode = n;
				cost = t.distance(n);
			} else if (minDist == dist[n.y][n.x][n.equipment]) {
				int possibleCost = t.distance(n);
				if (cost > possibleCost) {
					nextNode = n;
					cost = t.distance(n);
				}
			}
		}
		return nextNode;
	}
}

class Node {
	public int x, y, equipment;
	int changeTime = 7;

	public Node(int x, int y, int equipment) {
		this.x = x;
		this.y = y;
		this.equipment = equipment;
	}

	public String toString() {
		return "(" + x + ", " + y + ") carrying " + equipment;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;

		if (!(o instanceof Node))
			return false;

		Node that = (Node) o;
		if (x != that.x)
			return false;
		if (y != that.y)
			return false;
		if (equipment != that.equipment)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, equipment);
	}

	public int distance(Node that) {
		return Math.abs(x - that.x) + Math.abs(y - that.y) + ((equipment == that.equipment) ? 0 : changeTime);
	}
}
