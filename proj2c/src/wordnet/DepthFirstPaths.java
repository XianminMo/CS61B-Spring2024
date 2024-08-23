package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.HashSet;
import java.util.Set;

public class DepthFirstPaths {
    private Set<Integer> marked;

    public DepthFirstPaths(Graph graph, int vertex) {
        marked = new HashSet<>();
        dfs(graph, vertex);
    }

    private void dfs(Graph graph, int vertex) {
        marked.add(vertex);
        for (Integer neighbor : graph.neighbors(vertex)) {
            if (!marked.contains(neighbor)) {
                dfs(graph, neighbor); // recursive
            }
        }
    }

    public Set<Integer> reachableVertexes() {
        return marked;
    }
}
