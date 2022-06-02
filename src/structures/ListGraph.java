package structures;

import javafx.util.Pair;

import java.lang.reflect.Parameter;
import java.util.*;

public class ListGraph<T> implements GraphI<T>{
    //-------------------------------------------------- Attributes

    private HashMap<T, Vertex<T>> graph;
    private int numVertices;
    private boolean directed;
    private ArrayList<ArrayList<T>> groupedGraph; // Connected vertices per group after DFS
    private int groups; // Groups found after DFS

    //-------------------------------------------------- Constructor

    public ListGraph(boolean directed) {
        this.directed = directed;
        graph = new HashMap<>();
        groupedGraph = new ArrayList<>();
        numVertices = 0;
        groups = 0;
    }

    //-------------------------------------------------- Getters and Setters

    public HashMap<T, Vertex<T>> getGraph() {
        return graph;
    }

    public void setGraph(HashMap<T, Vertex<T>> graph) {
        this.graph = graph;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public boolean isDirected() {
        return directed;
    }

    public void setDirected(boolean directed) {
        this.directed = directed;
    }

    public int getGroups() {
        return groups;
    }

    public void setGroups(int groups) {
        this.groups = groups;
    }

    public ArrayList<ArrayList<T>> getGroupedGraph() {
        return groupedGraph;
    }

    public void setGroupedGraph(ArrayList<ArrayList<T>> groupedGraph) {
        this.groupedGraph = groupedGraph;
    }

    //-------------------------------------------------- Methods

    @Override
    public boolean addEdge(T value1, T value2, double weight) {
        Vertex<T> vertex1;
        Vertex<T> vertex2;

        if(!graph.containsKey(value1)) {
            addVertex(value1);
        }
        vertex1 = graph.get(value1);

        if(!graph.containsKey(value2)) {
            addVertex(value2);
        }
        vertex2 = graph.get(value2);

        return addEdge(vertex1, vertex2, weight);
    }

    @Override
    public boolean addEdge(T value1, T value2) {
        Vertex<T> vertex1;
        Vertex<T> vertex2;

        if(!graph.containsKey(value1)) {
            addVertex(value1);
        }
        vertex1 = graph.get(value1);

        if(!graph.containsKey(value2)) {
            addVertex(value2);
        }
        vertex2 = graph.get(value2);

        return addEdge(vertex1, vertex2, 1);
    }


    private boolean addEdge(Vertex<T> vertex, Vertex<T> destiny, double weight) {
        for (int i = 0; i < vertex.getEdges().size(); i++) {
            if (vertex.getEdges().get(i).getNext().equals(destiny)) {
                return false;
            }
        }

        vertex.addEdge(weight, destiny);

        if (!directed) {
            destiny.addEdge(weight, vertex);
        }

        return true;
    }

    @Override
    public boolean addVertex(T value) {

        if (!graph.containsKey(value)) {
            graph.put(value, new Vertex<>(value));
            numVertices++;
            return true;
        }

        return false;
    }

    @Override
    public boolean removeVertex(T value) {
        if (graph.containsKey(value)) {
            graph.remove(value);

            // Remove any existent connection
            for (Vertex<T> vertex :
                    graph.values()) {
                for (int i = 0; i < vertex.getEdges().size(); i++) {
                    if (vertex.getEdges().get(i).getNext().getValue().equals(value)) {
                        vertex.getEdges().remove(i);
                        i--;
                    }
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean removeEdge(T value1, T value2) {
        Vertex<T> vertex1 = graph.get(value1);

        if (!directed) {
            Vertex<T> vertex2 = graph.get(value2);
            vertex2.getEdges().removeIf(edge -> edge.getNext().equals(graph.get(value1)));
        }

        return vertex1.getEdges().removeIf(edge -> edge.getNext().equals(graph.get(value2)));
    }

    @Override
    public List<Vertex<T>> getVertices() {
        return new ArrayList<>(graph.values());
    }

    @Override
    public List<Pair<Double, Vertex<T>>> getAdjacentVertices(Vertex<T> vertex) {
        ArrayList<Pair<Double, Vertex<T>>> adjVertices = new ArrayList<>();

        for (Edge<T> edge :
                vertex.getEdges()) {
            adjVertices.add(new Pair<>(edge.getWeight(), edge.getNext()));
        }

        return adjVertices;
    }

    //----------------------------------------------------------- Graph Algorithms

    public void clear() {
        graph = new HashMap<>();
        groupedGraph = new ArrayList<>();
        numVertices = 0;
        groups = 0;
    }

    public void resetVisitedNodes() {
        for (Vertex<T> vertex : graph.values()) {
            vertex.setVisited(false);
        }
    }

    /**
     * Executes DFS
     * @return true if the graph is strongly connected, and false if otherwise
     */
    public boolean dfs() {
        resetVisitedNodes();
        groupedGraph = new ArrayList<>();
        groups = 0;

        boolean connected = false;

        // For each vertex
        for (Vertex<T> vertex : graph.values()) {
            if (!vertex.isVisited()) {
                groups++;
                groupedGraph.add(new ArrayList<>());
                dfsRecursive(vertex);
            }
        }

        if (groups == 1) {
            connected = true;
        }

        return connected;
    }

    private void dfsRecursive(Vertex<T> vertex) {
        // Set as visited
        vertex.setVisited(true);
        groupedGraph.get(groups-1).add(vertex.getValue());

        // For every adjacent vertex
        for (Edge<T> adjEdge : vertex.getEdges()) {
            Vertex<T> adjVertex = adjEdge.getNext();

            // If not visited, do dfs
            if (!adjVertex.isVisited()) {
                dfsRecursive(adjVertex);
            }
        }
    }

    /**
     * Determines the shortest path between a value and another within the graph
     * @param startValue the value of the starting vertex
     * @param destinationValue the value of the destination vertex
     * @return a String giving the shortest path information
     */
    public String dijkstra(T startValue, T destinationValue) {
        Pair<HashMap<T, T>, HashMap<T, Double>> dijkstraResults = dijkstraImplementation(startValue);
        String result = "";

        HashMap<T, T> parents = dijkstraResults.getKey();
        HashMap<T, Double> distances = dijkstraResults.getValue();

        if (distances.get(destinationValue) == Double.MAX_VALUE) {
            result += "\nIt is not possible to reach " + destinationValue + " from " + startValue;
            return result;
        }

        result += "\nThe distance to " + destinationValue + " is " + distances.get(destinationValue) + "\nThe path to " + destinationValue + " is: ";

        boolean flag = false;
        ArrayList<T> path = new ArrayList<>();
        T currParent = destinationValue;

        while (!flag) {
            path.add(currParent);

            if (!parents.get(currParent).equals(startValue)) {
                currParent = parents.get(currParent);
            } else {
                path.add(startValue);
                flag = true;
            }
        }

        for (int i = path.size() - 1; i >= 0; i--) {
            if (i == 0) {
                result += path.get(i);
            } else {
                result += path.get(i) + " --- ";
            }
        }

        return result;
    }

    private Pair<HashMap<T, T>, HashMap<T, Double>> dijkstraImplementation(T startValue) {
        T[] keyArray = (T[]) graph.keySet().toArray();
        HashMap<T, T> parents = new HashMap<>();
        HashMap<T, Double> distances = new HashMap<>();

        PriorityQueue<Pair<T, Double>> pq = new PriorityQueue<>((o1, o2) -> {
            int r = 0;

            if (o1.getValue() < o2.getValue()) {
                r = -1;
            }else if (o1.getValue() > o2.getValue()){
                r = 1;
            }

            return r;
        });

        // Initialize all distances of vertices to infinite
        for (T t : keyArray) {
            distances.put(t, Double.MAX_VALUE);
        }

        // Distance of start value to 0
        distances.put(startValue, 0.0);
        pq.add(new Pair<>(startValue, 0.0));

        while(!pq.isEmpty()) {
            Pair<T, Double> currQuery = pq.poll();
            Vertex<T> currVertex = graph.get(currQuery.getKey());
            double currDistance = currQuery.getValue();

            for(Edge<T> e : currVertex.getEdges()) {

                double adjDistance = currDistance + e.getWeight();
                T adjVertexVal = e.getNext().getValue();

                if(adjDistance < distances.get(adjVertexVal)) {
                    distances.put(adjVertexVal, adjDistance);
                    parents.put(adjVertexVal, currVertex.getValue());
                    pq.add(new Pair<>(adjVertexVal, adjDistance));
                }
            }
        }

        return new Pair<>(parents, distances);
    }

    /**
     * Public Prim algorithm method that calculates the Minimum Spanning Tree and returns a
     * String representation
     * @return a String representation of the MST
     */
    public String prim() {
        String mstString = "";

        // Get all keys in the graph
        T[] graphKeyArray = (T[]) graph.keySet().toArray();
        
        T startValue = graphKeyArray[0];
        
        Pair<HashMap<T, T>, HashMap<T, Double>> mst = prim(startValue);
        
        HashMap<T,T> parents = mst.getKey();
        HashMap<T,Double> key = mst.getValue();

        for (T value :
                graphKeyArray) {

            if (parents.get(value) == null) {
                mstString += "Root -> " + value + "\n";
            } else {
                mstString += parents.get(value) + " --- " + key.get(value) + " --- " + value + "\n";
            }
        }

        return mstString;
    }

    /**
     * Executes the Prim algorithm of Minimum Spanning Tree
     * @param startValue the value in which the algorithm will start
     * @return the HashMap of parents and the HashMap of keys of the vertices of the MST
     */
    private Pair<HashMap<T,T>, HashMap<T, Double>> prim(T startValue){
        // Get all keys in the graph
        T[] graphKeyArray = (T[]) graph.keySet().toArray();

        // HashMap to store constructed MST
        HashMap<T, T> parent = new HashMap<>();

        // Key values used to pick minimum weight edge
        HashMap<T, Double> key = new HashMap<>();

        // To represent set of vertices included in MST
        HashSet<Vertex<T>> mstSet = new HashSet<>();

        // Initialize all keys as INFINITE
        for (T vertexVal : graphKeyArray) {
            key.put(vertexVal, Double.MAX_VALUE);
        }

        PriorityQueue<Pair<Vertex<T>, Double>> pq = new PriorityQueue<>(((o1, o2) -> {
            int cmp = 0;

            if (o1.getValue() < o2.getValue()) {
                cmp = -1;
            } else if (o1.getValue() > o2.getValue()){
                cmp = 1;
            }

            return cmp;
        }));

        // Include first key in the graphKeyArray in the MST
        key.put(startValue, 0.0); // Make key 0 to be picked first

        parent.put(startValue, null); // First node is always root of MST

        // Add first vertex with its weight
        pq.add(new Pair<>(graph.get(startValue), 0.0));

        while (!pq.isEmpty()) {
            // Get minimum vertex from priority queue
            Pair<Vertex<T>, Double> u = pq.poll();

            Vertex<T> uVertex = u.getKey();

            // Add vertex to the set of MST
            mstSet.add(uVertex);

            // For every adjacent edge, get the vertex
            for (Edge<T> adjEdge : uVertex.getEdges()) {
                Vertex<T> adjVertex = adjEdge.getNext();
                Double adjWeight = adjEdge.getWeight();

                // If its not on MST and weight is smaller than current key of the vertex
                if (!mstSet.contains(adjVertex) && key.get(adjVertex.getValue()) > adjWeight) {
                    // Update key of adjVertex
                    key.put(adjVertex.getValue(), adjWeight);
                    pq.add(new Pair<>(adjVertex, adjWeight));
                    parent.put(adjVertex.getValue(), uVertex.getValue());
                }
            }
        }

        return new Pair<>(parent, key);
    }
}