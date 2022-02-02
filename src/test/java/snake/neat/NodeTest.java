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
    @ValueSource(ints = { 0, 1, 2, 12 })
    public void propagateDeepnessChangesDeep(int newDeepness) {
        Node neuron = new Node(0, NodeType.hidden);
        neuron.propagateDeepness(newDeepness);
        assertEquals(newDeepness, neuron.getDeepness());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 12 })
    public void propagateDeepnessOnlyIfGreater(int newDeepness) {
        Node neuron = new Node(0, NodeType.hidden);
        neuron.propagateDeepness(newDeepness + 1);
        neuron.propagateDeepness(newDeepness);
        assertEquals(newDeepness + 1, neuron.getDeepness());
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 2, 12 })
    public void propagateDeepnessPropagates(int newDeepness) {
        Node neuron1 = new Node(0, NodeType.hidden);
        Node neuron2 = new Node(1, NodeType.hidden);
        Connection link = new Connection(neuron1, neuron2, 0.0);
        neuron1.addConnection(link);
        neuron1.propagateDeepness(newDeepness);
        assertEquals(newDeepness + 1, neuron2.getDeepness());
    }
}
