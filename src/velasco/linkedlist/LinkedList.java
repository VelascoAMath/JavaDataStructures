package src.velasco.linkedlist;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * It's my own Linked List.
 *
 * @author VelascoAMath
 *
 * @param <E>
 */
public class LinkedList<E> implements Collection<E>{
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

            lastSelectedNode.next = new Node<>(newItem, lastSelectedNode.next);

			numItems++;
		}

	}

	/**
	 * Adds an item to the beginning of the list
	 * 
	 * @param firstItem - the item to add to the beginning of the list
	 */
	public void addFirst(E firstItem) {
		Node<E> newNode = new Node<>(firstItem);
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

	public boolean addLast(E lastItem) {
		if (head == null) {
			head = new Node<E>(lastItem);
			lastSelectedIndex = 0;
			lastSelectedNode = head;
		} else {
			while (lastSelectedNode.next != null) {
				lastSelectedNode = lastSelectedNode.next;
				lastSelectedIndex++;
			}

			lastSelectedNode.next = new Node<>(lastItem);
		}

		numItems++;
		return true;
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

		E result;

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
	 * @throws NoSuchElementException - Thrown if the list is empty
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
//		src.velasco.linkedlist.LinkedList<Integer> a = new src.velasco.linkedlist.LinkedList<Integer>();
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
	 * @param <T>
	 */
    static class Node<T> {
		T item;
		Node<T> next;

		/**
		 * Makes a node that stores an element.
		 * 
		 * @param item - item to store
		 */
		public Node(T item) {
			this.item = item;
			next = null;
		}


		/**
		 * Makes a node that connects to another and has an element
		 *
		 * @param item - Item to hold
		 * @param nextNode - node to which the new node will point
		 */
		public Node(T item, Node<T> nextNode) {
			this.item = item;
			next = nextNode;
		}

		public String toString() {
			if (next == null) {
				return item + ":Null";
			}
			return item + ":" + next.item;
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

	/**
	 * Ensures that this collection contains the specified element (optional
	 * operation).  Returns {@code true} if this collection changed as a
	 * result of the call.  (Returns {@code false} if this collection does
	 * not permit duplicates and already contains the specified element.)<p>
	 * <p>
	 * Collections that support this operation may place limitations on what
	 * elements may be added to this collection.  In particular, some
	 * collections will refuse to add {@code null} elements, and others will
	 * impose restrictions on the type of elements that may be added.
	 * Collection classes should clearly specify in their documentation any
	 * restrictions on what elements may be added.<p>
	 * <p>
	 * If a collection refuses to add a particular element for any reason
	 * other than that it already contains the element, it <i>must</i> throw
	 * an exception (rather than returning {@code false}).  This preserves
	 * the invariant that a collection always contains the specified element
	 * after this call returns.
	 *
	 * @param newItem element whose presence in this collection is to be ensured
	 * @return {@code true} if this collection changed as a result of the
	 * call
	 * @throws UnsupportedOperationException if the {@code add} operation
	 *                                       is not supported by this collection
	 * @throws ClassCastException            if the class of the specified element
	 *                                       prevents it from being added to this collection
	 * @throws NullPointerException          if the specified element is null and this
	 *                                       collection does not permit null elements
	 * @throws IllegalArgumentException      if some property of the element
	 *                                       prevents it from being added to this collection
	 * @throws IllegalStateException         if the element cannot be added at this
	 *                                       time due to insertion restrictions
	 */
	@Override
	public boolean add(Object newItem) {
		return addLast((E) newItem);
	}

	@Override
	public Object[] toArray(Object[] a) {
		// TODO Auto-generated method stub
		return null;
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
