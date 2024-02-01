public class List1Exercises {
    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. L is not allowed
     * to change.
     */
    public static IntList incrList(IntList L, int x) {
        /* Your code here. */
        IntList Q = new IntList(L.get(L.size() - 1) + x, null);
        for (int i = L.size() - 2; i >= 0; i--) {
            Q = new IntList(L.get(i) + x, Q);
        }
        return Q;
    }

    /**
     * Returns an IntList identical to L, but with
     * each element incremented by x. Not allowed to use
     * the 'new' keyword.
     */
    public static IntList dincrList(IntList L, int x) {
        /* Your code here. */
        IntList point = L;
        while (point != null) {
            point.first = point.first + x;
            point = point.rest;
        }
        return L;
    }

    public static class IntList {
        public int first;
        public IntList rest;

        public IntList(int f, IntList r) {
            first = f;
            rest = r;
        }

        public int size() {
            if (rest == null) {
                return 1;
            }
            return 1 + rest.size();
        }

        public int get(int i) {
            if (i == 0) {
                return first;
            }
            return rest.get(i - 1);
        }
    }

    public static void main(String[] args) {
        IntList L = new IntList(5, null);
        L = new IntList(7, L);
        L = new IntList(9, L);

//        System.out.println(L.size());

        // Test your answers by uncommenting. Or copy and paste the
        // code for incrList and dincrList into IntList.java and
        // run it in the visualizer.
        System.out.println(L.get(0));
        System.out.println(L.get(1));
        System.out.println(L.get(2));
        IntList L1 = incrList(L, 3);
        IntList L2 = dincrList(L, 3);
        System.out.println(L1.get(0));
        System.out.println(L1.get(1));
        System.out.println(L1.get(2));
        System.out.println(L2.get(0));
        System.out.println(L2.get(1));
        System.out.println(L2.get(2));
        System.out.println(L.get(0));
        System.out.println(L.get(1));
        System.out.println(L.get(2));
    }
}