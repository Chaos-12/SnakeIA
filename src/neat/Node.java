package neat;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final NodeType type;
    private final int label;
    private double weight;
    private int deepness;
    private int order;

    private List<Node> neighborhood;

    public Node(int label, NodeType type){
        this.type = type;
        this.label = label;
        this.weight = 1.0;
        this.deepness = 0;
        this.order = 0;

        this.neighborhood = new ArrayList<Node>();
    }

    public double addWeight(double value){
        return this.weight += value;
    }
}