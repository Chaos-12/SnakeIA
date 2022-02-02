package snake.neat;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final NodeType type;
    private final int id;

    private double weight = 1.0;
    private int deepness = 0;

    private List<Connection> links = new ArrayList<Connection>();

    public Node(int nodeId, NodeType nodeType){
        this.id = nodeId;
        this.type = nodeType;
    }

    public NodeType getType(){
        return this.type;
    }

    public int getId(){
        return this.id;
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

    public void addConnection(Connection link){
        links.add(link);
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof Node)) {
            return false;
        }
        Node otherNode = (Node) object;
        return this.id == otherNode.id;
    }

    @Override
    public int hashCode(){
        return id;
    }
}