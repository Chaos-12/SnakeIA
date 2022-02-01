package neat;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final NodeType type;
    private final int label;
    private double weight = 1.0;
    private int deepness = 0;
    private int order = 0;

    private List<Connection> links = new ArrayList<Connection>();

    public Node(int label, NodeType type){
        this.type = type;
        this.label = label;
    }

    public double getWeight(){
        return this.weight;
    }

    public void addWeight(double value){
        this.weight += value;
    }

    public int getDeepness(){
        return this.deepness;
    }

    public void propagateDeepness(int newDeepness){
        if (this.type == NodeType.sensor || this.deepness < newDeepness){
            this.deepness = newDeepness;
            for (Connection link : this.links) {
                link.propagateDeepness();
            }
        }
    }
}