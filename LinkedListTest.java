import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;

import org.junit.jupiter.api.Test;

class LinkedListTest {
	int testSize = 100;

//	@Test
//	void testLinkedList() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testIsEmpty() {
//		fail("Not yet implemented");
//	}

	@Test
	void testSize() {
		LinkedList<Integer> test = new LinkedList<Integer>();

		for (int i = 0; i < testSize; i++) {
			if (test.size() != i) {
				fail("Size returned(" + test.size() + ") doesn't match the correct size(" + i + ")");
			}
			test.add(i);
			if (test.size() != i + 1) {
				fail("Size returned(" + test.size() + ") doesn't match the correct size(" + i + ")");
			}
		}

		for (int i = testSize - 1; i >= 0; i--) {
			try {
				test.remove(i);
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
//	@Test
//	void testAddEInt() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testAddLast() {
//		fail("Not yet implemented");
//	}
//
	@Test
	void testGet() {
		testGetFirst();
	}

	@Test
	void testGetInt() {
		LinkedList<Integer> test = new LinkedList<>();

		
		try {
			test.get(0);
			fail("get returns an item when it's empty!");
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("getInt threw exception: " + e.getStackTrace());
		}

		for (int i = 0; i < testSize; i++) {
			test.addLast(i);
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

		HashSet<Integer> testedSet = new HashSet<>();
		Random generator = new Random();

		while (testedSet.size() < testSize) {
			int testIndex = generator.nextInt(testSize);

			testedSet.add(testIndex);
			try {
				if (test.get(testIndex) != testIndex) {
					fail("Got the wrong item(" + test.get(testIndex) + ") in the index(" + testIndex + ")!");

				}
			} catch (Exception e) {
				fail("get threw exception: " + e.getStackTrace());
			}
		}

		test = new LinkedList<>();

		for (int i = 0; i < testSize; i++) {
			test.addLast(testSize - i);
		}

		while (testedSet.size() < testSize) {
			int testIndex = generator.nextInt(testSize);

			testedSet.add(testIndex);
			try {
				if (test.get(testIndex) != testSize - testIndex) {
					fail("Got the wrong item(" + test.get(testIndex) + ") in the index(" + testIndex + ")!");

				}
			} catch (Exception e) {
				fail("get threw exception: " + e.getStackTrace());
			}
		}
	}

	@Test
	void testGetFirst() {
		LinkedList<Integer> test = new LinkedList<Integer>();

		try {
			test.getFirst();
			fail("getFirst returns an item even when it's empty!");
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("getFirst threw exception: " + e.getStackTrace());
		}

		for (int i = 0; i < testSize; i++) {
			test.addFirst(i);
			try {
				if (test.getFirst() != test.get(0)) {
					fail("GetFirst returns " + test.getFirst() + " at index " + i + "!");
				}
			} catch (Exception e) {
				fail("getFirst threw exception: " + e.getStackTrace());
			}
		}
	}

	@Test
	void testRemove() {
		LinkedList<Integer> test = new LinkedList<Integer>();

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

		Random gen = new Random();
		for (int i = 0; i < testSize; i++) {
			int index = gen.nextInt(test.size());
			try {
				if (test.remove(index) != list.remove(index)) {
					fail("Returned the wrong item!");
				}
			} catch (Exception e) {
				fail("remove throws exception " + e.getStackTrace());
			}
		}
	}

	@Test
	void testRemoveAll() {
		LinkedList<Integer> test = new LinkedList<Integer>();

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

//	@Test
//	void testToString() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	void testClear() {
//		fail("Not yet implemented");
//	}

	@Test
	void testRemoveLast() {
		LinkedList<Integer> test = new LinkedList<Integer>();

		try {
			test.removeLast();
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("removeLast threw exception: " + e.getStackTrace());
		}

		for (int i = 0; i < testSize; i++) {
			test.addLast(i);
		}

		for (int i = testSize - 1; i >= 0; i--) {
			try {
				if (test.removeLast() != i) {
					fail("Wrong value removed!");
				}
			} catch (Exception e) {
				fail("removeLast threw exception: " + e.getStackTrace());
			}
		}

		for (int i = testSize - 1; i >= 0; i--) {
			test.addLast(i);
		}

		for (int i = 0; i < testSize; i++) {
			try {
				if (test.removeLast() != i) {
					fail("Wrong value removed!");
				}
			} catch (Exception e) {
				fail("removeLast threw exception: " + e.getStackTrace());
			}
		}
	}

	@Test
	public void testRemoveFirst() {
		LinkedList<Integer> test = new LinkedList<Integer>();

		try {
			test.removeFirst();
		} catch (NoSuchElementException e) {

		} catch (Exception e) {
			fail("removeFirst threw exception: " + e.getStackTrace());
		}

		for (int i = 0; i < testSize; i++) {
			test.addFirst(i);
		}

		for (int i = testSize - 1; i >= 0; i--) {
			try {
				if (test.removeFirst() != i) {
					fail("Wrong value removed!");
				}
			} catch (Exception e) {
				fail("removeLast threw exception: " + e.getStackTrace());
			}
		}

		for (int i = testSize - 1; i >= 0; i--) {
			test.addFirst(i);
		}

		for (int i = 0; i < testSize; i++) {
			try {
				if (test.removeFirst() != i) {
					fail("Wrong value removed!");
				}
			} catch (Exception e) {
				fail("removeFirst threw exception: " + e.getStackTrace());
			}
		}

	}

}
