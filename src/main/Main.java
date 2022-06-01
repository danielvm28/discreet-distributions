package main;

import model.DistributionSystem;
import structures.ListGraph;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static ListGraph<String> graph = new ListGraph<>(false);
    public static Scanner s = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        int selection = 0;
        boolean exit = false;

        DistributionSystem ds = new DistributionSystem();

        graph.addEdge("1", "2", 2);
        graph.addEdge("2", "5", 5);
        graph.addEdge("2", "3", 4);
        graph.addEdge("1", "4", 1);
        graph.addEdge("4", "3", 3);
        graph.addEdge("3", "5", 1);

        while (!exit) {
            System.out.println("\n----------------------------------------------------\n");
            System.out.println("This graph works with strings and is undirected by default");
            System.out.println("1) Add an edge between vertices");
            System.out.println("2) Reset graph");
            System.out.println("3) Determine Minimum Spanning Tree");
            System.out.println("4) Determine Shortest Path");
            System.out.println("0) Exit");
            System.out.println("\n----------------------------------------------------\n");
            selection = s.nextInt();

            switch (selection) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    s.nextLine();
                    String val1 = "";
                    String val2 = "";
                    double weight = 0.0;

                    System.out.print("\nIntroduce the first value: ");
                    val1 = s.nextLine();
                    System.out.print("Introduce the second value: ");
                    val2 = s.nextLine();
                    System.out.print("Introduce the weight of the edge: ");
                    weight = s.nextDouble();
                    s.nextLine();

                    graph.addEdge(val1, val2, weight);

                    break;
                case 2:
                    graph.clear();
                    System.out.println("\nGraph cleared");
                    System.out.println("\nPress enter to continue...");
                    s.nextLine();
                    s.nextLine();
                    break;
                case 3:
                    if (graph.getNumVertices() != 0) {
                        System.out.println("\nEach edge of the tree will be in a different line\n");
                        System.out.println(graph.prim());
                    } else {
                        System.out.println("The graph is empty\n");
                    }

                    System.out.println("Press enter to continue...");
                    s.nextLine();
                    s.nextLine();
                    break;
                case 4:
                    if (graph.getNumVertices() != 0) {
                        s.nextLine();
                        System.out.print("\nIntroduce the start value: ");
                        String startVal = s.nextLine();
                        System.out.print("Introduce the destination value: ");
                        String destinationVal = s.nextLine();

                        System.out.println("\n" + graph.dijkstra(startVal, destinationVal));
                    } else {
                        System.out.println("The graph is empty\n");
                    }

                    System.out.println("\nPress enter to continue...");
                    s.nextLine();
                    break;
                default:
                    System.out.println("Invalid selection");
                    System.out.println("\nPress enter to continue...");
                    s.nextLine();
                    s.nextLine();
                    break;
            }
        }
    }
}
