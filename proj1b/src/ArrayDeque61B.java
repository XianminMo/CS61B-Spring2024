import java.util.ArrayList;
import java.util.List;

public class ArrayDeque61B<T> implements Deque61B<T>{

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

    @Override
    public void addFirst(T x) {
        items[nextFirst] = x;
        size += 1;
        First = nextFirst;
        if (nextFirst == 0) {
            nextFirst = items.length - 1;
        }
        else {
            nextFirst -= 1;
        }
    }

    @Override
    public void addLast(T x) {
        items[nextLast] = x;
        size += 1;
        Last = nextLast;
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
        int cnt = 0;
        int index = First;

        while(cnt < size) {
            if (index == items.length) {
                index = 0;
            }
            returnList.add(items[index]);
            index += 1;
            cnt += 1;
        }

        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
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
        return null;
    }

    public static void main(String[] args) {
        Deque61B<Integer> L = new ArrayDeque61B<>();
        L.addFirst(1);
    }
}
