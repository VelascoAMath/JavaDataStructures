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
public class LinkedList<E> {
	private Node<E> head;
	private int numItems;

	/**
	 * 
	 */
	public LinkedList() {
		numItems = 0;
		head = null;
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

//	private Node<E> find(int index) {
//		Node<E> curr = head;
//		for (int skip = 1; skip < index; skip++) {
//			curr = curr.next;
//		}
//		return curr;
//	}

	/**
	 * Adds an item to the end of the list.
	 * 
	 * @param newItem to add to the list
	 */
	public void add(E newItem) {
		addLast(newItem);
	}

	public void add(E newItem, int index) {
		if (index > numItems)
			throw new IndexOutOfBoundsException("Out of Bounds!");

		Node<E> curr = head;
		while (curr.next != null) {
			curr = curr.next;
		}

		curr.next = new Node<E>(newItem);
		numItems++;
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
		numItems++;
	}

	public void addLast(E lastItem) {
		Node<E> curr = head;
		if (curr == null) {
			head = new Node<E>(lastItem);
		} else {
			while (curr.next != null)
				curr = curr.next;

			curr.next = new Node<E>(lastItem);
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
		Node<E> curr = head;

		
		for (int count = 1; count < i; count++) {
			curr = curr.next;
		}
		
		result = curr.next.item;
		curr.next = curr.next.next;

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
		} else {
			Node<E> curr = head;
			while (curr.next.next != null)
				curr = curr.next;
			result = curr.next.item;
			curr.next = null;
		}
		numItems--;
		return result;
	}

	/**
	 * Deletes everything in the list. Note: This is much more efficient than
	 * calling any of the remove methods repeatedly.
	 */
	public void removeAll() {
		head = null;
		numItems = 0;
	}

	public String toString() {
		if (head == null)
			return "[]";
		else {
			Node<E> curr = head;
			String s = "[" + curr.item;
			curr = curr.next;
			while (curr.next != null) {
				s += ", " + curr.item;
				curr = curr.next;
			}

			s = s + ", " + curr.item + "]";
			return s;
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
}
