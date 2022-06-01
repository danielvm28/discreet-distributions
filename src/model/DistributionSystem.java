package model;

import structures.GraphI;
import structures.ListGraph;
import structures.MatrixGraph;
import java.io.*;
import java.util.ArrayList;

public class DistributionSystem {
    private GraphI<Office> graph;
    private ArrayList<Office> offices;

    /**
     * Constructs the graph according to the selected type
     * @param graphType the type of graph selected
     */
    public DistributionSystem(GraphType graphType) {
        graph = (graphType == GraphType.LISTS) ? new ListGraph<>(false) : new MatrixGraph<>(50, false);
        readData();
    }

    public void readData()  {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data/cities_database.csv")));
            String line = br.readLine();

            ArrayList<Office> offices = new ArrayList<>();
            String[] hVariables = line.split(",");

            // Iterate one time to get all the offices information
            while ((line = br.readLine()) != null) {
                String[] officeValues = line.split(",");
                Office office = new Office(officeValues[0], Integer.parseInt(officeValues[officeValues.length - 1]));
                offices.add(office);

                graph.addVertex(office);
            }

            br = new BufferedReader(new InputStreamReader(new FileInputStream("data/cities_database.csv")));
            line = br.readLine();

            // Iterate a second time to associate the offices with the distances
            for (int i = 0; (line = br.readLine()) != null; i++) {
                String[] officeValues = line.split(",");

                // Fill the graph
                for (int j = 1; j < officeValues.length - 1; j++) {
                    if (!officeValues[j].equals("N/A")) {
                        addConnection(offices.get(i), offices.get(searchCity(hVariables[j], offices)), Double.parseDouble(officeValues[j]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int searchCity(String line, ArrayList<Office> offices) {
        // Gets rid of "Distance" word
        line = line.substring(9);
        int pos = -1;

        for (int i = 0; i < offices.size(); i++) {
            if (offices.get(i).getCity().equalsIgnoreCase(line)) {
                pos = i;
                break;
            }
        }

        return pos;
    }

    /**
     * Adds a connection between two offices
     * @param office1 the first office
     * @param office2 the first office
     * @param distance the distance between offices
     * @return true if the connection did not exist, and false if it did
     */
    public boolean addConnection(Office office1, Office office2, double distance) {
        return graph.addEdge(office1, office2, distance);
    }

    /**
     * Updates a connection between two offices
     * @param office1 the first office
     * @param office2 the second office
     * @param distance the distance between offices
     * @return true if the connection existed, and false if it did not
     */
    public boolean updateConnection(Office office1, Office office2, double distance) {
        boolean existed = graph.removeEdge(office1, office2);

        if (existed) {
            graph.addEdge(office1, office2, distance);
        }

        return existed;
    }

    public boolean removeConnection(Office office1, Office office2) {
        return false;
    }

    /**
     * Adds an office to the graph
     * @return
     */
    public boolean addOffice(Office office1) {
        return false;
    }

    public boolean removeOffice(Office office1) {
        return false;
    }


}
