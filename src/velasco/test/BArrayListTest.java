package src.velasco.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.velasco.array.BArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BArrayListTest {

    BArrayList<Integer> l;
    @BeforeEach
    void setUp() {
        l = new BArrayList<>();
        for(int i = 0; i < 10; i++){
            l.add(i);
        }
    }

    @Test
    void getFirst() {
assertEquals(l.getFirst(), 0);
    }

    @Test
    void getLast() {
        assertEquals(l.getLast(), 9);
    }

    @Test
    void removeFirst() {
        assertEquals(l.removeFirst(), 0);
        assertEquals(l.getFirst(), 1);
        assertEquals(l.size(), 9);
    }

    @Test
    void removeLast() {
        assertEquals(l.removeLast(), 9);
        assertEquals(l.getLast(), 8);
        assertEquals(l.size(), 9);

    }
}