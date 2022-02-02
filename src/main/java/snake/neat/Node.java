package snake.neat;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private final NodeType type;
    private final int id;

    private double weight = 1.0;
    private int layer = 0;
    private int indexLayer = 0;

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

    public void setWeight(double value){
        this.weight = value;
    }

    public void addWeight(double value){
        this.weight += value;
    }

    public int getLayer(){
        return this.layer;
    }

    public void assignLayer(int newLayer){
        if (this.type == NodeType.sensor || this.layer < newLayer){
            this.layer = newLayer;
            for (Connection link : this.links) {
                link.propagateDeepness();
            }
        }
    }

    public int getIndexLayer(){
        return this.indexLayer;
    }

    public void setIndexLayer(int index){
        this.indexLayer = index;
    }

    public void propagate(){
        for (Connection link : links) {
            link.propagateWeight();
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