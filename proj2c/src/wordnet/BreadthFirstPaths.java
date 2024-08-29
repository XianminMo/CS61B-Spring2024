package wordnet;
import edu.princeton.cs.algs4.In;

import java.util.*;

public class BreadthFirstPaths {
    private final Set<Integer> marked;
    private final Map<Integer, Integer> edgeTo; // 记录该节点的父节点，Mapping vertex -> parent
    private final int start;

    BreadthFirstPaths(Graph graph, int vertex) {
        this.marked = new HashSet<>();
        this.edgeTo = new HashMap<>();
        this.start = vertex;
        bfs(graph, start);
    }

    private void bfs(Graph graph, int vertex) {
        Queue<Integer> fringe = new LinkedList<>(); // 用队列来存储待访问的节点
        fringe.offer(start); // 将起始节点加入队列
        marked.add(start); // 标记起始节点已访问

        while (!fringe.isEmpty()) {
            int v = fringe.poll(); // 取出队列中的第一个节点
            for (int neighbor : graph.neighbors(v)) { // 遍历所有邻居
                if (!marked.contains(neighbor)) {
                    fringe.offer(neighbor); // 将该未访问的邻居节点加入队列
                    edgeTo.put(neighbor, v); // 记录路径
                    marked.add(neighbor); // 标记邻居节点已访问
                }
            }
        }
    }

    // 返回所有从起始节点出发可到达的节点
    public Set<Integer> reachableVertices() {
        return marked;
    }

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
        Collections.reverse(path); // 反转路径
        return path;
    }

    // 判断是否存在从起始节点到目标节点的路径
    public boolean hasPathTo (int vertex) {
        return marked.contains(vertex);
    }

}
