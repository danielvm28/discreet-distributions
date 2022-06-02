package structures;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatrixGraph<T> implements GraphI<T>{
    //-------------------------------------------------- Attributes
    private final HashMap<T, Integer> pos; // Position in the matrix for a given value
    private HashMap<Integer, T> posIndex; // Value according to the position in the matrix
    private final ArrayList<ArrayList<Double>> graph; // Adjacency matrix representation of graph
    private int numVertex;
    private int keyPos = 0;
    private boolean directed;
    private ArrayList<Integer> posRemove; // Removed positions
    private ArrayList<ArrayList<Double>> mapFloyd; // Distances matrix of Floyd-Warshall
    private ArrayList<ArrayList<Integer>> mapAntFloyd; // Ancestors matrix of Floyd-Warshall
    private ArrayList<ArrayList<T>> groupedGraph; // Connected vertices per group after DFS
    private int groups; // Groups found after DFS
    //-------------------------------------------------- Constructor
    public MatrixGraph(boolean directed) {
        pos = new HashMap<>();
        posRemove = new ArrayList<>();
        mapFloyd= new ArrayList<>();
        mapAntFloyd= new ArrayList<>();
        graph = new ArrayList<>();
        posIndex = new HashMap<>();
        groupedGraph = new ArrayList<>();
        this.directed = directed;
    }
    //-------------------------------------------------- Getters and Setters

    public ArrayList<ArrayList<Double>> getGraph() {
        return graph;
    }

    public ArrayList<ArrayList<Double>> getMapFloyd() {
        return mapFloyd;
    }

    public ArrayList<ArrayList<Integer>> getMapAntFloyd() {
        return mapAntFloyd;
    }

    public ArrayList<ArrayList<T>> getGroupedGraph() {
        return groupedGraph;
    }

    public void setGroupedGraph(ArrayList<ArrayList<T>> groupedGraph) {
        this.groupedGraph = groupedGraph;
    }

    //-------------------------------------------------- Methods
    @Override
    public boolean addVertex(T value){
        if (!pos.containsKey(value)) {
            for (ArrayList<Double> doubles : graph) {
                doubles.add(Integer.MAX_VALUE + 0.0);
            }
            graph.add(new ArrayList<>());
            for (int i = 0; i < graph.size(); i++) {
                graph.get(graph.size()-1).add(Integer.MAX_VALUE+0.0);
            }

            numVertex++;
            keyVertex(value);

            return true;
        }

        return false;
    }

    @Override
    public boolean addEdge(T value1, T value2) {
        int pos1 = pos.get(value1);
        int pos2 = pos.get(value2);

        if (graph.get(pos1).get(pos2) != Integer.MAX_VALUE + 0.0) {
            return false;
        }

        graph.get(pos1).set(pos2, 1.0);

        if (!directed) {
            graph.get(pos2).set(pos1, 1.0);
        }

        return true;
    }

    @Override
    public boolean addEdge(T value1, T value2, double weight) {
        int pos1 = pos.get(value1);
        int pos2 = pos.get(value2);

        if (graph.get(pos1).get(pos2) != Integer.MAX_VALUE + 0.0) {
            return false;
        }

        graph.get(pos1).set(pos2, weight);

        if (!directed) {
            graph.get(pos2).set(pos1, weight);
        }

        return true;
    }

    @Override
    public boolean removeVertex(T value){
        if (!pos.containsKey(value)) {
            return false;
        }

        int posi = pos.get(value);
        posRemove.add(posi);

        for (ArrayList<Double> doubles : graph) {
            doubles.set(posi, Integer.MAX_VALUE + 0.0);
        }
        for (int i = 0; i < graph.size(); i++) {
            graph.get(posi).set(i, Integer.MAX_VALUE+0.0);
        }

        numVertex--;

        return true;
    }

    @Override
    public boolean removeEdge(T value1, T value2) {
        int pos1 = pos.get(value1);
        int pos2 = pos.get(value2);
        boolean hadConnection = graph.get(pos1).get(pos2) != Integer.MAX_VALUE+0.0;

        graph.get(pos1).set(pos2, Integer.MAX_VALUE+0.0);

        if (!directed) {
            graph.get(pos2).set(pos1, Integer.MAX_VALUE+0.0);
        }

        return hadConnection;
    }

    @Override
    public List<Vertex<T>> getVertices() {
        return null;
    }

    @Override
    public List<Pair<Double, Vertex<T>>> getAdjacentVertices(Vertex<T> vertex) {
        return null;
    }

    private void keyVertex(T value){
        if (posRemove.size()!=0){
            pos.put(value, posRemove.get(0));
            posIndex.put(posRemove.get(0), value);
            posRemove.remove(0);
        } else {
            posIndex.put(keyPos, value);
            pos.put(value, keyPos);
            keyPos++;
        }
    }

    public void fillFloydMaps(){
        mapAntFloyd=new ArrayList<>();
        mapFloyd = new ArrayList<>();

        for (int i = 0; i < graph.size(); i++) {
            mapAntFloyd.add(new ArrayList<>());
            for (int j = 0; j < graph.size(); j++) {
                mapAntFloyd.get(i).add(i);
            }
        }

        for (int i = 0; i < graph.size(); i++) {
            mapFloyd.add(new ArrayList<>());
            for (int j = 0; j < graph.get(i).size(); j++) {
                mapFloyd.get(i).add(graph.get(i).get(j));
            }
        }
    }

    public String floydWarshall(T startValue, T destinationValue) {
        String output = "";
        String pathOutput = destinationValue + "";
        boolean flag = false;
        int posStart = pos.get(startValue);
        int posDestination = pos.get(destinationValue);

        // Execute Floyd-Warshall
        floydWarshall();

        if (mapFloyd.get(posStart).get(posDestination) == Integer.MAX_VALUE + 0.0) {
            output += "\nIt is not possible to reach " + destinationValue + " from " + startValue;
            return output;
        }

        output += "\nThe distance to " + destinationValue + " is " + mapFloyd.get(posStart).get(posDestination) + "\nThe path to " + destinationValue + " is: ";

        while (!flag) {
            if (mapAntFloyd.get(posStart).get(posDestination) != posStart) {
                pathOutput = posIndex.get(mapAntFloyd.get(posStart).get(posDestination)) + " --- " + pathOutput;
                posDestination = mapAntFloyd.get(posStart).get(posDestination);
            } else {
                flag = true;
            }
        }

        pathOutput = startValue + " --- " + pathOutput;
        output += pathOutput;

        return output;
    }

    private void floydWarshall(){
        fillFloydMaps();

        for (int k = 0; k < mapFloyd.size(); k++) {
            for (int i = 0; i < mapFloyd.size(); i++) {
                for (int j = 0; j < mapFloyd.size(); j++) {
                    if (mapFloyd.get(i).get(j)> mapFloyd.get(i).get(k) + mapFloyd.get(k).get(j)){
                        mapFloyd.get(i).set(j, mapFloyd.get(i).get(k) + mapFloyd.get(k).get(j));
                        mapAntFloyd.get(i).set(j, mapAntFloyd.get(k).get(j));
                    }
                }
            }
        }
    }

    /**
     * Executes DFS
     * @return true if the graph is strongly connected, and false if otherwise
     */
    public boolean dfs() {
        ArrayList<Boolean> visited = new ArrayList<>();

        // Initialize all nodes to false
        for (int i = 0; i < numVertex; i++) {
            visited.add(false);
        }

        groupedGraph = new ArrayList<>();
        groups = 0;

        boolean connected = false;

        // For each vertex
        for (int i = 0; i < graph.size(); i++) {
            if (!visited.get(i)) {
                groups++;
                groupedGraph.add(new ArrayList<>());
                visited = dfsRecursive(i, visited);
            }
        }

        if (groups == 1) {
            connected = true;
        }

        return connected;
    }

    private ArrayList<Boolean> dfsRecursive(int vertexIndex, ArrayList<Boolean> visited) {
        // Set as visited
        visited.set(vertexIndex, true);
        groupedGraph.get(groups-1).add(posIndex.get(vertexIndex));

        // For every adjacent vertex
        for (int i = 0; i < graph.get(vertexIndex).size(); i++) {

            // If not visited, do dfs
            if (graph.get(vertexIndex).get(i) != Integer.MAX_VALUE + 0.0 && !visited.get(i)) {
                dfsRecursive(i, visited);
            }
        }

        return visited;
    }
}
