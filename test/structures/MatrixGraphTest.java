package structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.AssertJUnit.assertEquals;

class MatrixGraphTest {
    MatrixGraph<Integer> matrixGraph;


    public void setupStage1() {
        matrixGraph = new MatrixGraph<>(true);

        matrixGraph.addVertex(1);
        matrixGraph.addVertex(2);
        matrixGraph.addVertex(3);

        matrixGraph.addEdge(1, 2, 2);
        matrixGraph.addEdge(2, 3, 1);
        matrixGraph.addEdge(2, 1, 2);
        matrixGraph.addEdge(3, 1, 8);
        matrixGraph.addEdge(3, 2, 3);
    }

    @Test
    void addVertex() {
        setupStage1();
        matrixGraph.addVertex(4);
        assertEquals(matrixGraph.getNumVertex(), 4);
    }

    @Test
    void addEdge() {
        setupStage1();
        matrixGraph.addEdge(1, 3, 1);
        assertEquals(matrixGraph.getGraph().get(0).get(2), 1.0);
    }

    @Test
    void floydWarshall() {
        setupStage1();
        String out = matrixGraph.floydWarshall(3, 1);
        String expected = "\nThe distance to 1 is 5.0\nThe path to 1 is: 3 --- 2 --- 1";

        assertEquals(out, expected);
    }
}