package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {

    private class LinkedListIterator implements Iterator<T>{

        private DoubleNode trackNode;

        public LinkedListIterator() {
            trackNode = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return trackNode != sentinel;
        }

        @Override
        public T next() {
            T returnItem = trackNode.item;
            trackNode = trackNode.next;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class DoubleNode {
        T item;
        DoubleNode prev;
        DoubleNode next;
        DoubleNode(DoubleNode prev, T item, DoubleNode next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }

    private DoubleNode sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new DoubleNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        DoubleNode first = new DoubleNode(sentinel, item, sentinel.next);
        sentinel.next.prev = first;
        sentinel.next = first;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        DoubleNode last = new DoubleNode(sentinel.prev, item, sentinel);
        sentinel.prev.next = last;
        sentinel.prev = last;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        DoubleNode p = sentinel.next;
        while (p != sentinel) {
            returnList.add(p.item);
            p = p.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return returnItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T returnItem = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size -= 1;
        return returnItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size) {
            return null;
        }
        T returnItem = null;
        int cnt = 0;
        DoubleNode p = sentinel.next;
        while (p != sentinel) {
            if (cnt == index) {
                returnItem = p.item;
            }
            p = p.next;
            cnt += 1;
        }
        return returnItem;
    }

    @Override
    // LinkedListDeque is not recursive, so we need a helper method
    public T getRecursive(int index) {
        return getRecursive(sentinel.next, index);
    }

    // Helper method for getRecursive(int index)
    public  T getRecursive(DoubleNode p, int index) {
        if (index < 0 || index >= this.size) {
            return null;
        }
        if (index == 0) {
            return p.item;
        }
        return getRecursive(p.next, index - 1);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof LinkedListDeque61B<?> otherList) {
            if (this.size != otherList.size()) {
                return false;
            }
            int i = 0;
            for (T item : this) {
                if (item != otherList.get(i)) {
                    return false;
                }
                i += 1;
            }
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Deque61B<Integer> L1 = new LinkedListDeque61B<>();
        Deque61B<Integer> L2 = new LinkedListDeque61B<>();
        L1.addLast(1);
        L1.addFirst(3);
        L1.addLast(2);
        L2.addLast(1);
        L2.addFirst(3);
        L2.addLast(2);
        if (L1.equals(L2)) {
            System.out.println("L1 == L2");
        }
    }
}
