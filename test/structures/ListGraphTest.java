package structures;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ListGraphTest {
    ListGraph<Integer> listGraph;

    public void setupStage2() {
        listGraph = new ListGraph<>(true);

        listGraph.addVertex(1);
        listGraph.addVertex(2);
        listGraph.addVertex(3);

        listGraph.addEdge(1, 2, 2);
        listGraph.addEdge(2, 3, 1);
        listGraph.addEdge(2, 1, 2);
        listGraph.addEdge(3, 1, 8);
        listGraph.addEdge(3, 2, 3);
    }

    @Test
    void addEdge() {
        setupStage2();
        HashMap<Integer, Vertex<Integer>> graph = listGraph.getGraph();
        Vertex<Integer> vertex1 = graph.get(1);
        int prevEdgeQuantity = vertex1.getEdges().size();

        listGraph.addEdge(1, 3, 1);

        assertEquals(vertex1.getEdges().size(), prevEdgeQuantity + 1);
    }

    @Test
    void addVertex() {
        setupStage2();
        listGraph.addVertex(4);
        assertEquals(listGraph.getNumVertices(), 4);
    }

    @Test
    void dijkstra() {
        setupStage2();
        String dijkstraOutput = listGraph.dijkstra(3, 1);

        String expected = "\nThe distance to 1 is 5.0\nThe path to 1 is: 3 --- 2 --- 1";

        assertEquals(dijkstraOutput, expected);
    }
}