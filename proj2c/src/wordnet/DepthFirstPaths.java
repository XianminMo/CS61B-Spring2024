package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class DepthFirstPaths {
    private final Set<Integer> marked;
    private final int start;
    private final Map<Integer, Integer> edgeTo;
    private final boolean reverse;

    public DepthFirstPaths(Graph graph, int vertex, boolean reverse) {
        this.marked = new HashSet<>();
        this.start = vertex;
        this.edgeTo = new HashMap<>(); // 使用Map来动态存储已访问的父节点
        this.reverse = reverse; // 标志是否反向搜索
        dfs(graph, start);
    }

    // recursive depth first search vertex, reachable vertices are stored in marked set
    private void dfs(Graph graph, int vertex) {
        marked.add(vertex); // 现在是前序的深搜，若想改为后序的深搜只需要将这一步放在递归后面，也就是先递归访问完所有邻居，再将自己标记为已访问
        Iterable<Integer> neighbors = reverse ? graph.reverseNeighbors(vertex) : graph.neighbors(vertex);

        for (Integer neighbor : neighbors) {
            if (!marked.contains(neighbor)) {
                edgeTo.put(neighbor, vertex);
                dfs(graph, neighbor); // recursive
            }
        }
    }

    // 返回所有从起始节点出发可到达的节点
    public Set<Integer> reachableVertices() {
        return marked;
    }

    // 以下不是Proj2C必须的，只是为了锻炼自己熟悉图而新增加的方法
    // 返回从起始节点到指定节点的路径
    public List<Integer> pathTo (int vertex) {
        if (!hasPathTo(vertex)) {
            return null;
        }
        List<Integer> path = new ArrayList<>();
        for (int x = vertex; x != start;  x = edgeTo.get(x)) {
            path.add(x);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    // 判断是否存在从起始节点到目标节点的路径
    public boolean hasPathTo (int vertex) {
        return marked.contains(vertex);
    }
}
