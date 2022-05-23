package structures;

import java.util.ArrayList;

/**
 * Generic node class
 * @author Daniel Valencia - A00372845
 */
public class Node<T> {
    private T value;
    private ArrayList<Node<T>> adjacentNodes;
    private boolean visited;
    
    public Node(T value) {
        this.value = value;
        adjacentNodes = new ArrayList<>();
        visited = false;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public ArrayList<Node<T>> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void setAdjacentNodes(ArrayList<Node<T>> adjacentNodes) {
        this.adjacentNodes = adjacentNodes;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void addAdjacent(Node<T> adj) {
        adjacentNodes.add(adj);
    }
}
