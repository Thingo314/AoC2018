// a disjoint set using integers which can be used as indexes pointing to other objects

public class DisjointSet {

	int[] ranks, parents;
	int setNum;

	public DisjointSet(int elementNum) {
		parents = new int[elementNum];
		ranks = new int[elementNum];
		setNum = elementNum;
		// intialise the disjoint set with elements in their own set
		for (int i = 0; i < elementNum; i++) {
			parents[i] = i;
		}
	}

	// returns parent of the set which has num
	int find(int num) {
		if (parents[num] != num) {
			parents[num] = find(parents[num]);
		}
		return parents[num];
	}

	// joins the sets that contain a and b
	void union(int a, int b) {
		int aParent = find(a), bParent = find(b);
		// checks if the two numbers are already in the same set
		if (aParent == bParent) {
			return;
		}

		// puts the set with the smaller height into the larger set
		if (ranks[aParent] < ranks[bParent]) {
			parents[aParent] = bParent;
		} else if (ranks[bParent] < ranks[aParent]) {
			parents[bParent] = aParent;
		} else {
			parents[bParent] = aParent;
			ranks[aParent]++;
		}
		setNum--;
	}

	int getSetNum() {
		return setNum;
	}

}
