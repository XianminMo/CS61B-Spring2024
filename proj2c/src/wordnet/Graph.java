package wordnet;

import java.util.*;

// Directed graph
public class Graph {
    private final Map<Integer, List<Integer>> adjList;
    private final Map<Integer, List<Integer>> reverseAdjList;

    public Graph() {
        this.adjList = new HashMap<>();
        this.reverseAdjList = new HashMap<>();
    }

    public void addVertex(int vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
        reverseAdjList.putIfAbsent(vertex, new ArrayList<>());
    }

    public void addEdge(int fromVertex, int toVertex) {
        adjList.putIfAbsent(fromVertex, new ArrayList<>());
        adjList.putIfAbsent(toVertex, new ArrayList<>());
        adjList.get(fromVertex).add(toVertex);
        reverseAdjList.putIfAbsent(fromVertex, new ArrayList<>());
        reverseAdjList.putIfAbsent(toVertex, new ArrayList<>());
        reverseAdjList.get(toVertex).add(fromVertex);
    }

    public Iterable<Integer> neighbors(int vertex) {
        List<Integer> neighbors = adjList.getOrDefault(vertex, new ArrayList<>());
        Collections.sort(neighbors); // ensure order
        return neighbors;
    }

    public Iterable<Integer> reverseNeighbors(int vertex) {
        List<Integer> reverseNeighbors = reverseAdjList.getOrDefault(vertex, new ArrayList<>());
        Collections.sort(reverseNeighbors); // ensure order
        return reverseNeighbors;
    }
}

