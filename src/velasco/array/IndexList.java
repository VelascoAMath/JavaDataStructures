package src.velasco.array;

import src.velasco.other.IndexCollection;

import java.util.Iterator;

public class IndexList implements IndexCollection {
    BArrayList<Integer> indexList;

    IndexList(){
        indexList = new BArrayList<>();
    }

    @Override
    public boolean contains(int i) {
        return indexList.contains(i);
    }

    @Override
    public void add(int i) {
        if(!contains(i)){
            indexList.add(i);
        }
    }

    @Override
    public int remove(int i) {
        return indexList.remove(i);
    }

    @Override
    public Iterator<Integer> iterator() {
        return indexList.iterator();
    }

    public int get(int i){
        return indexList.get(i);
    }
}
