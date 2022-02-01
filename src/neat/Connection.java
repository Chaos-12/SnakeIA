package neat;

public class Connection {
    private final Node startNode;
    private final Node finalNode;
    private final int label;
    private double weight;
    private boolean enabled = true;

    public Connection(Node from, Node to, int innovationNumber, double weight){
        this.startNode = from;
        this.finalNode = to;
        this.label = innovationNumber;
        this.weight = weight;
    }

    public Connection(Node from, Node to, int innovationNumber){
        this(from, to, innovationNumber, Neat.getRandomWeight());
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
}
