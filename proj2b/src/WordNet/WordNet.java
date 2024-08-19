package WordNet;

import edu.princeton.cs.algs4.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {
    private Graph graph;
    private Map<Integer, List<String>> labelIndexMap;

    public WordNet(String synsetsFilename, String hyponymsFilename) {
        graph = new Graph();
        labelIndexMap = new HashMap<>();

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

    public List<String> hyponyms(String word) {
        List<Integer> vertexs = new ArrayList<>();
    }


}
