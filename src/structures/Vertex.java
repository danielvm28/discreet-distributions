package structures;

import java.util.ArrayList;
import java.util.List;

public class Vertex<T> {
    //-------------------------------------------------- Attributes

    private T value;
    private List<Edge<T>> edges;
    private boolean visited;

    //-------------------------------------------------- Constructor

    public Vertex(T value) {
        this.value = value;
        this.edges = new ArrayList<>();
        this.visited = false;
    }

    //-------------------------------------------------- Getters and Setters

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public List<Edge<T>> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge<T>> edges) {
        this.edges = edges;
    }

    public boolean isVisited() {
        return this.visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    //-------------------------------------------------- Methods

    public void addEdge(double value, Vertex<T> next) {
        boolean exist = false;
        for(Edge<T> e : edges) {
            if(e.getNext().equals(next)) {
                e.setWeight(value);
                exist = true;
                break;
            }
        }

        if(!exist) {
            edges.add(new Edge<T>(next, value));
        }
    }

}
