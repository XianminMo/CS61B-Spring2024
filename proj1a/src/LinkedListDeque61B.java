import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList; // import the ArrayList class

public class LinkedListDeque61B<T> implements Deque61B<T> {

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
}
