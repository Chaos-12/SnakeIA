package snake.neat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NetworkTest {

    @BeforeEach
    public void resetNumberOfNodes(){
        Network.setNumberOfNodes(1, 1);
    }

    @Test
    public void firstNodeIsBias() {
        Network network = new Network();
        Node firstNode = network.nodeList.get(0);
        assertEquals(NodeType.bias, firstNode.getType());
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 5, 12 })
    public void rightNumberInputNodes(int nInputs) {
        Network.setNumberOfNodes(nInputs, 1);
        Network network = new Network();
        int counter = 0;
        for (int i = 0; i < network.nodeList.size(); i++) {
            if (network.nodeList.get(i).getType() == NodeType.sensor) {
                counter++;
            }
        }
        assertEquals(nInputs, counter);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 5, 12 })
    public void rightNumberOutputNodes(int nOutputs) {
        Network.setNumberOfNodes(1, nOutputs);
        Network network = new Network();
        int counter = 0;
        for (int i = 0; i < network.nodeList.size(); i++) {
            if (network.nodeList.get(i).getType() == NodeType.output) {
                counter++;
            }
        }
        assertEquals(nOutputs, counter);
    }

    @Test
    public void outputNodesAreLast() {
        Network network = new Network();
        network.createNewNode(NodeType.hidden);
        network.orderNetwork();
        assertEquals(NodeType.output, network.nodeList.get(network.nodeList.size() - 1).getType());
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 5, 12 })
    public void correctDeepness(int nHiddenNodes) {
        Network network = new Network();
        int sensorNodeId = 1;
        int outputNodeId = 2;
        network.createNewNode(10, NodeType.hidden);
        network.createNewConnection(sensorNodeId, 10, 1.0);
        for (int i = 1; i < nHiddenNodes; i++) {
            network.createNewNode(10 + i, NodeType.hidden);
            network.createNewConnection(9 + i, 10 + i, 1.0);
        }
        network.createNewConnection(9 + nHiddenNodes, outputNodeId, 1.0);
        network.orderNetwork();
        assertEquals(nHiddenNodes + 1, network.getDeepness());
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.0, 0.0, -1.0, 0.5, -0.33 })
    public void connectionWeight(double weight) {
        Network network = new Network();
        int sensorNodeId = 1;
        int outputNodeId = 2;
        network.createNewConnection(sensorNodeId, outputNodeId, weight);
        network.getDecision(new double[] { 1.0 });
        assertEquals(weight, network.nodeList.get(outputNodeId).getWeight());
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1.0, 0.0, -1.0, 0.5, -0.33 })
    public void inputWeight(double weight) {
        Network network = new Network();
        int sensorNodeId = 1;
        int outputNodeId = 2;
        network.createNewConnection(sensorNodeId, outputNodeId, 1.0);
        network.getDecision(new double[] { weight });
        assertEquals(weight, network.nodeList.get(outputNodeId).getWeight());
    }

    @Test
    public void getOutputWithMaxWeight() {
        Network.setNumberOfNodes(1, 2);
        Network network = new Network();
        int sensorNodeId = 1;
        int outputNodeId1 = 2;
        int outputNodeId2 = 3;
        network.createNewConnection(sensorNodeId, outputNodeId1, 1.0);
        network.createNewConnection(sensorNodeId, outputNodeId2, -1.0);
        assertEquals(0, network.getDecision(new double[] { 1.0 }));
        assertEquals(1, network.getDecision(new double[] { -1.0 }));
    }
}
