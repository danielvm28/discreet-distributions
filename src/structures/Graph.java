package structures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Directed generic graph class
 * @author Daniel Valencia - A00372845
 */
public class Graph<T> {
    private ArrayList<Node<T>> nodes;
    private String printedGraph;
    private int groups;

    public Graph() {
        nodes = new ArrayList<>();
        printedGraph = "";
        groups = 0;
    }

    public String getPrintedGraph() {
        return printedGraph;
    }

    public void setPrintedGraph(String printedGraph) {
        this.printedGraph = printedGraph;
    }

    public ArrayList<Node<T>> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Node<T>> nodes) {
        this.nodes = nodes;
    }

    public int getGroups() {
        return groups;
    }

    public void setGroups(int groups) {
        this.groups = groups;
    }

    public void clear() {
        nodes.clear();
        printedGraph = "";
        groups = 0;
    }

    /**
     * This will create a link from the node with the value A, to the node with the
     * value B
     * 
     * @param valueA value of the node to add the link
     * @param valueB value of the node to be added
     */
    public void addLink(T valueA, T valueB) {
        boolean foundA = false;
        boolean foundB = false;
        Node<T> nodeA = new Node<>(valueA);
        Node<T> nodeB = new Node<>(valueB);

        // Looks for repeated nodes in the graph
        for (Node<T> node : nodes) {
            if (!foundA && valueA.equals(node.getValue())) {
                nodeA = node;
                foundA = true;
            }

            if (!foundB && valueB.equals(node.getValue())) {
                nodeB = node;
                foundB = true;
            }

            if (foundB && foundA) {
                break;
            }
        }

        // If the nodes did not repeat, add a new node for the graph
        if (!foundA) {
            nodes.add(nodeA);
        }
        if (!foundB) {
            nodes.add(nodeB);
        }

        nodeA.addAdjacent(nodeB);
    }

    public void resetVisitedNodes() {
        for (Node<T> node : nodes) {
            node.setVisited(false);
        }
    }

    public boolean dfsRecursive() {
        resetVisitedNodes();
        printedGraph = "";
        groups = 0;

        boolean connected = false;

        // For each node
        for (Node<T> node : nodes) {
            if (!node.isVisited()) {
                groups++;
                dfsRecursive(node);
                printedGraph += "\n";
            }
        }

        if (groups == 1) {
            connected = true;
        }

        return connected;
    }

    private void dfsRecursive(Node<T> node) {
        // Set as visited
        node.setVisited(true);

        printedGraph += node.getValue() + " ";

        // For every adjacent node
        for (Node<T> adj : node.getAdjacentNodes()) {

            // If not visited, do dfs
            if (!adj.isVisited()) {
                dfsRecursive(adj);
            }
        }
    }

    public boolean dfsIterative() {
        resetVisitedNodes();
        printedGraph = "";
        groups = 0;

        boolean connected = false;
        Stack<Node<T>> nodeStack = new Stack<>();

        // For every node in the graph
        for (Node<T> node : nodes) {
            if (!node.isVisited()) {
                node.setVisited(true);
                groups++;

                // Put into the stack the actual unvisited node
                nodeStack.add(node);

                // While the stack has elements
                while (!nodeStack.isEmpty()) {
                    // Get first element
                    Node<T> u = nodeStack.pop();

                    printedGraph += u.getValue() + " ";

                    // Add adjacent nodes of the actual node to a temporal stack and visit them
                    Stack<Node<T>> temp = new Stack<>();

                    for (Node<T> adj : u.getAdjacentNodes()) {
                        if (!adj.isVisited()) {
                            temp.add(adj);
                            adj.setVisited(true);
                        }
                    }

                    // Fill the node stack with temp stack (This is done for means of order)
                    while (!temp.isEmpty()) {
                        nodeStack.add(temp.pop());
                    }
                }

                printedGraph += "\n";
            }
        }

        if (groups == 1) {
            connected = true;
        }

        return connected;
    }

    public boolean bfs() {
        resetVisitedNodes();
        printedGraph = "";
        groups = 0;

        boolean connected = false;
        Queue<Node<T>> nodeQueue = new LinkedList<>();

        // For every node in the graph
        for (Node<T> node : nodes) {
            if (!node.isVisited()) {
                node.setVisited(true);
                groups++;

                // Queue actual unvisited node
                nodeQueue.add(node);

                // While the queue has elements
                while (!nodeQueue.isEmpty()) {
                    // Get first element
                    Node<T> u = nodeQueue.poll();

                    printedGraph += u.getValue() + " ";

                    // Add adjacent nodes of the actual node to the queue and visit them
                    for (Node<T> adj : u.getAdjacentNodes()) {
                        if (!adj.isVisited()) {
                            nodeQueue.add(adj);
                            adj.setVisited(true);
                        }
                    }
                }

                printedGraph += "\n";
            }
        }

        if (groups == 1) {
            connected = true;
        }

        return connected;
    }
}
