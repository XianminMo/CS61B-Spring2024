import java.util.ArrayList;
import java.util.List;

public class ListExercises {

    /** Returns the total sum in a list of integers */
	public static int sum(List<Integer> L) {
        // TODO: Fill in this function.
        if (L.isEmpty()) {
            return 0;
        }
        int sum = 0;
        for (int i : L) {
            sum += i;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Fill in this function.
        List<Integer> returnList = new ArrayList<>();
        if (L.isEmpty()) {
            return returnList;
        }
        for (int i : L) {
            if (i % 2 == 0) {
                returnList.add(i);
            }
        }
        return returnList;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Fill in this function.
        List<Integer> returnList = new ArrayList<>();
        for (int i : L1) {
            for (int j : L2) {
                if (i == j) {
                    returnList.add(i);
                }
            }
        }
        return returnList;
    }


    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Fill in this function.
        int cnt = 0;
        for (String elem : words) {
            for (int i = 0; i < elem.length(); i++) {
                if (elem.charAt(i) == c) {
                    cnt ++;
                }
            }
        }
        return cnt;
    }
}
