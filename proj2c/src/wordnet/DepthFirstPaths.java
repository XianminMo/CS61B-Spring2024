package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.HashSet;
import java.util.Set;

public class DepthFirstPaths {
    private Set<Integer> marked;
    private boolean reverse;

    public DepthFirstPaths(Graph graph, int vertex, boolean reverse) {
        this.marked = new HashSet<>();
        this.reverse = reverse; // 标志是否反向搜索
        dfs(graph, vertex);
    }

    // recursive depth first search vertex, reachable vertices are stored in marked set
    private void dfs(Graph graph, int vertex) {
        marked.add(vertex);
        Iterable<Integer> neighbors;
        if (reverse) {
            neighbors = graph.reverseNeighbors(vertex); // 反向搜索
        } else {
            neighbors = graph.neighbors(vertex); // 正向搜索
        }
        for (Integer neighbor : neighbors) {
            if (!marked.contains(neighbor)) {
                dfs(graph, neighbor); // recursive
            }
        }
    }

    public Set<Integer> reachableVertices() {
        return marked;
    }
}
