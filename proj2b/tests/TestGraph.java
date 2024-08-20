import WordNet.Graph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static com.google.common.truth.Truth.assertThat;

public class TestGraph {
    @Test
    public void testAddVertex() {
        Graph graph = new Graph();
        for (int i = 0; i < 9; i++) {
            graph.addVertex(i);
        }
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(1, 4);
        graph.addEdge(2, 5);
        graph.addEdge(4, 5);
        graph.addEdge(4, 3);
        graph.addEdge(5, 6);
        graph.addEdge(5, 8);
        graph.addEdge(6, 7);

        assertThat(graph.neighbors(5)).isEqualTo(Arrays.asList(6,8));
        assertThat(graph.neighbors(4)).isEqualTo(Arrays.asList(3,5));
    }
}
