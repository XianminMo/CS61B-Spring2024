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
            for (int i = 1; i < splitLine.length - 1; i++) {
                labels.add(splitLine[i]);
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
        List<Integer> indexes = labelIndexMap.findIndexForWord(word);
        Set<String> hyponymsSet = new HashSet<>();
        for (Integer index : indexes) {
            Iterable<Integer> neighbors = graph.neighbors(index);
            for (Integer neighbor : neighbors) {
                List<String> neighborWords = labelIndexMap.get(neighbor);
                if (neighborWords != null) {
                    hyponymsSet.addAll(neighborWords);
                }
            }
        }
        return hyponymsSet;
    }


}
