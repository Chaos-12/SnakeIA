package snake.neat;

import java.util.HashMap;
import java.util.Map;

public class Network {
    private static int nInputs = 1;
    private static int nOutputs = 1;

    private final static Map<Connection, Integer> innovationMap = new HashMap<Connection, Integer>();
    private final Map<Integer, Connection> connectionMap = new HashMap<Integer, Connection>();
    private final Map<Integer, Node> nodeMap = new HashMap<Integer, Node>();

    public Network(){
        this.createNewNode(NodeType.bias);
        for(int i=0; i < Network.nInputs; i++){
            this.createNewNode(NodeType.sensor);
        }
        for(int i=0; i < Network.nOutputs; i++){
            this.createNewNode(NodeType.output);
        }
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
}
