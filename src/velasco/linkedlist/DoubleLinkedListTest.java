package src.velasco.linkedlist;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.jupiter.api.Test;

class DoubleLinkedListTest {
	int testSize = 10000;

	@Test
	void testDoubleLinkedList() {
		DoubleLinkedList<Integer> test = new DoubleLinkedList<>();
		if (test.lastSelectedIndex != -1) {
			fail("Last Selected Index is " + test.lastSelectedIndex + " instead of -1!");
		}
		if (test.lastSelectedNode != null) {
			fail("Last selected node is set to " + test.lastSelectedNode + " instead of null!");
		}
		if (test.head != null) {
			fail("Head is set to " + test.head + " instead of null!");
		}

		if (test.tail != null) {
			fail("Tail is set to " + test.tail + " instead of null!");
		}

		if (test.size() != 0) {
			fail("Size is " + test.size() + " instead of 0!");
		}
	}

	@Test
	void testIsEmpty() throws Exception {
		DoubleLinkedList<Integer> test = new DoubleLinkedList<>();

		if (!test.isEmpty()) {
			fail("New src.velasco.linkedlist.DoubleLinkedList is not empty!");
		}

		for (int seed = 0; seed < 100; seed++) {
			test.removeAll();
			Random gen = new Random(seed);
			int numItems = 0;

			for (int i = 0; i < testSize; i++) {
				// Randomly add an item
				if (gen.nextBoolean()) {
					int index = gen.nextInt(test.size() + 1);
					test.add(i, index);
					numItems++;
				} else if (numItems > 0) {
					test.remove(gen.nextInt(test.size()));
					numItems--;
				}

				if (numItems != test.size()) {
					fail("The size of the list(" + test.size() + ") doesn't match the real size " + numItems + "!");
				}
				if (numItems > 0 && test.isEmpty()) {
					fail("List is empty even when it has items!");
				}

				if (numItems == 0 && !test.isEmpty()) {
					fail("List isn't empty even when it has no items!");
				}

				if (numItems == 0 && test.lastSelectedIndex != -1) {
					fail("Last Selected Index is " + test.lastSelectedIndex + " instead of -1!");
				}
				if (numItems == 0 && test.lastSelectedNode != null) {
					fail("Last selected node is set to " + test.lastSelectedNode + " instead of null!");
				}
				if (numItems == 0 && test.head != null) {
					fail("Head is set to " + test.head + " instead of null!");
				}

				if (numItems == 0 && test.tail != null) {
					fail("Tail is set to " + test.tail + " instead of null!");
				}

				if (numItems == 0 && test.lastSelectedIndex < -1) {
					fail("Last index is less than -1!");
				}

				checkForConnection(test);
			}
		}

	}

	@Test
	void testSize() throws Exception {
		DoubleLinkedList<Integer> test = new DoubleLinkedList<Integer>();
		Random gen = new Random();

		for (int i = 0; i < testSize; i++) {
			if (test.size() != i) {
				fail("Size returned(" + test.size() + ") doesn't match the correct size(" + i + ")");
			}
			test.add(i, gen.nextInt(test.size() + 1));
			if (test.size() != i + 1) {
				fail("Size returned(" + test.size() + ") doesn't match the correct size(" + i + 1 + ")");
			}
		}

		for (int i = testSize - 1; i >= 0; i--) {
			try {
				test.remove(gen.nextInt(test.size()));
			} catch (Exception e) {
				fail("testSize threw exception: " + e.getStackTrace());
			}
			if (test.size() != i) {
				fail("Size returned(" + test.size() + ") doesn't match the correct size(" + i + ")");
			}
		}

		for (int i = 0; i < testSize; i++) {
			if (test.size() != i) {
				fail("Size returned(" + test.size() + ") doesn't match the correct size(" + i + ")");
			}
			test.addLast(i);
			if (test.size() != i + 1) {
				fail("Size returned(" + test.size() + ") doesn't match the correct size(" + i + ")");
			}
		}

		for (int i = testSize - 1; i >= 0; i--) {
			try {
				test.removeLast();
			} catch (Exception e) {
				fail("testSize threw exception: " + e.getStackTrace());
			}
			if (test.size() != i) {
				fail("Size returned(" + test.size() + ") doesn't match the correct size(" + i + ")");
			}
		}

		for (int i = 0; i < testSize; i++) {
			if (test.size() != i) {
				fail("Size returned(" + test.size() + ") doesn't match the correct size(" + i + ")");
			}
			test.addFirst(i);
			if (test.size() != i + 1) {
				fail("Size returned(" + test.size() + ") doesn't match the correct size(" + i + ")");
			}
		}

		for (int i = testSize - 1; i >= 0; i--) {
			try {
				test.removeFirst();
			} catch (Exception e) {
				fail("testSize threw exception: " + e.getStackTrace());
			}
			if (test.size() != i) {
				fail("Size returned(" + test.size() + ") doesn't match the correct size(" + i + ")");
			}
		}

	}

//
//	@Test
//	void testAddE() {
//		fail("Not yet implemented");
//	}
//
	@Test
	void testAddEInt() throws Exception {
		DoubleLinkedList<Integer> test = new DoubleLinkedList<Integer>();
		ArrayList<Integer> list = new ArrayList<>();

		try {
			test.add(0, -1);
		} catch (IndexOutOfBoundsException e) {

		} catch (Exception e) {
			fail("add return an exception " + e.getStackTrace());
		}

		try {
			test.add(0, 1);
		} catch (IndexOutOfBoundsException e) {

		} catch (Exception e) {
			fail("add return an exception " + e.getStackTrace());
		}

		Random gen = new Random(1);
		for (int i = 0; i < testSize; i++) {
			if (test.size() != i) {
				fail("src.velasco.linkedlist.DoubleLinkedList is size " + test.size() + " when it should be " + i);
			}
			if (test.size() != list.size()) {
				fail("src.velasco.linkedlist.DoubleLinkedList is size " + test.size() + " while list is size " + list.size() + "!");
			}
			int index = gen.nextInt(test.size() + 1);
			Integer item = i;
//			try {
//			System.out.println(index);
			// Remember that the index comes first for listarrays
//			System.out.println(list.size());
			list.add(index, item);
//			System.out.println(list);

			test.add(item, index);
//			System.out.println(test);

//			} catch (Exception e) {
//				System.out.println(list);
//				System.out.println(test);
//				fail("Inserting " + i + " at index " + index + " for a list of size " + test.size()
//						+ " returned an error! " + e.getStackTrace());
//			}
//			System.out.println();
			if (test.getFirst() != test.get(0)) {
				fail("GetFirst returns " + test.getFirst() + " at index " + i + "!");
			}

			if (test.lastSelectedNode.item != test.get(test.lastSelectedIndex)) {
				fail("Item at index " + test.lastSelectedIndex + " should be " + test.get(test.lastSelectedIndex)
						+ " but is instead " + test.lastSelectedNode.item + "!");
			}
		}
		for (int j = 0; j < list.size(); j++) {
			if (list.get(j) != test.get(j)) {
				fail("Items in arraylist(" + list.get(j) + ") and src.velasco.linkedlist.DoubleLinkedList(" + test.get(j) + ") at index " + j
						+ " don't match up!");
			}
		}

	}

//
//	@Test
//	void testAddLast() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testGet() throws Exception {
//		testGetFirst();
//	}

	@Test
	void testGetInt() {
		DoubleLinkedList<Integer> test = new DoubleLinkedList<>();

		try {
			test.get(0);
			fail("get returns an item when it's empty!");
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("getInt threw exception: " + e.getStackTrace());
		}

		for (int i = 0; i < testSize; i++) {
			test.addLast(i);
			if (test.lastSelectedNode.item != test.get(test.lastSelectedIndex)) {
				fail("Item at index " + test.lastSelectedIndex + " should be " + test.get(test.lastSelectedIndex)
						+ " but is instead " + test.lastSelectedNode.item + "!");
			}
		}

		try {
			test.get(-1);
			fail("get returns an item for a negative index!");
		} catch (IndexOutOfBoundsException e) {

		} catch (Exception e) {
			fail("getInt threw exception: " + e.getStackTrace());
		}

		try {
			test.get(testSize);
			fail("get returns an item for an index that's out of bounds!");
		} catch (Exception e) {

		}

		ArrayList<Integer> list = new ArrayList<>();
		for (int seed = 0; seed < 10; seed++) {
			HashSet<Integer> testedSet = new HashSet<>();
			Random generator = new Random(seed);

			test.removeAll();
			list.clear();
			for (int i = 0; i < testSize; i++) {
				test.addLast(i);
				list.add(i);
				if (test.lastSelectedNode.item != test.get(test.lastSelectedIndex)) {
					fail("Item at index " + test.lastSelectedIndex + " should be " + test.get(test.lastSelectedIndex)
							+ " but is instead " + test.lastSelectedNode.item + "!");
				}
			}

			while (testedSet.size() < testSize) {
				int testIndex = generator.nextInt(testSize);

				testedSet.add(testIndex);

				if (test.get(testIndex) != test.get(testIndex)) {
					fail("Getting in the same index returns two different numbers!");
				}

				if (test.get(testIndex) != testIndex) {
					fail("Got the wrong item(" + test.get(testIndex) + ") in the index(" + testIndex + ")!");

				}

				if (test.get(testIndex).compareTo(list.get(testIndex)) != 0) {
					fail("Got item " + test.get(testIndex) + " instead of " + list.get(testIndex) + "!");
				}

			}
		}
	}

	@Test
	void testGetFirst() throws Exception {
		DoubleLinkedList<Integer> test = new DoubleLinkedList<Integer>();

		try {
			test.getFirst();
			fail("getFirst returns an item even when it's empty!");
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("getFirst threw exception: " + e.getStackTrace());
		}

		for (int i = 0; i < testSize; i++) {
			test.addFirst(i);
//			if (test.lastSelectedIndex != i) {
//				fail("LastSelectedIndex(" + test.lastSelectedIndex + ") isn't " + i + " for some reason!");
//			}

			if (test.getFirst() != test.get(0)) {
				fail("GetFirst returns " + test.getFirst() + " at index " + i + "!");
			}

		}
		checkForConnection(test);

	}

	public void checkForConnection(DoubleLinkedList<Integer> test) {
		if (test.size() == 0) {
			return;
		}

		if (test.size() == 1 && test.head != test.tail) {
			fail("Head and tail aren't the same even though there's only one item!");
		}

		DoubleLinkedList<Integer>.Node<Integer> curr = test.head;
		for (int j = 0; j < test.size() - 1; j++) {
			if (curr.next.prev != curr) {
				fail("The list is not properly connected!");
			}
		}

		curr = test.head.next;
		for (int j = 1; j < test.size(); j++) {
			if (curr.prev.next != curr) {
				fail("The list is not properly connected!");
			}
		}

		curr = test.head;

		while (curr != test.tail) {
			if (curr.next == null) {
				fail("Head is not connected to the tail!");
			}
			curr = curr.next;
		}

		while (curr != test.head) {
			if (curr.prev == null) {
				fail("Tail is not connected to the head!");
			}
			curr = curr.prev;
		}
	}

	@Test
	void testGetLast() throws Exception {
		DoubleLinkedList<Integer> test = new DoubleLinkedList<Integer>();

		try {
			test.getLast();
			fail("getFirst returns an item even when it's empty!");
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("getFirst threw exception: " + e.getStackTrace());
		}

		for (int i = 0; i < testSize; i++) {
			test.addLast(i);
			if (test.lastSelectedIndex != i) {
				fail("LastSelectedIndex(" + test.lastSelectedIndex + ") isn't " + i + " for some reason!");
			}

			try {
				if (test.getLast() != test.get(test.size() - 1)) {
					fail("GetLast returns " + test.getLast() + " at index " + i + "!");
				}
			} catch (Exception e) {
				fail("getFirst threw exception: " + e.getStackTrace());
			}
		}

		checkForConnection(test);

	}

	@Test
	void testRemove() {
		DoubleLinkedList<Integer> test = new DoubleLinkedList<Integer>();

		try {
			test.remove(0);
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("remove throws exception " + e.getStackTrace());
		}

		ArrayList<Integer> list = new ArrayList<>();

		for (int i = 0; i < testSize; i++) {
			test.add(i);
			list.add(i);
		}

		try {
			test.remove(testSize);
		} catch (IndexOutOfBoundsException e) {

		} catch (Exception e) {
			fail("remove throws exception " + e.getStackTrace());
		}

		Random gen = new Random(1);
		for (int i = 0; i < testSize; i++) {
			int index = gen.nextInt(test.size());
//			System.out.println(test);
//			System.out.println(list);
			int testItem = test.remove(index);
			int listItem = list.remove(index);
//			System.out.println(test.lastSelectedNode + " " + test.lastSelectedIndex);
//			System.out.println("Index: " + index + " link = " + testItem + " list = " + listItem);
//			System.out.println();
			if (testItem != listItem) {
				fail("Returned " + testItem + " instead of " + listItem + "!");
			}

			if (test.size() > 1 && test.lastSelectedNode.item != test.get(test.lastSelectedIndex)) {
				fail("Item at index " + test.lastSelectedIndex + " should be " + test.get(test.lastSelectedIndex)
						+ " but is instead " + test.lastSelectedNode.item + "!");
			}

		}
	}

	@Test
	void testRemoveAll() {
		DoubleLinkedList<Integer> test = new DoubleLinkedList<Integer>();

		for (int i = 0; i < testSize; i++) {
			test.add(i);
		}
		test.removeAll();

		if (test.size() != 0) {
			fail("Remove all has a size of " + test.size() + "!");
		}

		try {
			test.getFirst();
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("removeAll throws exception " + e.getStackTrace());
		}

		try {
			test.getLast();
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("removeAll throws exception " + e.getStackTrace());
		}

		try {
			test.get(testSize);
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("removeAll throws exception " + e.getStackTrace());
		}
	}

	@Test
	void testToString() throws Exception {
		DoubleLinkedList<Integer> test = new DoubleLinkedList<Integer>();

		if (test.toString().compareTo("[]") != 0) {
			fail(test.toString() + " was returned instead of []!");
		}

		test.add(1);

		if (test.toString().compareTo("[1]") != 0) {
			fail(test.toString() + " was returned instead of [1]!");
		}

		test.addFirst(0);

		if (test.toString().compareTo("[0, 1]") != 0) {
			fail(test.toString() + " was returned instead of [0, 1]!");
		}

		test.addLast(2);

		if (test.toString().compareTo("[0, 1, 2]") != 0) {
			fail(test.toString() + " was returned instead of [0, 1, 2]!");
		}

		test.remove(0);

		if (test.toString().compareTo("[1, 2]") != 0) {
			fail(test.toString() + " was returned instead of [1, 2]!");
		}

		test.removeAll();

		if (test.toString().compareTo("[]") != 0) {
			fail(test.toString() + " was returned instead of []!");
		}

		for (int i = 0; i < 31; i++) {
			test.add(i);
		}

		if (test.toString().compareTo(
				"[0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30]") != 0) {
			fail(test.toString() + " was returned instead of [0, 1, .., 9]!");
		}
	}

	@Test
	void testRemoveLast() {
		DoubleLinkedList<Integer> test = new DoubleLinkedList<Integer>();

		try {
			test.removeLast();
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("removeLast threw exception: " + e.getStackTrace());
		}

		for (int i = 0; i < testSize; i++) {
			test.addLast(i);
			if (test.lastSelectedIndex != i) {
				fail("Last selected index(" + test.lastSelectedIndex + ") isn't " + Math.max(i - 1, 0));
			}
			checkForConnection(test);
		}

		for (int i = testSize - 1; i >= 0; i--) {
			int removedItem = test.removeLast();
			if (removedItem != i) {
				fail("Removed value(" + removedItem + ") is not " + i + "!");
			}
			if (test.lastSelectedIndex != i - 1) {
				fail("Last selected index(" + test.lastSelectedIndex + ") isn't " + (i - 1) + "!");
			}
			checkForConnection(test);
		}

		for (int i = testSize - 1; i >= 0; i--) {
			test.addLast(i);
			checkForConnection(test);
		}

		for (int i = 0; i < testSize; i++) {
			try {
				if (test.removeLast() != i) {
					fail("Wrong value removed!");
				}
			} catch (Exception e) {
				fail("removeLast threw exception: " + e.getStackTrace());
			}
			checkForConnection(test);
		}

		if (test.size() != 0) {
			fail("This list should be empty but is of size " + test.size() + "!");
		}
		if (test.head != null) {
			fail("Empty list doesn't have the head point to null!");
		}
		if (test.tail != null) {
			fail("Empty list doesn't have the tail point to null!");
		}
	}

	@Test
	public void testRemoveFirst() throws Exception {
		DoubleLinkedList<Integer> test = new DoubleLinkedList<Integer>();

		try {
			test.removeFirst();
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("removeFirst threw exception: " + e.getStackTrace());
		}

		for (int i = 0; i < testSize; i++) {
			test.addFirst(i);
			if (test.lastSelectedIndex != i) {
				fail("Last selected index(" + test.lastSelectedIndex + ") isn't " + i);
			}
			checkForConnection(test);
		}

		for (int i = testSize - 1; i >= 0; i--) {
			if (test.removeFirst() != i) {
				fail("Wrong value removed!");
			}
			checkForConnection(test);

		}

		for (int i = testSize - 1; i >= 0; i--) {
			test.addFirst(i);
			checkForConnection(test);
		}

		for (int i = 0; i < testSize; i++) {
			try {
				if (test.removeFirst() != i) {
					fail("Wrong value removed!");
				}
			} catch (Exception e) {
				fail("removeFirst threw exception: " + e.getStackTrace());
			}
			checkForConnection(test);
		}

		if (test.size() != 0) {
			fail("This list should be empty but is of size " + test.size() + "!");
		}
		if (test.head != null) {
			fail("Empty list doesn't have the head point to null!");
		}
		if (test.tail != null) {
			fail("Empty list doesn't have the tail point to null!");
		}

	}

}
