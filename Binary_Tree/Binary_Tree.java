package Binary_Tree;

import java.util.*;

public class Binary_Tree {

	private class Node {
		int data;
		Node right;
		Node left;
	}

	private Node root;
	@SuppressWarnings("unused")
	private int size = 0;

	public Binary_Tree(int[] arr) {

		Stack<Node> stack = new Stack<>();

		for (int val : arr)
			if (val == -1) {

				stack.pop();

			} else {
				Node node = new Node();

				node.data = val;

				size++;

				if (stack.size() == 0) {

					root = node;

				}
				if (stack.size() > 0) {
					if (stack.peek().left == null) {

						stack.peek().left = node;

					} else {

						stack.peek().right = node;

					}
				}

				stack.push(node);
			}

	}

	public void display() {

		display(root);

	}

	private void display(Node node) {

		if (node == null) {
			return;
		}
		String str = new String();

		str += node.left != null ? node.left.data : ".";

		str += "<- " + node.data + " -> ";

		str += node.right != null ? node.right.data : ".";

		System.out.println(str);

		display(node.left);
		display(node.right);

	}

	public int size() {
		return size(root);
	}

	private int size(Node node) {

		if (node == null) {
			return 0;
		}

		int lsize = size(node.left);

		int rsize = size(node.right);
		return lsize + rsize + 1;

	}

	public int max() {
		return max(root);
	}

	private int max(Node node) {

		if (node == null) {
			return Integer.MIN_VALUE;
		}

		int l = max(node.left);
		int r = max(node.right);

		return Math.max(node.data, Math.max(l, r));

	}

	public int height() {
		return height(root);

	}

	private int height(Node node) {

		if (node == null) {
			return 0;
		}
		int lh = height(node.left);
		int rh = height(node.right);

		int h = 1 + Math.max(lh, rh);

		return h;
	}

	public boolean find(int data) {
		return find(root, data);
	}

	private boolean find(Node node, int data) {

		if (node == null) {
			return false;
		}

		if (node.data == data) {
			return true;
		}

		boolean foundL = find(node.left, data);

		if (foundL) {
			return true;
		}
		boolean foundR = find(node.right, data);
		boolean found = foundL || foundR;

		return found;
	}

	public ArrayList<Integer> nodeToRootPath(int data) {

		return nodeToRootPath(root, data);

	}

	private ArrayList<Integer> nodeToRootPath(Node node, int data) {

		if (node == null) {
			ArrayList<Integer> bres = new ArrayList<>();
			return bres;
		}

		if (node.data == data) {
			ArrayList<Integer> list = new ArrayList<>();

			list.add(node.data);
			return list;
		}

		ArrayList<Integer> res = nodeToRootPath(node.left, data);

		if (res.size() > 0) {
			res.add(node.data);
			return res;
		}

		res = nodeToRootPath(node.right, data);

		if (res.size() > 0) {
			res.add(node.data);
			return res;
		}

		return res;

	}

	public ArrayList<Integer> NodeToNodePath(int i, int j) {

		ArrayList<Integer> list1 = nodeToRootPath(i);

		ArrayList<Integer> list2 = nodeToRootPath(j);

		int t1 = list1.size() - 1;
		int t2 = list2.size() - 1;

		int lca_val = 0;

		while (t1 > 0 && t2 > 0) {
			if (list1.get(t1) != list2.get(t2)) {
				lca_val = list1.get(t1 + 1);
				break;
			}
			t1--;
			t2--;
		}

		ArrayList<Integer> ans = new ArrayList<Integer>();

		for (int k = 0; k < list1.size(); k++) {

			if (list1.get(k) != lca_val) {
				ans.add(list1.get(k));
			} else {
				break;
			}

		}

		ans.add(lca_val);

		boolean trigger = false;

		for (int k = list2.size() - 1; k >= 0; k--) {

			if (trigger) {
				ans.add(list2.get(k));
			}

			if (list2.get(k) == lca_val) {
				trigger = true;
			}

		}

		return ans;

	}

	public int lca(int i, int j) {

		return lca(root, i, j);

	}

	public int lca(Node node, int i, int j) {

		ArrayList<Integer> p1 = nodeToRootPath(i);

		ArrayList<Integer> p2 = nodeToRootPath(j);

		int a = p1.size() - 1;

		int b = p2.size() - 1;

		while (a >= 0 && b >= 0) {

			if (p1.get(a) != p2.get(b)) {

				return p1.get(a + 1);

			}

			a--;
			b--;

		}

		return -1;

	}

	public int lca_better(int i, int j) {

		return lca_better(root, findNode(i), findNode(j)).data;

	}

	private Node lca_better(Node node, Node p, Node q) {

		if (node == null) {
			return null;
		}

		if (node == p || node == q) {
			return node;
		}

		Node left = lca_better(node.left, p, q);
		Node right = lca_better(node.right, p, q);

		if (left != null && right != null) {
			return node;
		}

		return left != null ? left : right;

	}

	public Node lca_better_bst(Node a, Node b) {

		return lca_better_bst(root, a, b);
	}

	private Node lca_better_bst(Node node, Node a, Node b) {

		if (node.data > a.data && node.data > b.data) {
			lca_better_bst(node.left, a, b);
		}

		if (node.data < a.data && node.data < b.data) {
			lca_better_bst(node.right, a, b);
		}

		return node;

	}

	public int distanceBWnode(int i, int j) {
		ArrayList<Integer> iList = nodeToRootPath(i);

		ArrayList<Integer> jList = nodeToRootPath(j);

		int x = lca(i, j);

		ArrayList<Integer> xList = nodeToRootPath(x);

		return ((iList.size() + jList.size()) - 2 * xList.size());
	}

	public Node findNode(int data) {
		return findNode(root, data);
	}

	private Node findNode(Node node, int data) {

		if (node == null) {
			return null;
		}

		if (node.data == data) {
			return node;
		}

		Node foundL = findNode(node.left, data);

		if (foundL != null) {
			return foundL;
		}
		Node foundR = findNode(node.right, data);

		if (foundR != null) {
			return foundR;
		}
		return null;

	}

	public void KDown(int data, int k) {

		Node node = findNode(root, data);

		KDown(node, k, 0);
	}

	private void KDown(Node node, int k, int i) {

		if (node == null) {
			return;
		}

		if (i == k) {
			System.out.println(node.data);
			return;
		}

		KDown(node.left, k, i + 1);
		KDown(node.right, k, i + 1);

	}

	private ArrayList<Node> nodeToRootPath_Node(Node node, int data) {

		if (node == null) {
			ArrayList<Node> bres = new ArrayList<>();
			return bres;
		}

		if (node.data == data) {
			ArrayList<Node> list = new ArrayList<>();
			list.add(node);
			return list;
		}

		ArrayList<Node> res = nodeToRootPath_Node(node.left, data);

		if (res.size() > 0) {
			res.add(node);
			return res;
		}

		res = nodeToRootPath_Node(node.right, data);

		if (res.size() > 0) {
			res.add(node);
			return res;
		}

		return res;

	}

	public void printKFar(int data, int k) {

		ArrayList<Node> path = nodeToRootPath_Node(root, data);

		KDown(path.get(0), k, 0);

		for (int i = 1; i <= k - 1; i++) {

			Node imnode = path.get(i - 1);

			Node inode = path.get(i);

			if (inode.left == imnode) {
				KDown(inode.right, k - i - 1, 0);

			} else {
				KDown(inode.left, k - i - 1, 0);
			}

		}

	}

	public void printKfarBetter(int data, int k) {
		printKfarBetter(root, data, k);
	}

	static int dist = 0;

	private boolean printKfarBetter(Node node, int data, int k) {
		if (node == null) {
			return false;

		} else if (node.data == data) {
			kDownBlocker(node, k, null);
			return true;

		} else if (printKfarBetter(node.left, data, k)) {
			kDownBlocker(node, k - dist, node.left);
			dist++;
			return true;

		} else if (printKfarBetter(node.right, data, k)) {
			kDownBlocker(node, k - dist, node.right);
			dist++;
			return true;

		}
		return false;
	}

	private void kDownBlocker(Node node, int k, Node blocker) {
		if (node == null || blocker == node || k < 0) {
			return;
		}

		if (k == 0) {
			System.out.println(node.data);
		}

		kDownBlocker(node.left, k - 1, blocker);
		kDownBlocker(node.right, k - 1, blocker);
	}

	Node head = new Node();

	static Node prev = null;

	public void BTtoDLL() {

		BTtoDLL(root);

		System.out.println(head.data);

		printDLL(head);
	}

	private void BTtoDLL(Node node) {

		if (node == null) {
			return;
		}

		BTtoDLL(node.left);
		if (prev == null) {
			head = node;
		} else {
			prev.right = node;
			node.left = prev;
		}
		prev = node;

		BTtoDLL(node.right);

	}

	private void printDLL(Node node) {

		while (node != null) {
			System.out.print(node.data + " -> ");
			node = node.right;
		}

		System.out.println();

	}

	public void sortedLLtoBST(Node head) {
		this.root = toBST(head, null);

		display(this.root);
	}

	private Node toBST(Node head, Node tail) {

		if (head == tail) {
			return null;
		}
		Node mid = MidNode(head, tail);

		Node node = mid;

		node.left = toBST(head, mid);

		node.right = toBST(mid.right, tail);

		return node;
	}

	private Node MidNode(Node node, Node tail) {

		Node slow = node;
		Node fast = node;

		while (fast != tail && fast.right != tail) {
			fast = fast.right.right;
			slow = slow.right;
		}
		return slow;
	}

	public void removeLeaves() {
		removeLeaves(root);
	}

	private Node removeLeaves(Node node) {

		if (node.left == null && node.right == null) {

			return null;
		}

		node.left = removeLeaves(node.left);
		node.right = removeLeaves(node.right);

		return node;

	}

	public void printSinglechild() {
		printSinglechild(root);
	}

	private void printSinglechild(Node node) {

		if (node.left == null && node.right != null) {

			System.out.println(node.data);
			return;

		}

		if (node.right == null && node.left != null) {

			System.out.println(node.data);
			return;
		}

		printSinglechild(node.left);

		printSinglechild(node.right);

	}

	static int sum = 0;

	public void printInRange(int lo, int hi) {

		printInRange(root, lo, hi, 0, "");

	}

	private void printInRange(Node node, int lo, int hi, int sum, String str) {

		if (node == null) {

			if (sum >= lo && sum <= hi) {
				System.out.println(str);
			}
			return;
		}

		if (sum >= lo && sum <= hi) {
			System.out.println(str);
		}

		sum += node.data;

		printInRange(node.left, lo, hi, sum, str + " " + node.data);

		printInRange(node.right, lo, hi, sum, str + " " + node.data);

		sum -= node.data;
	}

	public void levelorder() {
		levelorder(root);
	}

	private void levelorder(Node node) {
		LinkedList<Node> queue = new LinkedList<>();

		queue.add(node);

		while (queue.size() > 0) {

			Node temp = queue.removeFirst();

			System.out.print(temp.data + "  ");

			if (temp.left != null) {
				queue.addLast(temp.left);
			}

			if (temp.right != null) {
				queue.addLast(temp.right);
			}

		}

	}

	public void levelorder_lineWise_NULL() {

		levelorder_lineWise_NULL(root);

	}

	private void levelorder_lineWise_NULL(Node node) {

		LinkedList<Node> queue = new LinkedList<>();

		queue.add(node);
		queue.add(null);

		while (queue.size() > 0) {
			Node temp = queue.removeFirst();

			if (temp != null) {

				System.out.print(temp.data + "  ");
				if (temp.left != null) {
					queue.addLast(temp.left);
				}

				if (temp.right != null) {
					queue.addLast(temp.right);
				}

			} else {
				if (queue.size() == 0) {
					return;
				}
				System.out.println();
				queue.addLast(null);
			}

		}

	}

	public void levelorder_linewise_TWO_QUEUE() {

		levelorder_linewise_TWO_QUEUE(root);

	}

	public void levelorder_linewise_TWO_QUEUE(Node node) {
		LinkedList<Node> que1 = new LinkedList<Node>();
		LinkedList<Node> que2 = new LinkedList<Node>();

		que1.addLast(root);

		while (!que1.isEmpty()) {

			Node node1 = que1.removeFirst();

			System.out.print(node1.data + " ");

			if (node1.left != null) {
				que2.addLast(node1.left);
			}
			if (node1.right != null) {
				que2.addLast(node1.right);
			}

			if (que1.isEmpty()) {
				que1 = que2;
				que2 = new LinkedList<>();
				System.out.println();
			}
		}
	}

	public void zigzag() {

		boolean ltr = true;

		Stack<Node> curr = new Stack<>();
		Stack<Node> next = new Stack<>();

		curr.add(root);

		while (!curr.isEmpty()) {

			Node temp = curr.pop();

			System.out.print(temp.data + " ");

			if (ltr) {
				if (temp.left != null) {
					next.push(temp.left);
				}

				if (temp.right != null) {
					next.push(temp.right);
				}
			} else {

				if (temp.right != null) {
					next.push(temp.right);
				}
				if (temp.left != null) {
					next.push(temp.left);
				}

			}

			if (curr.isEmpty()) {

				System.out.println();
				ltr = !ltr;

				curr = next;
				next = new Stack<Binary_Tree.Node>();

			}
		}

	}

	public void printleftView() {
		leftView(root, 0);
	}

	static int max_level = -1;

	private void leftView(Node node, int level) {
		if (node == null) {
			return;
		}
		if (level > max_level) {
			System.out.print(node.data + " ");
			max_level = level;
		}

		leftView(node.left, level + 1);
		leftView(node.right, level + 1);

	}

	public void printRightView() {
		rightview(root, 0);
	}

	static int max_level_R = -1;

	private void rightview(Node node, int level) {

		if (node == null) {
			return;
		}

		if (level > max_level_R) {
			System.out.print(node.data + " ");
			max_level_R = level;
		}

		rightview(node.right, level + 1);
		rightview(node.left, level + 1);

	}

	static HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();

	public void printBottomView() {

		printBottomView(root, 0);

		System.out.println(map.values());
	}

	public void printBottomView(Node node, int hd) {

		if (node == null) {
			return;
		}

		map.put(hd, node.data);

		printBottomView(node.left, hd - 1);
		printBottomView(node.right, hd + 1);

	}

	static HashMap<Integer, Integer> map2 = new HashMap<Integer, Integer>();

	public void printTopView() {

		printTopView(root, 0);

		System.out.println(map2.values());
	}

	public void printTopView(Node node, int hd) {

		if (node == null) {
			return;
		}

		if (!map2.containsKey(hd)) {

			map2.put(hd, node.data);

		}

		printTopView(node.left, hd - 1);
		printTopView(node.right, hd + 1);

	}

	public void diameter() {

		System.out.println(diameter(root));

	}

	static int dia = 0;

	private int diameter(Node node) {

		if (node == null) {
			return 0;
		}

		int lh = height(node.left);
		int rh = height(node.right);

		int ld = diameter(node.left);
		int rd = diameter(node.right);

		return Math.max(lh + rh + 2, Math.max(ld, rd));

	}

	class DiaPair {
		int height;
		int dia;
	}

	public int diameter_Better() {

		DiaPair dp = diameter_Better(root);

		return dp.dia;

	}

	private DiaPair diameter_Better(Node node) {

		if (node == null) {
			DiaPair bres = new DiaPair();
			bres.height = -1;
			bres.dia = 0;
			return bres;
		}

		DiaPair lp = diameter_Better(node.left);
		DiaPair rp = diameter_Better(node.right);

		DiaPair mp = new DiaPair();

		mp.height = Math.max(lp.height, rp.height) + 1;
		mp.dia = Math.max((lp.height + rp.height) + 2, Math.max(lp.dia, rp.dia));

		return mp;
	}

	class BalPair {
		Boolean isbalanced;
		int height;
	}

	public boolean isBalanced() {
		return isBalanced(root).isbalanced;
	}

	private BalPair isBalanced(Node node) {
		if (node == null) {
			BalPair bp = new BalPair();
			bp.height = -1;
			bp.isbalanced = true;
			return bp;
		}

		BalPair lb = isBalanced(node.left);
		BalPair rb = isBalanced(node.right);

		BalPair mypair = new BalPair();

		mypair.isbalanced = lb.isbalanced && rb.isbalanced && Math.abs(lb.height - rb.height) <= 1;
		mypair.height = Math.max(lb.height, rb.height) + 1;

		return mypair;

	}

	class BSTPairs {
		boolean isBST;
		int max;
		int min;
	}

	public boolean isBST() {

		return isBST(root).isBST;
	}

	private BSTPairs isBST(Node node) {

		if (node == null) {
			BSTPairs bp = new BSTPairs();
			bp.isBST = true;
			bp.max = Integer.MIN_VALUE;
			bp.min = Integer.MAX_VALUE;

			return bp;
		}

		BSTPairs lp = isBST(node.left);
		BSTPairs rp = isBST(node.right);

		BSTPairs mypair = new BSTPairs();

		mypair.isBST = lp.isBST && rp.isBST && node.data <= lp.max && node.data > rp.min;
		mypair.max = Math.max(node.data, Math.max(lp.max, rp.max));
		mypair.min = Math.min(node.data, Math.min(lp.min, rp.min));

		return mypair;

	}

	class LargestBSTPairs {
		boolean isBST;
		int max;
		int min;
		int Lbst_size;
		Node Lbst_root;
	}

	public void isLargestBST() {

		LargestBSTPairs pair = isLargestBST(root);

		System.out.println(pair.Lbst_root + " " + pair.Lbst_size);
	}

	private LargestBSTPairs isLargestBST(Node node) {

		if (node == null) {
			LargestBSTPairs bp = new LargestBSTPairs();
			bp.isBST = true;
			bp.max = Integer.MIN_VALUE;
			bp.min = Integer.MAX_VALUE;
			bp.Lbst_size = 0;

			return bp;
		}

		LargestBSTPairs lp = isLargestBST(node.left);
		LargestBSTPairs rp = isLargestBST(node.right);

		LargestBSTPairs mypair = new LargestBSTPairs();

		mypair.isBST = lp.isBST && rp.isBST && node.data >= lp.max && node.data < rp.min;
		mypair.max = Math.max(node.data, Math.max(lp.max, rp.max));
		mypair.min = Math.min(node.data, Math.min(lp.min, rp.min));

		if (mypair.isBST) {
			mypair.Lbst_root = node;
			mypair.Lbst_size = mypair.Lbst_size + rp.Lbst_size + 1;
		} else {
			if (lp.Lbst_size > rp.Lbst_size) {
				mypair.Lbst_root = lp.Lbst_root;
				mypair.Lbst_size = lp.Lbst_size;
			} else {
				mypair.Lbst_root = rp.Lbst_root;
				mypair.Lbst_size = rp.Lbst_size;
			}
		}
		return mypair;

	}

	public void inroderSucessor(int data) {

		inroderSucessor(root, data);

		if (trigger) {
			System.out.println(" No inorder Successor ");
		}

	}

	static Boolean trigger = false;

	private void inroderSucessor(Node node, int data) {

		if (node == null) {
			return;
		}

		inroderSucessor(node.left, data);

		if (trigger) {
			System.out.println(node.data);
			trigger = false;
		}

		if (data == node.data) {
			trigger = true;

		}

		inroderSucessor(node.right, data);

	}

	class levelPair {
		int sum;
		int n;
	}

	static HashMap<Integer, levelPair> avg = new HashMap<>();

	public void levelAvg() {
		levelAvg(root, 0);

		for (levelPair pair : avg.values()) {

			float ans = (float) (pair.sum / pair.n);

			System.out.println(ans);

		}

	}

	private void levelAvg(Node node, int vc) {

		if (node == null) {
			return;
		}

		if (avg.containsKey(vc)) {

			levelPair pair = avg.get(vc);
			pair.sum += node.data;
			pair.n++;

			avg.put(vc, pair);

		} else {
			levelPair pair = new levelPair();
			pair.sum = node.data;
			pair.n++;

			avg.put(vc, pair);
		}

		levelAvg(node.left, vc + 1);
		levelAvg(node.right, vc + 1);

	}

	public void levelAvg_LevelOrder() {

		LinkedList<Node> queue = new LinkedList<>();

		queue.addFirst(root);

		while (!queue.isEmpty()) {
			int n = queue.size();

			Double sum = 0.0;

			for (int i = 0; i < n; i++) {
				Node temp = queue.remove();

				sum += temp.data;

				if (temp.left != null) {
					queue.addFirst(temp.left);
				}

				if (temp.right != null) {
					queue.addFirst(temp.right);
				}
			}

			System.out.println(sum / n);
		}

	}

	public void ExtremeEndAlternative() {

		LinkedList<Node> curr = new LinkedList<>();
		LinkedList<Node> next = new LinkedList<>();

		boolean trigger = true;

		boolean print = true;

		curr.add(root);

		while (!curr.isEmpty()) {

			Node temp = curr.pop();

			if (print) {
				System.out.print(temp.data + " ");
				print = !print;
			}

			if (trigger) {
				if (temp.left != null) {
					next.push(temp.left);
				}

				if (temp.right != null) {
					next.push(temp.right);
				}
			} else {

				if (temp.right != null) {
					next.push(temp.right);
				}
				if (temp.left != null) {
					next.push(temp.left);
				}

			}

			if (curr.isEmpty()) {

				LinkedList<Node> list = curr;
				curr = next;
				next = list;

				trigger = !trigger;
				print = !print;

			}
		}
	}

	public void printCommon(Binary_Tree bt) {

		Node root = this.root;
		Node root2 = root;

		Stack<Node> s = new Stack<Binary_Tree.Node>();
		Stack<Node> s2 = new Stack<Binary_Tree.Node>();

		while (true) {

			if (root != null) {

				s.push(root);
				root = root.left;

			} else if (root2 != null) {

				s2.push(root2);
				root2 = root2.left;

			} else if (!s.isEmpty() && !s2.isEmpty()) {

				root = s.peek();
				root2 = s2.peek();

				if (root.data == root2.data) {

					System.out.print(root.data);

					s.pop();
					s2.pop();
					root = root.right;
					root2 = root2.right;

				} else if (root.data > root2.data) {

					s2.pop();
					root2 = root2.left;
					root = null;

				} else if (root.data < root2.data) {

					s.pop();
					root = root.right;
					root2 = null;

				}
			} else {
				System.out.println("No Common");
				break;
			}

		}

	}

	public void trimTreeWSumLessThanK(int k) {

		root = trimTreeWSumLessThanK(root, k);

	}

	public Node trimTreeWSumLessThanK(Node node, int sum) {

		if (node == null)
			return null;

		node.left = trimTreeWSumLessThanK(node.left, sum - node.data);

		node.right = trimTreeWSumLessThanK(node.right, sum - node.data);

		if (isLeaf(node)) {
			if (sum > node.data)
				return null;
		}

		return node;
	}

	private boolean isLeaf(Node node) {
		if (node.left == null && node.right == null) {
			return true;
		}
		return false;
	}

	static PriorityQueue<Integer> pq = new PriorityQueue<Integer>();

	public void KthLargest(int k) {
		KthLargest(root, k);

		System.out.println(pq.peek());
	}

	private void KthLargest(Node node, int k) {
		if (node == null) {
			return;
		}

		if (pq.size() == k) {
			if (node.data > pq.peek()) {
				pq.poll();

				pq.add(node.data);
			}
		} else if (pq.size() < k) {
			pq.add(node.data);
		}

		KthLargest(node.right, k);
		KthLargest(node.left, k);
	}

	public void KthLargestBetter(int k) {
		int count = 0;
		Node node = root;

		while (node != null) {
			if (node.right == null) {

				if (++count == k) {
					System.out.println(node.data);
					break;
				}
				node = node.left;
			} else {

				Node temp = r_lM(node);

				if (temp.left == null) {

					temp.left = node;

					node = node.right;

				} else {

					temp.left = null;
					if (++count == k) {
						System.out.println(node.data);
						break;
					}

					node = node.left;

				}

			}
		}
	}

	private Node r_lM(Node node) {

		Node temp = node.right;
		while (node.left != null || temp.left != node) {
			temp = temp.left;
		}
		return temp;
	}

	public void MergeBT(Binary_Tree tree1, Binary_Tree tree2) {

		Stack<Node> stack1 = new Stack<Node>();
		Stack<Node> stack2 = new Stack<Node>();

		Node curr1 = tree1.root;
		Node curr2 = tree2.root;

		while (curr1 != null && curr2 != null) {

			while (curr1 != null) {
				stack1.push(curr1);
				curr1 = curr1.left;
			}

			while (curr2 != null) {
				stack2.push(curr2);
				curr2 = curr2.left;
			}

			curr1 = stack1.pop();
			curr2 = stack2.pop();

			if (curr1.data > curr2.data) {

				System.out.println(curr1.data + " ");

				curr1 = curr1.right;

				stack1.push(curr1);

				curr2 = null;
			} else {

				System.out.println(curr2.data + " ");

				curr2 = curr2.right;

				stack2.push(curr2);

				curr1 = null;
			}

		}

		if (!stack1.isEmpty())
			printBSTFromStack(stack1);
		if (!stack2.isEmpty())
			printBSTFromStack(stack2);

	}

	private void printBSTFromStack(Stack<Node> stack) {

		Node cur = null;
		while (cur != null || !stack.isEmpty()) {

			while (cur != null) {
				stack.push(cur);
				cur = cur.left;
			}
			cur = stack.pop();
			System.out.print(cur.data + " ");
			cur = cur.right;

		}

	}

	public void Ancestor_Difference() {
		System.out.println(Ancestor_Difference(root));
	}

	public int Ancestor_Difference(Node node) {

		return Ancestor_Difference(node, node.data, node.data);
	}

	private int Ancestor_Difference(Node node, int min, int max) {
		if (node == null) {
			return max - min;
		}

		max = Math.max(max, node.data);

		min = Math.min(min, node.data);

		return Math.max(Ancestor_Difference(node.left, min, max), Ancestor_Difference(node.right, min, max));
	}

	Node DllHead = new Node();

	static Node prevNode = null;

	public Node leafs_To_DLL() {

		leafs_To_DLL(root);

		printDLL(DllHead);

		return DllHead;

	}

	public Node leafs_To_DLL(Node node) {
		if (node == null) {
			return null;
		}

		if (node.left == null && node.right == null) {

			if (prevNode == null) {
				DllHead = node;
			} else {
				prevNode.right = node;
				node.left = prevNode;
			}
			prevNode = node;
			return null;
		}

		node.left = leafs_To_DLL(node.left);
		node.right = leafs_To_DLL(node.right);

		return node;
	}

	static String str = new String();

	public void Serialize() {
		ArrayList<Integer> idx = new ArrayList<Integer>();
		serialize(root, idx);
		idx.add(-1);
		System.out.println(idx);
	}

	private void serialize(Node node, ArrayList<Integer> idx) {

		if (node == null) {
			return;
		}

		idx.add(node.data);

		serialize(node.left, idx);

		serialize(node.right, idx);

		idx.add(-1);

	}

	static String NullSymbol = "X";

	static String Deltmiter = ",";

	public String Serialize_LEETCODE() {
		String code = Serialize_LEETCODE(root);

		return code;

	}

	private String Serialize_LEETCODE(Node node) {

		if (node == null) {
			return NullSymbol + Deltmiter;
		}

		String left = Serialize_LEETCODE(node.left);
		String right = Serialize_LEETCODE(node.right);

		return node.data + Deltmiter + left + right;
	}

	public Node DeSerialize_LEETCODE(String Code) {

		Queue<String> queue = new LinkedList<>();

		queue.addAll(Arrays.asList(Code.split(Deltmiter)));

		return DeSerialize_LEETCODE(queue);
	}

	private Node DeSerialize_LEETCODE(Queue<String> Serialized_Queue) {

		String nodeVal = Serialized_Queue.poll();

		if (nodeVal == NullSymbol) {
			return null;
		}
		Node node = new Node();

		node.data = Integer.valueOf(nodeVal);

		node.left = DeSerialize_LEETCODE(Serialized_Queue);

		node.right = DeSerialize_LEETCODE(Serialized_Queue);

		return node;

	}

	public void Connect_Same_Level() {

		HashMap<Integer, Node> map = new HashMap<>();

		Connect_Same_Level(root, 0, map);
	}

	static int Max_Level = -1;

	private void Connect_Same_Level(Node node, int level, HashMap<Integer, Node> map) {
		if (node == null) {
			return;
		}

		if (map.containsKey(level)) {

			Node temp = map.get(level);
//			temp.next = node;
			map.put(level, node);

		} else {
			map.put(level, node);
		}

		Max_Level = Math.max(level, Max_Level);

		Connect_Same_Level(node.left, level + 1, map);
		Connect_Same_Level(node.right, level + 1, map);

	}

	public boolean TargetPair(int k) {

		return TargetPair(root, k, new HashSet<Integer>());

	}

	private boolean TargetPair(Node node, int k, HashSet<Integer> set) {

		if (node == null) {
			return false;
		}

		if (set.contains(k - node.data)) {
			return true;
		}

		set.add(node.data);

		return TargetPair(node.left, k, set) || TargetPair(node.right, k, set);

	}

	static int max = -1;

	public void max_path_node_to_Node() {

		max_path_node_to_Node(root);

	}

	private int max_path_node_to_Node(Node node) {
		if (node == null) {
			return 0;
		}

		int leftval = max_path_node_to_Node(node.left);
		int rightval = max_path_node_to_Node(node.right);

		int currentval = Math.max(Math.max(leftval, rightval) + node.data, node.data);

		int val = Math.max(currentval, leftval + node.data + rightval);

		max = Math.max(max, val);

		return currentval;
	}

	static boolean result = false;

	public boolean Edge_tree_divide() {

		Edge_tree_divide(root, NodeCount(root), result);

		return result;
	}

	private int Edge_tree_divide(Node node, int count, boolean result) {

		if (node == null) {
			return 0;
		}

		int c = Edge_tree_divide(node.left, count, result) + Edge_tree_divide(node.right, count, result) + 1;

		if (c == count - c) {
			result = true;
		}

		return c;
	}

	private int NodeCount(Node node) {
		if (node == null) {
			return 0;
		}
		return (NodeCount(node.left) + NodeCount(node.right) + 1);
	}

	public void Boundary_Traversal() {

		Node node = this.root;

		if (node != null) {

			System.out.println(node.data);

			printLeft(node.left);

			printLeave(node.left);
			printLeave(node.right);

			printRight(node.right);

		}

	}

	private void printLeft(Node node) {

		if (node != null) {

			if (node.left != null) {

				System.out.println(node.data);

				printLeft(node.left);
			} else if (node.right != null) {

				System.out.println(node.data);

				printLeft(node.right);
			}

		}

	}

	private void printRight(Node node) {
		if (node != null) {

			if (node.right != null) {

				System.out.println(node.data);

				printRight(node.right);
			} else if (node.left != null) {

				System.out.println(node.data);

				printRight(node.left);
			}

		}

	}

	private void printLeave(Node node) {

		if (node == null) {
			return;
		}
		if (node.left == null && node.right == null) {
			System.out.println(node.data + " ");
			return;
		}

		printLeave(node.left);

		printLeave(node.right);

	}

	public void CountKFarLeaf(int k) {

		ArrayList<Node> pathsofar = new ArrayList<Binary_Tree.Node>();
		CountKFarLeaf(root, pathsofar, k);
	}

	private void CountKFarLeaf(Node node, ArrayList<Node> list, int k) {

		if (node == null) {
			return;
		}

		list.add(node);

		if (list.size() > k && node.left == null && node.right == null) {

			System.out.print(list.get(list.size() - k).data + " ");

			list.set(list.size() - k, null);
		}

		CountKFarLeaf(node.left, list, k);
		CountKFarLeaf(node.right, list, k);

		list.remove(list.size() - 1);
	}

	public void LongestSequence() {
		LongestSequence(root, 0);
	}

	static int ml = 0;

	public void LongestSequence(Node node, int lsf) {

		if (ml < lsf) {
			ml = lsf;
		}

		if (node.left != null) {

			LongestSequence(node.left, node.data < node.left.data ? lsf + 1 : 1);
		}

		if (node.right != null) {
			LongestSequence(node.right, node.data < node.right.data ? lsf + 1 : 1);
		}
	}

	public void LongestSequencePrint() {

		LongestSequencePrint(root, 0);
	}

	static boolean Bflag = false;

	static boolean Eflag = false;

	private void LongestSequencePrint(Node node, int lsf) {
		if (ml == lsf) {

			Bflag = true;
		}
		if (!Bflag && node.left != null) {
			LongestSequencePrint(node.left, node.data < node.left.data ? lsf + 1 : 1);
		}

		if (!Bflag && node.right != null) {
			LongestSequencePrint(node.left, node.data < node.left.data ? lsf + 1 : 1);
		}

		if (Bflag == true && Eflag == false) {
			System.out.println(node.data);
			if (lsf == 1) {
				Eflag = true;
			}
		}

	}

	public void morrisInorderTraversal() {
		// L-N-R
		Node node = root;

		while (node != null) {
			if (node.left == null) {

				System.out.print(node.data + " ");

				node = node.right;

			} else {

				Node l_rm = leftRMost(node);

				if (l_rm.right == null) {

					l_rm.right = node;

					node = node.left;

				} else {

					l_rm.right = null;
					System.out.print(node.data + " ");
					node = node.right;
				}
			}
		}

	}

	private Node leftRMost(Node node) {

		Node l_rm = node.left;

		while (l_rm.right != null && l_rm.right != node) {
			l_rm = l_rm.right;
		}
		return l_rm;
	}

	public void morrisPreorderTraversal() {
		// N-L-R
		Node node = root;

		while (node != null) {
			if (node.left == null) {
				System.out.print(node.data + " ");
				node = node.right;

			} else {

				Node l_rm = leftRMost(node);

				if (l_rm.right == null) {

					l_rm.right = node;

					System.out.print(node.data + " ");

					node = node.left;

				} else {

					l_rm.right = null;

					node = node.right;
				}
			}
		}

	}

	public void TreefromPreOrderInOrder(String pre, String in) {

		root = construct(pre.toCharArray(), 0, pre.length() - 1, in.toCharArray(), 0, in.length() - 1);

	}

	private Node construct(char[] pre, int psi, int pei, char[] in, int isi, int iei) {

		if (psi < pei || isi < iei) {
			return null;
		}

		int idx = -1;

		for (int i = isi; i <= iei; i++) {
			if (in[i] == pre[psi]) {
				idx = i;
				break;
			}
		}

		int lhs = idx - isi;

		Node node = new Node();
		node.data = pre[psi];
		node.left = construct(pre, psi + 1, pei, in, isi, idx - 1);
		node.right = construct(pre, psi + lhs + 1, pei, in, idx + 1, iei);

		return node;
	}

	public void Reverselevelorder() {
		Reverselevelorder(root);
	}

	public void Reverselevelorder(Node node) {
		LinkedList<Node> queue = new LinkedList<>();
		LinkedList<Node> stack = new LinkedList<>();

		queue.addLast(node);

		while (!queue.isEmpty()) {

			int size = queue.size();

			while (size-- > 0) {

				Node temp = queue.removeFirst();

				stack.addFirst(temp);

				if (temp.right != null)
					queue.addLast(temp.right);
				if (temp.left != null)
					queue.addLast(temp.left);

			}
		}

		while (!stack.isEmpty()) {
			System.out.print(stack.removeFirst().data + " ");
		}
	}

	public void Reverselevelorder_Easy() {
		Reverselevelorder_Easy(root);
	}

	private void Reverselevelorder_Easy(Node node) {
		LinkedList<Node> queue = new LinkedList<>();

		LinkedList<Integer> stack = new LinkedList<>();

		queue.add(node);

		while (queue.size() > 0) {

			Node temp = queue.removeFirst();

			stack.addFirst(temp.data);

			if (temp.right != null) {
				queue.addLast(temp.right);
			}

			if (temp.left != null) {
				queue.addLast(temp.left);
			}

		}

		while (!stack.isEmpty()) {
			System.out.print(stack.removeFirst() + " ");
		}

	}

	public void printPostO1rderIN_PRE(int in[], int pre[], int n) {
		printPostOrderIN_PRE(pre, 0, n - 1, in, 0, n - 1);

	}

	private void printPostOrderIN_PRE(int[] pre, int psp, int pep, int[] in, int isp, int iep) {

		if (psp > pep || isp > iep) {
			return;
		}

	}

	public int sumNumber() {

		return sumnumber(root, 0);
	}

	public int sumnumber(Node node, int sum) {
		if (node == null) {
			return 0;
		}

		if (node.left == null && node.right == null) {
			return sum * 10 + node.data;
		}

		int ans = 0;
		ans += sumnumber(node.left, sum * 10 + node.data);
		ans += sumnumber(node.right, sum * 10 + node.data);
		return ans;
	}

	public int sumRootToLeaf(Node node) {
		return pathSum_0_1_Tree(node, "");
	}

	public int pathSum_0_1_Tree(Node root, String binSoFar) {
		if (root == null)
			return 0;

		binSoFar += root.data;

		if (root.left == null && root.right == null)

			return Integer.parseInt(binSoFar, 2);

		return pathSum_0_1_Tree(root.right, binSoFar) + pathSum_0_1_Tree(root.left, binSoFar);
	}

	public int sumOfLeftLeaves(Node node) {
		return helpersumOfLeftLeaves(node, false);
	}

	public int helpersumOfLeftLeaves(Node node, boolean isleaf) {

		if (node == null) {
			return 0;
		}

		if (node.left == null && node.right == null && (isleaf)) {
			return node.data;
		}

		return helpersumOfLeftLeaves(node.left, true) + helpersumOfLeftLeaves(node.right, false);

	}

	public int rightLeafSum(Node root) {

		return helperrightLeafSum(root, false);

	}

	public int helperrightLeafSum(Node node, boolean isRight) {
		if (node == null) {
			return 0;
		}

		if (node.right == null && node.left == null && (isRight)) {
			return node.data;
		}

		return (helperrightLeafSum(node.left, false) + helperrightLeafSum(node.right, true));

	}

	public void postorderTraversalSQ() {
		postorderTraversalSQ(root);
	}

	private void postorderTraversalSQ(Node node) {

		LinkedList<Node> stack1 = new LinkedList<>();
		LinkedList<Node> queue = new LinkedList<>();

		stack1.addFirst(node);

		while (!stack1.isEmpty()) {
			Node temp = stack1.removeFirst();

			if (temp.left != null) {
				stack1.addFirst(temp.left);
			}

			if (temp.right != null) {
				stack1.addFirst(temp.right);
			}

			queue.addFirst(temp);

		}
		while (!queue.isEmpty()) {
			System.out.print(queue.pollFirst().data + " ");
		}

	}

	public void postorderTraversalOneStack() {
		postorderTraversalOneStack(root);
	}

	private void postorderTraversalOneStack(Node node) {

		Node curr = node;

		LinkedList<Node> stack = new LinkedList<>();

		while (curr != null || !stack.isEmpty()) {

			if (curr != null) {
				stack.addFirst(curr);
				curr = curr.left;
			} else {
				Node temp = stack.peek().right;

				if (temp == null) {
					temp = stack.poll();
					System.out.print(temp.data + " ");

					while (!stack.isEmpty() && temp == stack.peek().right) {
						temp = stack.poll();
						System.out.print(temp.data + " ");
					}
				} else {
					curr = temp;
				}
			}

		}

	}

	public void postOrderItrOneStack(Node root) {
		Node current = root;
		Deque<Node> stack = new LinkedList<>();
		while (current != null || !stack.isEmpty()) {
			if (current != null) {
				stack.addFirst(current);
				current = current.left;
			} else {
				Node temp = stack.peek().right;
				if (temp == null) {
					temp = stack.poll();
					System.out.print(temp.data + " ");
					while (!stack.isEmpty() && temp == stack.peek().right) {
						temp = stack.poll();
						System.out.print(temp.data + " ");
					}
				} else {
					current = temp;
				}
			}
		}
	}

	public Node CloneBst() {
		extendTree(root);
		cloneTree(root);
		return seprateTree(root);
	}

	private void extendTree(Node node) {
		if (node == null) {
			return;
		}
		extendTree(node.left);
		extendTree(node.right);

		Node newleft = new Node();

		newleft.data = node.data;
		newleft.left = node.left;

		node.left = newleft;
	}

	private void cloneTree(Node node) {
		if (node == null) {
			return;
		}

		// node.left.random = node.random.left;

		cloneTree(node.left);
		cloneTree(node.right);
	}

	private Node seprateTree(Node Onode) {

		Node Clonenode = new Node();
		Node lcnode = null;
		Node rcnode = null;

		if (Onode.left != null && Onode.left.left != null) {
			lcnode = seprateTree(Onode.left.left);
		}

		if (Onode.right != null) {
			lcnode = seprateTree(Onode.right);
		}

		Onode.left = Clonenode.left;

		Clonenode.left = lcnode;

		Clonenode.right = rcnode;

		return Clonenode;

	}

	public boolean isSymmetric(Node root) {
		return root == null || isSymmetricHelp(root.left, root.right);
	}

	private boolean isSymmetricHelp(Node left, Node right) {
		if (left == null || right == null)
			return left == right;
		if (left.data != right.data)
			return false;
		return isSymmetricHelp(left.left, right.right) && isSymmetricHelp(left.right, right.left);
	}

	public boolean isSameTree(Node p, Node q) {
		if (p == null && q == null)
			return true;
		if (p == null || q == null)
			return false;
		if (p.data == q.data)
			return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
		return false;
	}

	public void PrintAllPathTillK(int k) {
		PrintAllPathTillK(root, k);
	}

	static ArrayList<Integer> path = new ArrayList<Integer>();

	private void PrintAllPathTillK(Node node, int k) {
		if (node == null)
			return;

		path.add(node.data);

		PrintAllPathTillK(node.left, k);

		PrintAllPathTillK(node.right, k);

		int sum = 0;
		for (int i = path.size() - 1; i > 0; i--) {

			sum += path.get(i);

			if (k == sum) {
				printPath(path, i);
			}

		}

		path.remove(path.size() - 1);

	}

	private void printPath(ArrayList<Integer> path, int start) {

		for (int i = start; i < path.size(); i++) {
			System.out.print(path.get(i) + " ");
		}
		System.out.println();
	}

	public Node constructMaximumBinaryTree(int[] arr) {

		return create(0, arr.length - 1, arr);

	}

	public Node create(int lo, int hi, int[] arr) {
		Node node = null;
		int maxIdx = helper(lo, hi, arr);

		if (maxIdx != -1) {
			node = new Node();
			node.data = arr[maxIdx];
		}

		if (node != null) {
			node.left = create(lo, maxIdx - 1, arr);
			node.right = create(maxIdx + 1, hi, arr);

		}

		return node;
	}

	public int helper(int lo, int hi, int[] arr) {

		if (lo > hi) {
			return -1;
		}

		int max = Integer.MIN_VALUE;

		int maxIdx = -1;

		for (int i = lo; i <= hi; i++) {
			if (max < arr[i]) {
				max = arr[i];
				maxIdx = i;
			}

		}

		return maxIdx;

	}
}
