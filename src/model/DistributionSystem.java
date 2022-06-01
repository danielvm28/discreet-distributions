package model;

import structures.ListGraph;
import structures.MatrixGraph;
import java.io.*;
import java.util.ArrayList;

public class DistributionSystem {
    private ListGraph<Office> listGraph = new ListGraph<>(false);
    private MatrixGraph<Office> matrixGraph = new MatrixGraph<>(50);

    public DistributionSystem() throws IOException {
        readData();
    }

    public void readData() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("data/cities_database.csv")));
        String line = br.readLine();

        ArrayList<Office> offices = new ArrayList<>();
        String[] hVariables = line.split(",");

        // Iterate one time to get all the offices information
        while ((line = br.readLine()) != null) {
            String[] officeValues = line.split(",");
            Office office = new Office(officeValues[0], Integer.parseInt(officeValues[officeValues.length - 1]));
            offices.add(office);

            listGraph.addVertex(office);
            matrixGraph.addVertex(office);
        }

        br = new BufferedReader(new InputStreamReader(new FileInputStream("data/cities_database.csv")));
        line = br.readLine();

        // Iterate a second time to associate the offices with the distances
        for (int i = 0; (line = br.readLine()) != null; i++) {
            String[] officeValues = line.split(",");

            // Fill the graphs
            for (int j = 1; j < officeValues.length - 1; j++) {
                if (!officeValues[j].equals("N/A")) {
                    addRelation(offices.get(i), offices.get(searchCity(hVariables[j], offices)), Double.parseDouble(officeValues[j]));
                }
            }
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

    public void addRelation(Office office1, Office office2, double distance) {
        listGraph.addEdge(office1, office2, distance);
        matrixGraph.addEdge(office1, office2, distance);
    }
}
