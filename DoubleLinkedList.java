import java.util.NoSuchElementException;

/**
 * 
 */

/**
 * @author Nalfredo
 *
 */
public class DoubleLinkedList<E> {
	Node<E> head;
	Node<E> tail;
	private int numItems;
	Node<E> lastSelectedNode;
	int lastSelectedIndex;

	/**
	 * 
	 */
	public DoubleLinkedList() {
		numItems = 0;
		head = null;
		tail = null;
		lastSelectedNode = null;
		lastSelectedIndex = -1;
	}

	/**
	 * Informs the user
	 * 
	 * @return a boolean indicating if the list is empty
	 */
	public boolean isEmpty() {
		return numItems == 0;
	}

	/**
	 * Informs the user of the number of elements in the list
	 * 
	 * @return the number of elements in the list
	 */
	public int size() {
		return numItems;
	}


	/**
	 * Adds an item to the end of the list.
	 * 
	 * @param newItem to add to the list
	 */
	public void add(E newItem) {
		addLast(newItem);
	}

	public void add(E newItem, int index) {
		if (index > numItems || index < 0)
			throw new IndexOutOfBoundsException("Out of Bounds!");

		// Don't need to check index because the previous error check already does
		if (isEmpty()) {
			addFirst(newItem);
		} else if (index == numItems) {
			addLast(newItem);
		} else if (index == 0) {
			addFirst(newItem);
		} else {
			Node<E> curr = null;
			if (lastSelectedIndex < index) {
				curr = lastSelectedNode;
				for (int i = lastSelectedIndex + 1; i < index; i++) {
					curr = curr.next;
				}
				lastSelectedIndex = index;
				Node<E> newNode = new Node<E>(newItem, curr.next);
				lastSelectedNode = newNode;
				curr.next = newNode;

			} else {
				curr = head;

				for (int i = 1; i < index; i++, curr = curr.next) {

				}
				lastSelectedIndex = index;
				Node<E> newNode = new Node<E>(newItem, curr.next);
				curr.next = newNode;
				lastSelectedNode = newNode;
			}

			numItems++;
		}

	}

	/**
	 * Adds an item to the beginning of the list
	 * 
	 * @param firstItem - the item to add to the beginning of the list
	 */
	public void addFirst(E firstItem) {
		Node<E> curr = new Node<E>(firstItem);
		curr.next = head;
		head = curr;
		if (lastSelectedIndex == -1) {
			lastSelectedIndex = 0;
			lastSelectedNode = head;
		} else {
			lastSelectedIndex++;
		}
		numItems++;
	}

	public void addLast(E lastItem) {
		if (head == null) {
			head = new Node<E>(lastItem);
			lastSelectedNode = head;
		} else {
			// Since numItems hasn't been updated, the index will (correctly) be the second
			// to last index
			tail.next = new Node<E>(lastItem);
			tail.next.prev = tail;
			tail = tail.next;
		}

		numItems++;
		lastSelectedNode = tail;
		lastSelectedIndex = numItems - 1;
	}

	public E get() {
		return getFirst();
	}

	public E get(int index) {
		if (numItems == 0) {
			throw new NoSuchElementException("Can't get item " + index + " because list is empty!");
		}
		if (index > numItems || index < 0)
			throw new IndexOutOfBoundsException("Out of Bounds!");

		Node<E> curr = head;
		for (int i = 0; i < index; i++)
			curr = curr.next;

		return curr.item;
	}

	/**
	 * Returns the first item in the list
	 * 
	 * @return the first item in the list
	 */
	public E getFirst() {
		if (size() == 0) {
			throw new NoSuchElementException("No first item since the list is empty!");
		}
		return head.item;
	}

	public E getLast() {
		return get(size() - 1);
	}

	public E removeFirst() {
		if (size() == 0) {
			throw new NoSuchElementException("Can't remove the first item from an empty list!");
		}

		E result = head.item;
		head = head.next;
		numItems--;
		lastSelectedNode = head;
		if (numItems == 0) {
			lastSelectedIndex = -1;
		} else {
			lastSelectedIndex = 0;
		}
		return result;

	}

	public E remove(int i) {

		if (size() == 0) {
			throw new NoSuchElementException("Can't remove item from an empty list!");
		}

		if (i >= numItems || i < 0)
			throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for a list of size " + size() + "!");

		if (i == 0) {
			return removeFirst();
		}

		if (i == size() - 1) {
			return removeLast();
		}

		E result = null;
		Node<E> curr = null;
		int count;

		if (i <= lastSelectedIndex || lastSelectedNode == head) {
			curr = head;
			count = 1;
		} else {
			curr = lastSelectedNode;
			count = lastSelectedIndex + 1;
		}

		for (; count < i; count++) {
			curr = curr.next;
		}

		result = curr.next.item;
		curr.next = curr.next.next;
		lastSelectedNode = curr;
		lastSelectedIndex = i - 1;

		numItems--;
		return result;
	}

	/**
	 * Returns and removes the last item in the list
	 * 
	 * @return the list item in the list
	 * @throws NoSuchElementException
	 */
	public E removeLast() {
		E result = null;
		if (size() == 0) {
			throw new NoSuchElementException("Attempting to retreive the last item from an empty LinkedList!");
		} else if (size() == 1) {
			result = head.item;
			head = null;
			lastSelectedNode = head;
		} else {
			Node<E> curr;
			// If the last selected node is already the last one, we can't remove it
			// We need the second to last node to remove the last one
			if (lastSelectedNode.next == null) {
				curr = head;
			} else {
				curr = lastSelectedNode;
			}

			while (curr.next.next != null)
				curr = curr.next;
			result = curr.next.item;
			curr.next = null;
			lastSelectedNode = curr;
		}
		numItems--;
		lastSelectedIndex = size() - 1;
		return result;
	}

	/**
	 * Deletes everything in the list. Note: This is much more efficient than
	 * calling any of the remove methods repeatedly.
	 */
	public void removeAll() {
		head = null;
		numItems = 0;
		lastSelectedNode = null;
		lastSelectedIndex = -1;
	}

	public String toString() {
		if (numItems == 0)
			return "[]";
		else if (numItems == 1) {
			return "[" + head.item + "]";
		} else {

			StringBuilder s = new StringBuilder("[");
			Node<E> curr = head;
			for (; curr.next != null; curr = curr.next) {
				s.append(curr.item);
				s.append(", ");

			}

			s.append(curr.item);
			s.append(']');
			return s.toString();
		}
	}

	/**
	 * It's a Node for DoubleLinkedLists.
	 * 
	 * @author VelascoAMath
	 *
	 * @param <E>
	 */
	class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;

		/**
		 * Makes a node that stores an element.
		 * 
		 * @param Eitem
		 */
		public Node(E item) {
			this.item = item;
			next = null;
			prev = null;
		}

		/**
		 * Makes a node that connects to another and has an element.
		 * 
		 * @param E       item
		 * @param Node<E> nextNode
		 */
		public Node(E item, Node<E> nextNode) {
			this.item = item;
			next = nextNode;
		}

		public Node(E item, Node<E> nextNode, Node<E> prevNode) {
			this.item = item;
			next = nextNode;
			prev = prevNode;
		}

		public String toString() {
			return String.valueOf(item);
		}
	}
}
