package wordnet;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private Graph graph;
    private myMap labelIndexMap;

    private class myMap extends HashMap<Integer, List<String>> {
        public myMap() {
            super();
        }

        // 扩展了 Map 的一个方法，可以通过索引找到特定的单词（value是一个 words list, 并且一个 word 在不同的 value 中可能会重复出现）
        public List<Integer> findIndexForWord(String word) {
            List<Integer> indexes = new ArrayList<>();
            for (Entry<Integer, List<String>> entry : this.entrySet()) {
                int index = entry.getKey();
                List<String> words = entry.getValue();
                if (words.contains(word)) {
                    indexes.add(index);
                }
            }
            return indexes;
        }

    }

    public WordNet(String synsetsFilename, String hyponymsFilename) {
        this.graph = new Graph(); // 提供存放的单词的基础结构
        this.labelIndexMap = new myMap(); // 存放 mapping from index to words list

        loadSynsetsFile(synsetsFilename, labelIndexMap);
        loadHyponymsFile(hyponymsFilename, graph);
    }

    private void loadSynsetsFile(String synsetsFilename, Map<Integer, List<String>> labelIndexMap) {
        In in = new In(synsetsFilename);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            int index = Integer.parseInt(splitLine[0]);
            String[] words = splitLine[1].split(" ");
            List<String> labels = new ArrayList<>(Arrays.asList(words));
            labelIndexMap.putIfAbsent(index, labels);
        }
    }

    private void loadHyponymsFile(String hyponymsFilename, Graph graph) {
        In in = new In(hyponymsFilename);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            int vertex = Integer.parseInt(splitLine[0]);
            for (int i = 1; i < splitLine.length; i++) {
                graph.addEdge(vertex, Integer.parseInt(splitLine[i]));
            }
        }
    }

    public Set<String> hyponyms(String word) {
        Set<String> hyponymsSet = new HashSet<>();
        List<Integer> indexes = labelIndexMap.findIndexForWord(word);
        for (Integer index : indexes) {
            DepthFirstPaths dfs = new DepthFirstPaths(graph, index, false);
            Set<Integer> reachableVertices = dfs.reachableVertices(); // depth first search on every reachable vertexes
            for (Integer vertex : reachableVertices) {
                List<String> labels = labelIndexMap.get(vertex);
                if (labels != null) {
                    hyponymsSet.addAll(labels);
                }
            }
        }
        return hyponymsSet;
    }

    public Set<String> ancestors(String word) {
        Set<String> ancestorsSet = new HashSet<>();
        List<Integer> indexes = labelIndexMap.findIndexForWord(word);
        for (Integer index : indexes) {
            DepthFirstPaths dfs = new DepthFirstPaths(graph, index, true);
            Set<Integer> reachableVertices = dfs.reachableVertices(); // reverse depth first search on every reachable vertexes
            for (Integer vertex : reachableVertices) {
                List<String> labels = labelIndexMap.get(vertex);
                if (labels != null) {
                    ancestorsSet.addAll(labels);
                }
            }
        }
        return ancestorsSet;
    }


}
