import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;


public class BHolder {

	ArrayList<Integer> list;
	ArrayList<BHolder> children;
	private BHolder setParent;

	public BHolder() {
		super();
		list = new ArrayList<Integer>();
		children = new ArrayList<BHolder>();
	}

	public BHolder(int degree) {
		super();
		list = new ArrayList<Integer>(degree);
		children = new ArrayList<BHolder>();
	}

	public void add(int item) {

		if (list.size() == 0) {
			list.add(item);
			return;
		}

		if (list.size() == 1) {
			if (item < list.get(0)) {
				list.add(0, item);
			} else {
				list.add(item);
			}
			return;
		}

		if (item < list.get(0)) {
			list.add(0, item);
			return;
		}
		if (list.get(list.size() - 1) <= item) {
			list.add(item);
			return;
		}

		for (int i = 1; i < list.size(); i++) {
			if (list.get(i - 1) <= item && item < list.get(i)) {
				list.add(i, item);
				return;
			}
		}
		assertTrue(false);
	}

	public int getFirst() {
		return list.get(0);
	}

	public int getLast() {
		return list.get(list.size() - 1);
	}

	@Override
	public String toString() {
		return "BHolder [list=" + list + "]";
	}

	public int size() {
		return list.size();
	}

	public int popRight() {
		return list.remove(list.size() - 1);
	}

	public int popLeft() {
		return list.remove(0);
	}

	public void addChild(BHolder child) {
		if (children.size() == 0) {
			children.add(child);
			return;
		}

		if (child.getLast() < children.get(0).getFirst()) {
			children.add(0, child);
			return;
		}

		if (children.get(children.size() - 1).getLast() <= child.getFirst()) {
			children.add(child);
			return;
		}

		for (int i = children.size() - 1; i >= 1; i--) {
			if (children.get(i - 1).getLast() <= child.getFirst() && child.getLast() < children.get(i).getFirst()) {
				children.add(i, child);
				return;
			}
		}

		assertTrue(false);
	}

	public void setParent(BHolder parent) {
		this.setParent = parent;
	}
}
