import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        // TODO: Fill in this function.
        Map<Character, Integer> returnMap = new HashMap<>();
        for (int i = 1; i <= 26; i++) {
            returnMap.put((char)('a' + i - 1), i);
        }
        return returnMap;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        // TODO: Fill in this function.
        Map<Integer, Integer> returnMap = new HashMap<>();
        if (nums.isEmpty()) {
            return returnMap;
        }
        for (int i : nums) {
            returnMap.put(i, (int) Math.pow(i, 2));
        }
        return returnMap;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        // TODO: Fill in this function.
        Map<String, Integer> returnMap = new HashMap<>();
        if (words.isEmpty()) {
            return returnMap;
        }
        for (String elem : words) {
            int cnt = 0;
            for (int i = 0; i < words.size(); i++) {
                if (elem.equals(words.get(i))) {
                    cnt++;
                }
            }
            returnMap.put(elem, cnt);
        }

        return returnMap;
    }
}
