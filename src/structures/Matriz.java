package structures;

import java.util.ArrayList;
import java.util.HashMap;

public class Matriz<T> {
    //-------------------------------------------------- Attributes
    private final HashMap<T, Integer> pos = new HashMap<>();
    private final ArrayList<ArrayList<Double>> map;
    private final int numVertex;
    private int keyPos = 0;
    private ArrayList<Integer> posRemove = new ArrayList<>();
    private ArrayList<ArrayList<Double>> mapFloyd= new ArrayList<>();
    private ArrayList<ArrayList<Integer>> mapAntFloyd= new ArrayList<>();

    //-------------------------------------------------- Constructor
    public Matriz(int numVertex) {
        this.map = new ArrayList<>();
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
    public void fillMatrix (){
        for (int i = 0; i < numVertex; i++) {
            map.add(new ArrayList<>());
            for (int j = 0; j < numVertex; j++) {
                map.get(i).add(Integer.MAX_VALUE+0.0);
            }
        }
    }

    public void keyVertex(T value){
        if (!pos.containsKey(value)){
            if (posRemove.size()!=0){
                pos.put(value, posRemove.get(0));
                posRemove.remove(0);
            } else {
                pos.put(value, keyPos);
                keyPos++;
            }
        }
    }

    public void addWeight(T value1, T value2, double weight){
        int pos1 = pos.get(value1);
        int pos2 = pos.get(value2);
        map.get(pos1).set(pos2, weight);
        floydWarshall();
    }

    public void removeWeight(T value1, T value2){
        int pos1 = pos.get(value1);
        int pos2 = pos.get(value2);
        map.get(pos1).set(pos2, Integer.MAX_VALUE+0.0);
        floydWarshall();
    }

    public void addVertex(){
        for (ArrayList<Double> doubles : map) {
            doubles.add(Integer.MAX_VALUE + 0.0);
        }
        map.add(new ArrayList<>());
        for (int i = 0; i < map.size(); i++) {
            map.get(map.size()-1).add(Integer.MAX_VALUE+0.0);
        }
        floydWarshall();
    }

    public void removeVertex(T value){
        int posi = pos.get(value);
        posRemove.add(posi);
        for (ArrayList<Double> doubles : map) {
            doubles.set(posi, Integer.MAX_VALUE + 0.0);
        }
        for (int i = 0; i < map.size(); i++) {
            map.get(posi).set(i, Integer.MAX_VALUE+0.0);
        }
        floydWarshall();
    }

    public void matrixAnt(){
        mapAntFloyd=new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            mapAntFloyd.add(new ArrayList<>());
            for (int j = 0; j < map.size(); j++) {
                mapAntFloyd.get(i).add(i);
            }
        }
    }

    public void floydWarshall(){
        mapFloyd = new ArrayList<>();
        mapFloyd.addAll(map);
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
