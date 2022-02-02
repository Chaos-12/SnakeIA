package snake.neat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NodeTest {

    @ParameterizedTest
    @EnumSource(NodeType.class)
    public void rightType(NodeType type) {
        Node neuron = new Node(0, type);
        assertEquals(type, neuron.getType());
    }

    @Test
    public void initialWeight() {
        Node neuron = new Node(0, NodeType.sensor);
        assertEquals(1.0, neuron.getWeight());
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.0, 1.0, -1.0, -0.33, 0.5 })
    public void addWeight(double value) {
        Node neuron = new Node(0, NodeType.sensor);
        double initialWeight = neuron.getWeight();
        neuron.addWeight(value);
        assertEquals(initialWeight + value, neuron.getWeight());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 12, -3, Integer.MAX_VALUE })
    public void equals(int nodeId) {
        Node neuron1 = new Node(nodeId, NodeType.sensor);
        Node neuron2 = new Node(nodeId, NodeType.sensor);
        assertEquals(neuron1, neuron2);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 12 })
    public void assignLayerChangesLayer(int newLayer) {
        Node neuron = new Node(0, NodeType.hidden);
        neuron.assignLayer(newLayer);
        assertEquals(newLayer, neuron.getLayer());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 12 })
    public void assignLayerOnlyIfGreater(int newLayer) {
        Node neuron = new Node(0, NodeType.hidden);
        neuron.assignLayer(newLayer + 1);
        neuron.assignLayer(newLayer);
        assertEquals(newLayer + 1, neuron.getLayer());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 12 })
    public void assignLayerPropagates(int newLayer) {
        Node neuron1 = new Node(0, NodeType.sensor);
        Node neuron2 = new Node(1, NodeType.hidden);
        Connection link = new Connection(neuron1, neuron2, 0.0);
        neuron1.addConnection(link);
        neuron1.assignLayer(newLayer);
        assertEquals(newLayer + 1, neuron2.getLayer());
    }
}
