package model;

import org.junit.jupiter.api.Test;
import structures.ListGraph;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DistributionAlgorithmsTest {
    ListGraph<Office> listGraph = new ListGraph<>(true);
    Office office1, office2, office3, office4;

    public void setupStage1() {
        office1 = new Office("San Francisco", 100);
        office2 = new Office("New York", 123);
        office3 = new Office("Chicago", 231);
        office4 = new Office("Miami", 98);

        listGraph.addVertex(office1);
        listGraph.addVertex(office2);
        listGraph.addVertex(office3);
        listGraph.addVertex(office4);

        listGraph.addEdge(office1, office4, 121);
        listGraph.addEdge(office1, office2, 190);
        listGraph.addEdge(office3, office4, 12);
        listGraph.addEdge(office3, office2, 123);
        listGraph.addEdge(office2, office4, 270);
        listGraph.addEdge(office2, office3, 98);
        listGraph.addEdge(office4, office1, 129);
    }

    @Test
    void determineMeanProductsPerGroup() {
        setupStage1();
        listGraph.dfs();
        ArrayList<ArrayList<Office>> groupedGraph = listGraph.getGroupedGraph();

        String expected = "All of the offices are connected\n" +
                "\n" +
                "Group 1 | Office of reference: "+ groupedGraph.get(0).get(0).getCity() +" | No. Offices: 4 | Product Mean: 138.0\n";
        String result = DistributionAlgorithms.determineMeanProductsPerGroup(listGraph);

        assertEquals(result, expected);
    }

    @Test
    void determineShortestPath() {
        setupStage1();
        String expected = "\nThe distance to Miami is 110.0\nThe path to Miami is: New York --- Chicago --- Miami";
        String result = DistributionAlgorithms.determineShortestPath(listGraph, office2, office4);

        assertEquals(result, expected);
    }
}