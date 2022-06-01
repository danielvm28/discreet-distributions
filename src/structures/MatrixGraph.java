package structures;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatrixGraph<T> implements GraphI<T>{
    //-------------------------------------------------- Attributes
    private final HashMap<T, Integer> pos;
    private final ArrayList<ArrayList<Double>> graph;
    private HashMap<Integer, T> posIndex;
    private final int numVertex;
    private int keyPos = 0;
    private ArrayList<Integer> posRemove;
    private ArrayList<ArrayList<Double>> mapFloyd;
    private ArrayList<ArrayList<Integer>> mapAntFloyd;
    //-------------------------------------------------- Constructor
    public MatrixGraph(int numVertex) {
        pos = new HashMap<>();
        posRemove = new ArrayList<>();
        mapFloyd= new ArrayList<>();
        mapAntFloyd= new ArrayList<>();
        this.graph = new ArrayList<>();
        posIndex = new HashMap<>();
        this.numVertex = numVertex;
        fillMatrix();
    }
    //-------------------------------------------------- Getters and Setters
    public ArrayList<ArrayList<Double>> getMapFloyd() {
        return mapFloyd;
    }

    public ArrayList<ArrayList<Integer>> getMapAntFloyd() {
        return mapAntFloyd;
    }

    //-------------------------------------------------- Methods
    @Override
    public boolean addVertex(T value){
        if (!pos.containsKey(value)) {
            for (ArrayList<Double> doubles : graph) {
                doubles.add(Integer.MAX_VALUE + 0.0);
            }
            graph.add(new ArrayList<>());
            for (int i = 0; i < graph.size(); i++) {
                graph.get(graph.size()-1).add(Integer.MAX_VALUE+0.0);
            }
            floydWarshall();
            keyVertex(value);

            return true;
        }

        return false;
    }

    @Override
    public void addEdge(T value1, T value2) {
        int pos1 = pos.get(value1);
        int pos2 = pos.get(value2);
        graph.get(pos1).set(pos2, 1.0);
        floydWarshall();
    }

    @Override
    public void addEdge(T value1, T value2, double weight) {
        int pos1 = pos.get(value1);
        int pos2 = pos.get(value2);
        graph.get(pos1).set(pos2, weight);
        floydWarshall();
    }

    @Override
    public boolean removeVertex(T value){
        if (!pos.containsKey(value)) {
            return false;
        }

        int posi = pos.get(value);
        posRemove.add(posi);

        for (ArrayList<Double> doubles : graph) {
            doubles.set(posi, Integer.MAX_VALUE + 0.0);
        }
        for (int i = 0; i < graph.size(); i++) {
            graph.get(posi).set(i, Integer.MAX_VALUE+0.0);
        }

        floydWarshall();

        return true;
    }

    @Override
    public boolean removeEdge(T value1, T value2) {
        int pos1 = pos.get(value1);
        int pos2 = pos.get(value2);
        boolean hadConnection = graph.get(pos1).get(pos2) != Integer.MAX_VALUE+0.0;

        graph.get(pos1).set(pos2, Integer.MAX_VALUE+0.0);
        floydWarshall();

        return hadConnection;
    }

    @Override
    public List<Vertex<T>> getVertices() {
        return null;
    }

    @Override
    public List<Pair<Double, Vertex<T>>> getAdjacentVertices(Vertex<T> vertex) {
        return null;
    }

    public void fillMatrix (){
        for (int i = 0; i < numVertex; i++) {
            graph.add(new ArrayList<>());
            for (int j = 0; j < numVertex; j++) {
                graph.get(i).add(Integer.MAX_VALUE+0.0);
            }
        }
    }

    public void keyVertex(T value){
        if (!pos.containsKey(value)){
            if (posRemove.size()!=0){
                pos.put(value, posRemove.get(0));
                posIndex.put(posRemove.get(0), value);
                posRemove.remove(0);
            } else {
                posIndex.put(keyPos, value);
                pos.put(value, keyPos);
                keyPos++;
            }
        }
    }

    public void matrixAnt(){
        mapAntFloyd=new ArrayList<>();
        for (int i = 0; i < graph.size(); i++) {
            mapAntFloyd.add(new ArrayList<>());
            for (int j = 0; j < graph.size(); j++) {
                mapAntFloyd.get(i).add(i);
            }
        }
    }

    public String floydWarshall(T startValue, T destinationValue) {
        String output = destinationValue + " ";
        boolean flag = false;
        int posStart = pos.get(startValue);
        int posDestination = pos.get(destinationValue);

        while (!flag) {
            if (mapAntFloyd.get(posStart).get(posDestination) != posStart) {
                output = posIndex.get(mapAntFloyd.get(posStart).get(posDestination)) + " " + output;
                posDestination = mapAntFloyd.get(posStart).get(posDestination);
            } else {
                flag = true;
            }
        }

        output = startValue + " " + output;

        return output;
    }

    public void floydWarshall(){
        mapFloyd = new ArrayList<>();
        mapFloyd.addAll(graph);
        matrixAnt();
        for (int k = 0; k < mapFloyd.size(); k++) {
            for (int i = 0; i < mapFloyd.size(); i++) {
                for (int j = 0; j < mapFloyd.size(); j++) {
                    if (mapFloyd.get(i).get(j)> mapFloyd.get(i).get(k) + mapFloyd.get(k).get(j) ){
                        mapFloyd.get(i).set(j, mapFloyd.get(i).get(k) + mapFloyd.get(k).get(j));
                        mapAntFloyd.get(i).set(j, mapAntFloyd.get(k).get(j));
                    }
                }
            }
        }
    }
}
