package model;

import structures.GraphI;
import structures.ListGraph;
import structures.MatrixGraph;

import java.util.ArrayList;

public class DistributionAlgorithms {

    public static String determineMeanProductsPerGroup(GraphI<Office> graph) {
        String output = "";
        int groups = 0;
        int totalProducts = 0;
        boolean connected = false;
        ArrayList<ArrayList<Office>> groupedGraph = new ArrayList<>();

        if (graph instanceof ListGraph) {
            ListGraph<Office> listGraph = (ListGraph<Office>) graph;
            connected = listGraph.dfs();
            groupedGraph = listGraph.getGroupedGraph();
            groups = groupedGraph.size();
        } else if (graph instanceof MatrixGraph) {
            MatrixGraph<Office> matrixGraph = (MatrixGraph<Office>) graph;
            connected = matrixGraph.dfs();
            groupedGraph = matrixGraph.getGroupedGraph();
            groups = groupedGraph.size();
        }

        if (connected) output += "All of the offices are connected\n\n";
        else output += "The offices are not all connected\n\n";

        for (int i = 0; i < groups; i++) {
            totalProducts = 0;

            for (Office office :
                    groupedGraph.get(i)) {
                totalProducts += office.getProducts();
            }

            output += "Group " + (i+1) + " | Office of reference: " + groupedGraph.get(i).get(0).getCity()
                    +  " | No. Offices: " + groupedGraph.get(i).size() + " | Product Mean: " + (double) (totalProducts/groupedGraph.get(i).size()) + "\n";
        }

        return output;
    }

    public static String determineShortestPath(GraphI<Office> graph, Office start, Office destination) {
        String output = "";

        if (graph instanceof ListGraph) {
            ListGraph<Office> listGraph = (ListGraph<Office>) graph;
            output = listGraph.dijkstra(start, destination);
        } else if (graph instanceof MatrixGraph) {
            MatrixGraph<Office> matrixGraph = (MatrixGraph<Office>) graph;
            output = matrixGraph.floydWarshall(start, destination);
        }

        return output;
    }
}
