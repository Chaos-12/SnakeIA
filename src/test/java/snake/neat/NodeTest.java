package snake.neat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

public class NodeTest {

    @ParameterizedTest
    @EnumSource(NodeType.class)
    public void rightType(NodeType type) {
        Node neuron = new Node(0, type);
        assertEquals(neuron.getType(), type);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 12, -3, Integer.MAX_VALUE})
    public void equals(int nodeId){
        Node neuron1 = new Node(nodeId, NodeType.sensor);
        Node neuron2 = new Node(nodeId, NodeType.sensor);
        assertEquals(neuron1, neuron2);
    }
}
