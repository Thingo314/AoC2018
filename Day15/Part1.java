import java.util.*;

public class Part1 {

	public static Node[][] nodeGrid = new Node[32][32];
	public static List<Unit> unitList = new ArrayList<>();
	public static List<Unit> elfList = new ArrayList<>();
	public static List<Unit> gobList = new ArrayList<>();

	public static void main (String args[]) {

		Scanner sc = new Scanner(System.in);

		// populates the grid and adds elf and goblins
		// to their corresponding list when encountered
		int rowNum = 0;
		while (sc.hasNextLine()) {
			String row = sc.nextLine();
			for (int colNum = 0; colNum < row.length(); colNum++) {
				char gridSpace = row.charAt(colNum);

				// adds the nodes when there is an empty space
				// or an elf or goblin occupies the space
				if (gridSpace == '.') {
					nodeGrid[rowNum][colNum] = new Node(rowNum, colNum, false);
				} else if (gridSpace == 'G') {
					nodeGrid[rowNum][colNum] = new Node(rowNum, colNum, true);
					Unit goblin = new Unit(false, nodeGrid[rowNum][colNum]);
					unitList.add(goblin);
					gobList.add(goblin);
				} else if (gridSpace == 'E') {
					nodeGrid[rowNum][colNum] = new Node(rowNum, colNum, true);
					Unit elf = new Unit(true, nodeGrid[rowNum][colNum]);
					unitList.add(elf);
					elfList.add(elf);
				}

				// updates the adjacency when a node is created of the node itself
				// and nodes directly to the left and up
				Node curNode = nodeGrid[rowNum][colNum];
				if (curNode != null) {
					Node adjNode1 = nodeGrid[rowNum - 1][colNum];
					Node adjNode2 = nodeGrid[rowNum][colNum - 1];
					if (adjNode1 != null) {
						curNode.addAdjNode(adjNode1, 0);
						adjNode1.addAdjNode(curNode, 3);
					}
					if (adjNode2 != null) {
						curNode.addAdjNode(adjNode2, 1);
						adjNode2.addAdjNode(curNode, 2);
					}

				}
			}
			rowNum++;
		}
		sc.close();

		int roundCounter = 0;
		while (elfList.size() != 0 && gobList.size() != 0) {
			for (int unitIndex = 0; unitIndex < unitList.size(); unitIndex++) {
				Unit unit = unitList.get(unitIndex);
				int unitsOnBoard = unitList.size();

				if (nextToEnemy(unit)) {
					int indexOfAttackedUnit = attackInRange(unit);
					clearDeadEnemies();

					// ensures that no unit will take an extra turn or miss a turn
					if (unitsOnBoard > unitList.size() && unitIndex > indexOfAttackedUnit) {
						unitIndex--;
					}

					if (combatEnded()) {
						if (unitIndex >= unitList.size() - 1) {
							roundCounter++;
						}
						break;
					}
				} else {
					// Unit movement routine
					// finds all the empty spaces adjacent to an elf and a goblin then sets the correct list for the unit
					List<Node> nodesInRangeOfElf = new ArrayList<>();
					List<Node> nodesInRangeOfGob = new ArrayList<>();
					
					for (Unit goblin : gobList) {
						Node[] possibleAdj = goblin.position.adjNodes;
						for (Node node : possibleAdj) {
							if (node != null && !node.occupied && !nodesInRangeOfGob.contains(node)) {
								nodesInRangeOfGob.add(node);
							}
						}
					}

					for (Unit elf : elfList) {
						Node[] possibleAdj = elf.position.adjNodes;
						for (Node node : possibleAdj) {
							if (node != null && !node.occupied && !nodesInRangeOfElf.contains(node)) {
								nodesInRangeOfElf.add(node);
							}
						}
					}

					List<Node> nodesInRange;
					if (unit.isElf) {
						nodesInRange = nodesInRangeOfGob;
					} else {
						nodesInRange = nodesInRangeOfElf;
					}

					// moves the unit to the closest empty space
					if (nodesInRange.size() != 0) {
						Node sittingNode = unit.position;
						List<Node> path = bfs(sittingNode, nodesInRange);

						Node nodeMovedTo = path.get(0);

						sittingNode.occupied = false;
						nodeMovedTo.occupied = true;
						unit.position = nodeMovedTo;
					}

					// if in range of an enemy, attack
					if (nextToEnemy(unit)) {
						int indexOfAttackedUnit = attackInRange(unit);
						clearDeadEnemies();
						if (unitsOnBoard > unitList.size() && unitIndex > indexOfAttackedUnit) {
							unitIndex--;
						}
						if (combatEnded()) {
							if (unitIndex >= unitList.size() - 1) {
								roundCounter++;
							}
							break;
						}
					}
				}
				if (unitIndex >= unitList.size() - 1) {
					roundCounter++;
				}

			}
			unitList.sort(null);

		}

		int healthSum = 0;
		for (Unit unit : unitList) {
			healthSum += unit.health;
		}

		System.out.println(roundCounter*healthSum);
		
	}


	public static List<Node> bfs(Node start, List<Node> targets) {
		Deque<Node> queue = new ArrayDeque<>();
		queue.add(start);
		Node[][] previousNode = new Node[nodeGrid.length][nodeGrid[0].length];
		boolean[][] visited = new boolean[nodeGrid.length][nodeGrid[0].length];

		visited[start.row][start.col] = true;

		Node endedNode = null;
		boolean foundTarget = false;

		while (queue.size() != 0) {
			Node currentNode = queue.poll();

			if (targets.contains(currentNode)) {
				endedNode = currentNode;
				foundTarget = true;
				break;
			}

			for (Node n : currentNode.adjNodes) {
				if (n != null) {
					if (!visited[n.row][n.col] && !n.occupied) {
						queue.add(n);
						visited[n.row][n.col] = true;
						previousNode[n.row][n.col] = currentNode;
					}
				}
			}
		}

		List<Node> path = new ArrayList<>();
		if (foundTarget) {
			Node currentNode = endedNode;
			while (currentNode != start && currentNode != null) {
				path.add(0, currentNode);
				currentNode = previousNode[currentNode.row][currentNode.col];
			}
		} else {
			path.add(start);
		}
		return path;

	}

	public static boolean nextToEnemy(Unit unit) {
		for (Node node : unit.position.adjNodes) {
			if (node != null && node.occupied) {
				if (unit.isElf) {
					for (Unit goblin : gobList) {
						if (goblin.position == node) {
							return true;
						}
					}
				} else {
					for (Unit elf : elfList) {
						if (elf.position == node) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public static int attackInRange(Unit unit) {
		Unit unitToAttack = null;
		for (Node node : unit.position.adjNodes) {
			if (node != null && node.occupied) {
				if (unit.isElf) {
					for (Unit goblin : gobList) {
						if (goblin.position == node) {
							if (unitToAttack == null) {
								unitToAttack = goblin;
							} else {
								if (unitToAttack.health > goblin.health) {
									unitToAttack = goblin;
								}
							}
						}
					}
				} else {
					for (Unit elf : elfList) {
						if (elf.position == node) {
							if (unitToAttack == null) {
								unitToAttack = elf;
							} else {
								if (unitToAttack.health > elf.health) {
									unitToAttack = elf;
								}
							}
						}
					}
				}
			}
		}
		unitToAttack.health -= unit.attPower;
		return unitList.indexOf(unitToAttack);
	}

	public static void clearDeadEnemies() {
		Iterator clearer = gobList.iterator();
		while (clearer.hasNext()) {
			Unit goblin = (Unit) clearer.next();
			if (goblin.health <= 0) {
				goblin.position.occupied = false;
				clearer.remove();
				unitList.remove(goblin);
			}
		}
		clearer = elfList.iterator();
		while (clearer.hasNext()) {
			Unit elf = (Unit) clearer.next();
			if (elf.health <= 0) {
				elf.position.occupied = false;
				clearer.remove();
				unitList.remove(elf);
			}
		}
	}

	public static boolean combatEnded() {
		return (elfList.size() == 0 || gobList.size() == 0);
	}


}

class Node {
	public int row, col;
	public boolean occupied = false;
	public Node[] adjNodes = new Node[4];

	public Node (int row, int col, boolean occupied) {
		this.row = row;
		this.col = col;
		this.occupied = occupied;
	}

	public void addAdjNode(Node adjNode, int index) {
		// 0 - up, 1 - left, 2 - right, 3 - down
		adjNodes[index] = adjNode;
	}
}

class Unit implements Comparable<Unit> {
	public int health, attPower;
	public boolean isElf;
	public Node position;

	public Unit (boolean isElf, Node position) {
		this.health = 200;
		this.attPower = 3;
		this.isElf = isElf;
		this.position = position;
	}

	@Override
	public int compareTo(Unit otherUnit) {
		if (this.position.row < otherUnit.position.row) {
			return -1;
		} else if (this.position.row > otherUnit.position.row) {
			return 1;
		} else {
			if (this.position.col < otherUnit.position.col) {
				return -1;
			} else if (this.position.col > otherUnit.position.col) {
				return 1;
			}
		}
		return 0;
	}

}
