package deque;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int First;
    private int Last;
    private int nextFirst;
    private int nextLast;


    public ArrayDeque61B() {
        items = (T[])(new Object[8]);
        size = 0;
        First = 0;
        Last = 0;
        nextFirst = 6;
        nextLast = 7;
    }

    private int adjustIndex(int index) {
        if (index == items.length) {
            index = 0;
        }
        return index;
    }
    private void resize(int capacity) {
        T[] a = (T[])(new Object[capacity]);
        int index = First;
        for (int i = 0; i < size; i++) {
            index = adjustIndex(index);
            a[i] = items[index];
            index += 1;
        }
        items = a;
    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
            nextFirst = items.length - 1;
            nextLast = size;
            Last = size - 1;
        }
        items[nextFirst] = x;
        size += 1;
        First = nextFirst;
        if (size == 1) {
            Last = First;
        }
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        }
        else {
            nextFirst -= 1;
        }
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
            nextFirst = items.length - 1;
            First = 0;
            nextLast = size;
        }
        items[nextLast] = x;
        size += 1;
        Last = nextLast;
        if (size == 1) {
            First = Last;
        }
        if (nextLast == items.length - 1) {
            nextLast = 0;
        }
        else {
            nextLast += 1;
        }
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        int index = First;

        for (int i = 0; i < size; i++) {
            index = adjustIndex(index);
            returnList.add(items[index]);
            index += 1;
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if ((float)(size) / (float) (items.length) < 0.25) {
            int newLength = items.length / 2;
            resize(newLength);
            First = 0;
            nextLast = size;
            Last = size - 1;
        }
        T removeValue = items[First];
        items[First] = null;
        size -= 1;
        nextFirst = First;
        if (First == items.length - 1) {
            First = 0;
        }
        else {
            First += 1;
        }
        return removeValue;
    }

    @Override
    public T removeLast() {
        if ((float)(size) / (float) (items.length) < 0.25) {
            int newLength = items.length / 2;
            resize(newLength);
            First = 0;
            nextFirst = 1;
            Last = size - 1;
        }
        T removeValue = items[Last];
        items[Last] = null;
        size -= 1;
        nextLast = Last;
        if (Last == 0) {
            Last = items.length - 1;
        }
        else {
            Last -= 1;
        }
        return removeValue;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        T returnValue = null;
        int actualIndex = First + index;
        if (actualIndex > items.length - 1) {
            actualIndex = actualIndex - items.length;
        }
        returnValue = items[actualIndex];
        return returnValue;
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }

    private class ArrayDequeIterator implements Iterator<T> {

        private int wizPos;
        public ArrayDequeIterator() {
            wizPos = First;
        }

        @Override
        public boolean hasNext() {
            wizPos = adjustIndex(wizPos);
            return wizPos != nextLast;
        }

        @Override
        public T next() {
            T returnItem = items[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other instanceof ArrayDeque61B<?> otherList) {
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
        Deque61B<Integer> L1 = new ArrayDeque61B<>();
        Deque61B<Integer> L2 = new ArrayDeque61B<>();
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
