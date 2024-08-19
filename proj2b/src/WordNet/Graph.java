package WordNet;

import java.util.*;

// Directed graph
public class Graph {
    private Map<Integer, List<Integer>> adjList;

    public Graph() {
        adjList = new HashMap<>();
    }

    public void addVertex(int vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(int fromVertex, int toVertex) {
        adjList.putIfAbsent(fromVertex, new ArrayList<>());
        adjList.putIfAbsent(toVertex, new ArrayList<>());
        adjList.get(fromVertex).add(toVertex);
    }

    public Iterable<Integer> neighbors(int vertex) {
        List<Integer> neighbors = adjList.getOrDefault(vertex, new ArrayList<>());
        Collections.sort(neighbors); // ensure order
        return neighbors;
    }

}

