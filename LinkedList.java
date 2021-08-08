import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Nalfredo
 *
 */

/**
 * It's my own Linked List.
 * 
 * @author VelascoAMath
 *
 * @param <E>
 */
/**
 * @author Nalfredo
 *
 * @param <E>
 */
/**
 * @author Nalfredo
 *
 * @param <E>
 */
/**
 * @author Nalfredo
 *
 * @param <E>
 */
public class LinkedList<E> implements Collection{
	Node<E> head;
	Node<E> lastSelectedNode;
	int lastSelectedIndex;
	private int numItems;

	/**
	 * 
	 */
	public LinkedList() {
		numItems = 0;
		head = null;
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
			if (index <= lastSelectedIndex) {
				lastSelectedNode = head;
				lastSelectedIndex = 0;

			}
			
			for (; lastSelectedIndex < index - 1; lastSelectedIndex++) {
				lastSelectedNode = lastSelectedNode.next;
			}

			Node<E> newNode = new Node<E>(newItem, lastSelectedNode.next);
			lastSelectedNode.next = newNode;

			numItems++;
		}

	}

	/**
	 * Adds an item to the beginning of the list
	 * 
	 * @param firstItem - the item to add to the beginning of the list
	 */
	public void addFirst(E firstItem) {
		Node<E> newNode = new Node<E>(firstItem);
		newNode.next = head;
		head = newNode;
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
			lastSelectedIndex = 0;
			lastSelectedNode = head;
		} else {
			while (lastSelectedNode.next != null) {
				lastSelectedNode = lastSelectedNode.next;
				lastSelectedIndex++;
			}

			lastSelectedNode.next = new Node<E>(lastItem);
		}

		numItems++;
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

		if (lastSelectedIndex > index) {
			lastSelectedNode = head;
			lastSelectedIndex = 0;
		}

		for (; lastSelectedIndex < index; lastSelectedIndex++)
			lastSelectedNode = lastSelectedNode.next;

		return lastSelectedNode.item;
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

	public E remove(int index) {

		if (size() == 0) {
			throw new NoSuchElementException("Can't remove item from an empty list!");
		}

		if (index >= numItems || index < 0)
			throw new IndexOutOfBoundsException(
					"Index " + index + " is out of bounds for a list of size " + size() + "!");

		if (index == 0) {
			return removeFirst();
		}

		E result = null;

		if (index <= lastSelectedIndex) {
			lastSelectedNode = head;
			lastSelectedIndex = 0;
		}

		for (; lastSelectedIndex < index - 1; lastSelectedIndex++) {
			lastSelectedNode = lastSelectedNode.next;
		}

		result = lastSelectedNode.next.item;
		lastSelectedNode.next = lastSelectedNode.next.next;

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
		return remove(size() - 1);
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

//	/**
//	 * @param args
//	 */
//	public static void main(String[] args) {
//		LinkedList<Integer> a = new LinkedList<Integer>();
//
//		for (int i = 0; i < 10000; i++) {
//			a.addLast(i);
//		}
//
//		while (!a.isEmpty()) {
//			System.out.println(a.removeLast());
//		}
//	}

	/**
	 * It's a Node for LinkedLists.
	 * 
	 * @author CoolerMaster
	 *
	 * @param <E>
	 */
	class Node<E> {
		E item;
		Node<E> next;

		/**
		 * Makes a node that stores an element.
		 * 
		 * @param Eitem
		 */
		public Node(E item) {
			this.item = item;
			next = null;
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

		public String toString() {
			if (next == null) {
				return String.valueOf(item) + ":Null";
			}
			return String.valueOf(item) + ":" + String.valueOf(next.item);
		}
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public boolean add(Object e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection c) {
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
	public void clear() {
		// TODO Auto-generated method stub
		
	}
}
