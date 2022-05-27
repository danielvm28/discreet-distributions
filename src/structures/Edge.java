package structures;

public class Edge<T> {
    //-------------------------------------------------- Attributes

    private Vertex<T> next;
    private double weight;

    //-------------------------------------------------- Constructor

    public Edge(Vertex<T> next, double weight) {
        this.next = next;
        this.weight = weight;
    }

    //-------------------------------------------------- Getters and Setters

    public Vertex<T> getNext() {
        return next;
    }

    public void setNext(Vertex<T> next) {
        this.next = next;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}