package structures;

import javafx.util.Pair;

import java.util.List;

public interface GraphI<T> {
    public boolean addVertex(T value);

    public void addEdge(T value1, T value2);

    public void addEdge(T value1, T value2, double weight);

    public boolean removeVertex(T value);

    public boolean removeEdge(T value1,T value2);

    public List<Vertex<T>> getVertices();

    public List<Pair<Double, Vertex<T>>> getAdjacentVertices(Vertex<T> vertex);
}
