package snake.neat;

public class Connection {
    private final Node startNode;
    private final Node finalNode;
    private double weight;
    private boolean enabled = true;

    public Connection(Node from, Node to, double weight){
        this.startNode = from;
        this.finalNode = to;
        this.weight = weight;
    }

    public void propagateWeight(){
        if(this.enabled){
            this.finalNode.addWeight(this.startNode.getWeight()*this.weight);
        }
    }

    public void propagateDeepness(){
        this.finalNode.propagateDeepness(this.startNode.getDeepness()+1);
    }

    public void setEnabled(boolean value){
        this.enabled = value;
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof Connection)) {
            return false;
        }
        Connection otherConnection = (Connection) object;
        return this.startNode.equals(otherConnection.startNode) && this.finalNode.equals(otherConnection.finalNode);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + startNode.hashCode();
        hash = 31 * hash + finalNode.hashCode();
        return hash;
    }
}
