package snake.neat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Network {
    private static int nInputs = 1;
    private static int nOutputs = 1;

    private final static Map<Connection, Integer> innovationMap = new HashMap<Connection, Integer>();
    private final Map<Integer, Connection> connectionMap = new HashMap<Integer, Connection>();
    private final Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();
    public final List<Node> nodeList = new ArrayList<Node>();

    private int deepness = 1;

    public Network(){
        this.createNewNode(NodeType.bias);
        for(int i=0; i < Network.nInputs; i++){
            this.createNewNode(NodeType.sensor);
        }
        for(int i=0; i < Network.nOutputs; i++){
            this.createNewNode(NodeType.output);
        }
        orderNetwork();
    }

    public static void setNumberOfNodes(int nInputs, int nOutputs){
        Network.nInputs = nInputs;
        Network.nOutputs = nOutputs;
    }

    public void createNewNode(NodeType type){
        this.createNewNode(nodeMap.size(), type);
    }

    public void createNewNode(int nodeId, NodeType type){
        this.nodeMap.put(nodeId, new Node(nodeId, type));
        //We create a connection to the node from Bias (weight=0)
        if(type == NodeType.hidden || type == NodeType.output){
            createNewConnection(0, nodeId, 0);
        }
    }

    public void createNewConnection(int from, int to, double weight){
        Connection link = new Connection(nodeMap.get(from), nodeMap.get(to), weight);
        int connectionId = innovationMap.size();
        if (innovationMap.containsKey(link)){
            connectionId = innovationMap.get(link);
        }
        nodeMap.get(from).addConnection(link);
        innovationMap.put(link, connectionId);
        connectionMap.put(connectionId, link);
    }
    
    public int getDecision(double[] input){
        //Clear weights of all the nodes
        for (int nodeId : nodeMap.keySet()) {
            nodeMap.get(nodeId).setWeight(0);
        }
        //Bias node always weights 1
        nodeMap.get(0).setWeight(1);
        //Assign sensor nodes with input weights
        for (int i=0; i < Network.nInputs; i++){
            nodeMap.get(i+1).setWeight(input[i]);
        }
        //Propagate the input to the rest of the network
        for (Node node : nodeList) {
            node.propagate();
        }
        //Find the output node with the highest weight
        int index = nInputs+1;
        double maximum = nodeMap.get(index).getWeight();
        for (int i = index+1; i <= nInputs+nOutputs; i++){
            if (maximum < nodeMap.get(i).getWeight()){
                maximum = nodeMap.get(i).getWeight();
                index = i;
            }
        }
        index -= nInputs+1;
        return index;
    }

    public void orderNetwork(){
        //Assign layer=0 to the sensor nodes (and propagate)
        for (int i=1; i <= Network.nInputs; i++){
            nodeMap.get(i).assignLayer(0);
        }
        //The deepness of the network is the maximum layer between the (output) nodes
        deepness = 1;
        for (int i = Network.nInputs+1; i <= Network.nOutputs+Network.nInputs; i++){
            if (deepness < nodeMap.get(i).getLayer()){
                deepness = nodeMap.get(i).getLayer();
            }
        }
        //All output nodes should be in the same (last) layer
        for (int i = Network.nInputs+1; i <= Network.nOutputs+Network.nInputs; i++){
            nodeMap.get(i).assignLayer(deepness);
        }
        //We order the nodes into a list
        nodeList.clear();
        for (int d=0; d <= deepness; d++){
            int positionLayer = 0;
            for (int nodeId : nodeMap.keySet()) {
                Node node = nodeMap.get(nodeId);
                if (node.getLayer() == d){
                    node.setIndexLayer(positionLayer);
                    positionLayer ++;
                    nodeList.add(node);
                }
            }
        }
    }

    public int getDeepness(){
        return this.deepness;
    }
}
