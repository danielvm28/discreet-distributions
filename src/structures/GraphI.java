package structures;

import javafx.util.Pair;

import java.util.List;

public interface GraphI<T> {
    /**
     * Adds a vertex to the graph
     * @param value the value associated to the new vertex
     * @return true if the vertex did not exist, and false if it did
     */
    public boolean addVertex(T value);

    /**
     * Adds an edge to the graph
     * @param value1 the first value in the graph
     * @param value2 the second value in the graph
     * @return true if the connection did not exist, and false if it did
     */
    public boolean addEdge(T value1, T value2);

    /**
     * Adds an edge to the graph
     * @param value1 the first value in the graph
     * @param value2 the second value in the graph
     * @param weight the weight of the edge
     * @return true if the connection did not exist, and false if it did
     */
    public boolean addEdge(T value1, T value2, double weight);

    /**
     * Removes a vertex from the graph
     * @param value value of the vertex to be eliminated
     * @return true if the vertex existed, and false if it did not
     */
    public boolean removeVertex(T value);

    /**
     * Removes an edge between to vertices
     * @param value1 the value of the first vertex
     * @param value2 the value of the second vertex
     * @return true if the edge existed, and false if it did not
     */
    public boolean removeEdge(T value1,T value2);

    public List<Vertex<T>> getVertices();

    public List<Pair<Double, Vertex<T>>> getAdjacentVertices(Vertex<T> vertex);
}
