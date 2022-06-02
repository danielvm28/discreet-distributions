package main;

import model.DistributionSystem;
import model.GraphType;
import model.Office;
import java.util.Scanner;

public class Main {
    public static DistributionSystem ds;
    public static Scanner s = new Scanner(System.in);

    public static boolean addOffice() {
        String city = "";
        int products = 0;

        System.out.print("\nIntroduce the city of the office: ");
        city = s.nextLine();
        System.out.print("Introduce the products of the city: ");
        products = s.nextInt();
        s.nextLine();

        Office existentOffice = ds.searchOffice(city);

        if (existentOffice == null) {
            Office newOffice = new Office(city, products);
            ds.addOffice(newOffice);

            return true;
        }

        return false;
    }

    public static Office determineOffice() {
        Office foundOffice = null;
        String city = "";

        System.out.print("Introduce the city of the office: ");
        city = s.nextLine();

        foundOffice = ds.searchOffice(city);

        return foundOffice;
    }

    public static void main(String[] args) {
        int selection = 0;
        boolean exit = false;

        do {
            System.out.println("\nChoose the graph representation");
            System.out.println("1) Adjacency List");
            System.out.println("2) Adjacency Matrix\n");
            selection = s.nextInt();
            s.nextLine();
        } while (selection != 1 && selection != 2);

        if (selection == 1) {
            ds = new DistributionSystem(GraphType.LISTS);
        } else {
            ds = new DistributionSystem(GraphType.MATRIX);
        }

        while (!exit) {
            Office office1;
            Office office2;

            System.out.println("\n----------------------------------------------------\n");
            System.out.println("Welcome to the Discreet Softwares Inc. distribution system");
            System.out.println("1) Add a new office");
            System.out.println("2) Remove an office");
            System.out.println("3) Add a new connection between offices");
            System.out.println("4) Update a connection between offices");
            System.out.println("5) Remove a connection between offices");
            System.out.println("6) Determine the office groups and product average");
            System.out.println("7) Determine the shortest path between two offices");
            System.out.println("0) Exit");
            System.out.println("\n----------------------------------------------------\n");
            selection = s.nextInt();
            s.nextLine();

            switch (selection) {
                case 0:
                    exit = true;
                    break;
                case 1:
                    if (addOffice()) {
                        System.out.println("\nOffice added successfully");
                    } else {
                        System.out.println("\nError, the office already exists");
                    }

                    System.out.println("\nPress enter to continue...");
                    s.nextLine();
                    break;
                case 2:
                    Office office = determineOffice();

                    if (ds.removeOffice(office)) {
                        System.out.println("\nOffice removed successfully");
                    } else {
                        System.out.println("\nError, the office did not exist");
                    }

                    System.out.println("\nPress enter to continue...");
                    s.nextLine();
                    break;
                case 3:
                    System.out.println("Office 1");
                    office1 = determineOffice();
                    System.out.println("Office 2");
                    office2 = determineOffice();

                    if (office1 == null || office2 == null) {
                        System.out.println("\nError, one of the offices does not exist");
                    } else {
                        double distance = 0.0;

                        System.out.print("Indicate the distance between the offices: ");
                        distance = s.nextDouble();
                        s.nextLine();

                        if (ds.addConnection(office1, office2, distance)) {
                            System.out.println("\nConnection added successfully");
                        } else {
                            System.out.println("\nError, the connection already exists");
                        }
                    }

                    System.out.println("\nPress enter to continue...");
                    s.nextLine();
                    break;
                case 4:
                    System.out.println("Office 1");
                    office1 = determineOffice();
                    System.out.println("Office 2");
                    office2 = determineOffice();

                    if (office1 == null || office2 == null) {
                        System.out.println("\nError, one of the offices does not exist");
                    } else {
                        double distance = 0.0;

                        System.out.print("Indicate the new distance between the offices: ");
                        distance = s.nextDouble();
                        s.nextLine();

                        if (ds.updateConnection(office1, office2, distance)) {
                            System.out.println("\nConnection updated successfully");
                        } else {
                            System.out.println("\nError, the connection did not exist");
                        }
                    }

                    System.out.println("\nPress enter to continue...");
                    s.nextLine();
                    break;
                case 5:
                    System.out.println("Office 1");
                    office1 = determineOffice();
                    System.out.println("Office 2");
                    office2 = determineOffice();

                    if (office1 == null || office2 == null) {
                        System.out.println("\nError, one of the offices does not exist");
                    } else {

                        if (ds.removeConnection(office1, office2)) {
                            System.out.println("\nConnection removed successfully");
                        } else {
                            System.out.println("\nError, the connection did not exist");
                        }
                    }

                    System.out.println("\nPress enter to continue...");
                    s.nextLine();
                    break;
                case 6:
                    System.out.println(ds.determineOfficeGroups());

                    System.out.println("Press enter to continue...");
                    s.nextLine();
                    break;
                case 7:
                    System.out.println("Office 1");
                    office1 = determineOffice();
                    System.out.println("Office 2");
                    office2 = determineOffice();

                    if (office1 == null || office2 == null) {
                        System.out.println("\nError, one of the offices does not exist");
                    } else {
                        System.out.println(ds.determineShortestPath(office1, office2));
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
