import java.util.Arrays;

public class UnionFind {
    private int[] ufSet;


    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        ufSet = new int[N];
        Arrays.fill(ufSet, -1);
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        int root = find(v);
        return - ufSet[root];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        return ufSet[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v >= ufSet.length) {
            throw new IllegalArgumentException("IllegalArgument!");
        }
        if (parent(v) < 0) {
            return v;
        }else {
            ufSet[v] = find(parent(v)); // path compression
        }
        return parent(v);
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        if (connected(v1, v2)) {
            return;
        }
        int size1 = sizeOf(v1);
        int size2 = sizeOf(v2);
        int root1 = find(v1);
        int root2 = find(v2);

        if (size1 <= size2) {
            ufSet[root1] = root2;
            ufSet[root2] = - (size1 + size2);
        }else {
            ufSet[root2] = root1;
            ufSet[root1] = - (size1 + size2);
        }
    }

}
