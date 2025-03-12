package src.velasco.tree;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class BTree<E> implements Set<E>, Collection<E> {

	int degree;
	BHolder root;

	public BTree(int degree) throws Exception {
		if (degree < 3) {
			System.err.println("Degree (" + degree + ") is too small!");
			throw new Exception();
		}
		this.degree = degree;
		root = null;
	}

	public boolean add(int item) {
		if (root == null) {
			root = new BHolder(degree);
		}
		BHolder current = root;
		current.add(item);
		if (current.size() == degree) {
			rebalanceInsertion(current);
		}

		return false;
	}

	private void rebalanceInsertion(BHolder current) {
		while (current.size() == degree) {
			BHolder left = new BHolder();
			BHolder right = new BHolder();

			int halfPoint = (current.size() - 1) / 2;
			for (int i = 0; i < halfPoint; i++) {
				right.add(current.popRight());
			}

			for (int i = 0; i < halfPoint; i++) {
				left.add(current.popLeft());
			}

			current.addChild(left);
			current.addChild(right);
			left.setParent(current);
			right.setParent(current);
		}
	}

	@Override
	public String toString() {
		// return "src.velasco.tree.BTree [degree=" + degree + ", root=" + root + "]";
		if (root == null) {
			return "";
		}
		StringBuilder s = new StringBuilder();
		LinkedList<BHolder> queue = new LinkedList<>();
		queue.add(root);
		while (!queue.isEmpty()) {
			LinkedList<BHolder> other = new LinkedList<>();
			for (BHolder b : queue) {
				other.addAll(b.children);
				s.append(b + " ");
			}
			s.append('\n');
			queue = other;
		}
		return s.toString();
	}

	@Override
	public boolean addAll(Collection c) {
		// TODO Auto-generated method stub
		for (Object object : c) {
			add(object);
		}
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	public static void main(String[] args) throws Exception {
		BTree tree = new BTree(3);

		for (int i = 0; i < 20; i++) {
			tree.add(i);
			System.out.println(tree);
		}

		System.out.println(tree);
	}

	@Override
	public boolean add(Object e) {
		// TODO Auto-generated method stub
		return false;
	}

}
