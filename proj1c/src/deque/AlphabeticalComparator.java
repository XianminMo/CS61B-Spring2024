package deque;

import java.util.Comparator;

public class AlphabeticalComparator implements Comparator<String> {
    @Override
    public int compare(String o1, String o2) {
        return o1.compareTo(o2);
    }
}
