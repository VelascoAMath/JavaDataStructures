package src.velasco.linkedlist.linkedlist;

import java.util.Iterator;

public class CircularLinkedList<E> implements Iterable<E>{


    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {

            Node<E> curr = head;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public E next() {
                E item = curr.item;
                curr = curr.next;
                return item;
            }
        };
    }

    private static class Node<T> {
        Node<T> next;
        T item;

        public Node(T item) {
            this.item = item;
        }

        public Node() {

        }
    }

    private Node<E> head;
    private int size;

    public void addFirst(E item){
        if(head == null){
            head = new Node<>(item);
            head.next = head;
        } else {
            Node<E> newNode = new Node<>(head.item);
            newNode.next = head.next;
            head.next = newNode;
            head.item = item;
        }

        size++;
    }

    public void add(E item){
        addLast(item);
    }

    private void addLast(E item) {
        if (head == null) {
            head = new Node<>(item);
            head.next = head;
        } else {
            Node<E> newNode = new Node<>(head.item);
            head.item = item;
            newNode.next = head.next;
            head.next = newNode;
            head = head.next;

        }
        size++;
    }

    public int getSize() {
        return size;
    }

    public String toString(){
        if (size == 0)
            return "[]";
        StringBuilder sb = new StringBuilder("[");
        Node<E> curr = head;
        do {
            sb.append(curr.item);
            sb.append(", ");
            curr = curr.next;
        }while(curr != head);
        sb.delete(sb.length()-2, sb.length());
        sb.append(']');
        return sb.toString();
    }
    public static void main(String[] args) {
        CircularLinkedList<Integer> l = new CircularLinkedList<>();
        for(int i = 0; i < 10; i++)
            l.add(i);

        System.out.println(l);

        for(Integer i: l){
            System.out.println(i);
        }
    }
}
