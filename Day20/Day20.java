import java.util.*;

public class Day20 {
	static ArrayList<Room> rooms = new ArrayList<Room>();

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String regex = sc.nextLine();
		sc.close();

		regex = regex.substring(1, regex.length() - 1);

		Room origin = new Room(0, 0);
		origin.setDistance(0);
		rooms.add(origin);

		ArrayDeque<Room> stack = new ArrayDeque<Room>();
		Room currentRoom = origin;

		for (char c : regex.toCharArray()) {
			switch (c) {
				case 'N':
					currentRoom = newRoom(currentRoom, 0, 1);
					break;
				case 'W':
					currentRoom = newRoom(currentRoom, -1, 0);
					break;
				case 'E':
					currentRoom = newRoom(currentRoom, 1, 0);
					break;
				case 'S':
					currentRoom = newRoom(currentRoom, 0, -1);
					break;
				case '(':
					stack.push(currentRoom);
					break;
				case ')':
					currentRoom = stack.pop();
					break;
				case '|':
					currentRoom = stack.peek();
				default:
					break;
			}
		}

		int maxDistance = 0;
		int roomsWithDistanceAbvThreshold = 0;
		int threshold = 1000;
		
		for (Room r : rooms) {
			int dist = r.getDistance();
			if (dist >= threshold)
				roomsWithDistanceAbvThreshold++;
			maxDistance = Math.max(maxDistance, dist);
		}

		System.out.println("Part 1: " + maxDistance);
		System.out.println("Part 2: " + roomsWithDistanceAbvThreshold);
	}

	static Room newRoom(Room currentRoom, int dx, int dy) {
		Room nextRoom = new Room(currentRoom.getX() + dx, currentRoom.getY() + dy);
		int index = rooms.indexOf(nextRoom);
		if (index != -1) {
			nextRoom = rooms.get(index);
		}

		nextRoom.setDistance(Math.min(nextRoom.getDistance(), currentRoom.getDistance() + 1));

		int directionOfNextRoom = 0;
		if (dx == 0) {
			if (dy == 1) {
				directionOfNextRoom = 0;
			} else if (dy == -1) {
				directionOfNextRoom = 3;
			}
		} else if (dy == 0) {
			if (dx == 1) {
				directionOfNextRoom = 2;
			} else if (dx == -1) {
				directionOfNextRoom = 1;
			}
		}

		currentRoom.addRoom(nextRoom, directionOfNextRoom);
		nextRoom.addRoom(currentRoom, 3 - directionOfNextRoom);

		if (index == -1) {
			rooms.add(nextRoom);
		}

		return nextRoom;
	}
}

class Room {
	int x, y;
	int distance = Integer.MAX_VALUE;
	Room[] adjRooms = new Room[4];

	public Room(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "Room(" + x + ", " + y + ")";
	}

	public void addRoom(Room nextRoom, int direction) {
		// connects a new room in the direction from this room
		// 0 - up, 1 - left, 2 - right, 3 - down
		adjRooms[direction] = nextRoom;
	}

	public int getDistance() {
		return distance;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Room[] getAdjRooms() {
		return adjRooms;
	}

	public void setDistance(int d) {
		if (d >= 0)
			distance = d;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Room))
			return false;
		Room that = (Room) o;
		if (this.x != that.getX())
			return false;
		if (this.y != that.getY())
			return false;
		return true;
	}
}
