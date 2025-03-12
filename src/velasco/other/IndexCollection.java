package src.velasco.other;

public interface IndexCollection extends Iterable<Integer> {

    boolean contains(int i);
    void add(int i);

    int remove(int i);


}
