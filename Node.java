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
	 * @param E
	 *            item
	 * @param Node<E>
	 *            nextNode
	 */
	public Node(E item, Node<E> nextNode) {
		this.item = item;
		next = nextNode;
	}

	public String toString() {
		return String.valueOf(item);
	}
}
