import java.util.ArrayList;
import java.util.List;

public class JavaExercises {

    /**
     * Returns an array [1, 2, 3, 4, 5, 6]
     */
    public static int[] makeDice() {
        // TODO: Fill in this function.
        int[] array = {1, 2, 3, 4, 5, 6};
        return array;
    }

    /**
     * Returns the order depending on the customer.
     * If the customer is Ergun, return ["beyti", "pizza", "hamburger", "tea"].
     * If the customer is Erik, return ["sushi", "pasta", "avocado", "coffee"].
     * In any other case, return an empty String[] of size 3.
     */
    public static String[] takeOrder(String customer) {
        // TODO: Fill in this function.
        String[] returnStrings1 = new String[4];
        if (customer.equals("Ergun")) {
            returnStrings1[0] = ("beyti");
            returnStrings1[1] = ("pizza");
            returnStrings1[2] = ("hamburger");
            returnStrings1[3] = ("tea");
            return returnStrings1;
        } else if (customer.equals("Erik")) {
            returnStrings1[0] = ("sushi");
            returnStrings1[1] = ("pasta");
            returnStrings1[2] = ("avocado");
            returnStrings1[3] = ("coffee");
            return returnStrings1;
        }
        else {
            return new String[3];
        }
    }

    /**
     * Returns the positive difference between the maximum element and minimum element of the given array.
     * Assumes array is nonempty.
     */
    public static int findMinMax(int[] array) {
        // TODO: Fill in this function.
        int max = array[0];
        int min = array[0];
        for(int i = 1; i < array.length; i++) {
            if (max < array[i]) {
                max = array[i];
            }
            if (min > array[i]) {
                min = array[i];
            }
        }
        return max - min;
    }

    /**
     * Uses recursion to compute the hailstone sequence as a list of integers starting from an input number n.
     * Hailstone sequence is described as:
     * - Pick a positive integer n as the start
     * - If n is even, divide n by 2
     * - If n is odd, multiply n by 3 and add 1
     * - Continue this process until n is 1
     */
    public static List<Integer> hailstone(int n) {
        return hailstoneHelper(n, new ArrayList<>());
    }

    private static List<Integer> hailstoneHelper(int x, List<Integer> list) {
        // TODO: Fill in this function.
        if (x == 1) {
            list.add(x);
            return list;
        }
        if (x % 2 == 0) {
            list.add(x);
            x = x / 2;
        }
        else {
            list.add(x);
            x = 3 * x + 1;
        }

        return hailstoneHelper(x, list);
    }

}
