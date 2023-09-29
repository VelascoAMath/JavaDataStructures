package src.velasco.array;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListMapTest {

    ListMap<Integer, Integer> l;
    final int SIZE = 1000;

    @BeforeEach
    void setUp() {
        l = new ListMap<>();
    }

    @Test
    void size() {

        for(int i = 0; i < SIZE; i++){
            assertEquals(l.size(), i);
            l.put(i, i);
        }

        assertEquals(l.size(), SIZE);

        for(int i = SIZE-1; i >= 0; i--){
            l.remove(i);
            assertEquals(l.size(), i);
        }
    }

    @Test
    void isEmpty() {
    }

    @Test
    void containsKey() {
    }

    @Test
    void containsValue() {
    }

    @Test
    void get() {
    }

    @Test
    void put() {
    }

    @Test
    void remove() {
        for(int i = 0; i < SIZE; i++){
            l.put(i, i + 1);
        }

        for(int i = 0; i < SIZE; i++){
            assertEquals(i + 1, l.remove(i));
        }

    }

    @Test
    void putAll() {
    }

    @Test
    void clear() {
    }

    @Test
    void keySet() {
    }

    @Test
    void values() {
    }

    @Test
    void entrySet() {
    }
}