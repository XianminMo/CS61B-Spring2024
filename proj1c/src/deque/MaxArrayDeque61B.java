package deque;

import org.apache.hc.core5.annotation.Internal;
import org.eclipse.jetty.websocket.client.masks.Masker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    private Comparator<T> c;

    public MaxArrayDeque61B(Comparator<T> c) {
        this.c = c;
    }

    public T max() {
        if (isEmpty()) {
            return null;
        }
        int compareNum = 0;
        int maxIndex = 0;
        for (int i = 0; i < size() - 1; i++) {
            compareNum = c.compare(this.get(i + 1), this.get(maxIndex));
            if (compareNum > 0) {
                maxIndex = i + 1;
            }
        }
        return this.get(maxIndex);

    }

    public T max(Comparator<T> c) {
        if (isEmpty()) {
            return null;
        }
        int compareNum = 0;
        int maxIndex = 0;
        for (int i = 0; i < size() - 1; i++) {
            compareNum = c.compare(this.get(maxIndex), this.get(i + 1));
            if (compareNum > 0) {
                maxIndex = i + 1;
            }
        }
        return this.get(maxIndex);
    }

    public static void main(String[] args) {
        MaxArrayDeque61B<Integer> L = new MaxArrayDeque61B<Integer>(Comparator.naturalOrder());
        L.addLast(1);
        L.addLast(99);
        L.addLast(21);
        L.addLast(51);
        System.out.println(L.max());
        AlphabeticalComparator act = new AlphabeticalComparator();
        MaxArrayDeque61B<String> L2 = new MaxArrayDeque61B<String>(act);
        L2.addLast("a");
        L2.addLast("c");
        L2.addLast("f");
        L2.addLast("b");
        L2.addLast("z");
        System.out.println(L2.max());
    }
}
