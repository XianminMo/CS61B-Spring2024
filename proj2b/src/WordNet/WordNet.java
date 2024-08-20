package WordNet;

import edu.princeton.cs.algs4.In;

import java.util.*;

public class WordNet {
    private Graph graph;
    private myMap labelIndexMap;

    private class myMap extends HashMap<Integer, List<String>> {
        public myMap() {
            super();
        }

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
        graph = new Graph();
        labelIndexMap = new myMap();

        loadSynsetsFile(synsetsFilename, labelIndexMap);
        loadHyponymsFile(hyponymsFilename, graph);
    }

    private void loadSynsetsFile(String synsetsFilename, Map<Integer, List<String>> labelIndexMap) {
        In in = new In(synsetsFilename);
        while (!in.isEmpty()) {
            String nextLine = in.readLine();
            String[] splitLine = nextLine.split(",");
            int index = Integer.parseInt(splitLine[0]);
            List<String> labels = new ArrayList<>();
            String[] words = splitLine[1].split(" ");
            for (String word : words) {
                labels.add(word);
            }
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
            DepthFirstPaths dfs = new DepthFirstPaths(graph, index);
            Set<Integer> reachableVertexes = dfs.reachableVertexes();
            for (Integer vertex : reachableVertexes) {
                List<String> labels = labelIndexMap.get(vertex);
                if (labels != null) {
                    hyponymsSet.addAll(labels);
                }
            }
        }
        return hyponymsSet;
    }


}
