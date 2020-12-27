package fr.unice.miage.graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GraphTest {

    @Test
    void test_set_value() {
        Node<Integer> value = new Node<>(5);
        assertEquals(5, value.getValue());
        assertTrue(value.getSub().isEmpty());
        value.setValue(6);
        assertEquals(6, value.getValue());
        assertTrue(value.getSub().isEmpty());
        value.add(value);
        assertEquals(6, value.getValue());
        assertFalse(value.getSub().isEmpty());
    }

    @Test
    void test_iterator() {
        Iterator<Integer> iterator = Node.fabric(1,
                new Node<>(2),
                new Node<>(3),
                Node.fabric(4,
                        new Node<>(5)
                )
        ).iterator();
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void test_empty_list() {
        Iterator<Integer> iterator = new DepthIterator<>(Collections.emptyIterator());
        assertFalse(iterator.hasNext());
    }

    @Test
    void test_trash_list() {
        List<Node<Integer>> trashList = new ArrayList<>();
        Collections.addAll(trashList, null, Node.fabric(5), null, Node.fabric(6,
                Node.fabric(8),
                null,
                Node.fabric(9)
        ), null);
        Iterator<Integer> iterator = new DepthIterator<>(trashList.iterator());
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(8, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(9, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(6, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void test_trash_list_2() {
        List<Node<Integer>> trashList = new ArrayList<>();
        Collections.addAll(trashList, null, null, null, null);
        Iterator<Integer> iterator = new DepthIterator<>(trashList.iterator());
        assertFalse(iterator.hasNext());
        trashList.add(Node.fabric(5, null, null, null));
        iterator = new DepthIterator<>(trashList.iterator());
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void test_subnode() {
        Iterator<Integer> iterator = Node.fabric(5,
                Node.fabric(4,
                        Node.fabric(
                                3,
                                new Node<>(2)
                        )
                )
        ).iterator();
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void test_graph() {
        List<Node<Integer>> nodes = new ArrayList<>();
        nodes.add(Node.fabric(1,
                new Node<>(2),
                new Node<>(3),
                Node.fabric(4,
                        new Node<>(5)
                )
        ));
        Node<Integer> s = new Node<>(6);
        s.add(new Node<>(7));
        nodes.add(s);

        Iterator<Integer> iterator = new DepthIterator<>(nodes.iterator());
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(7, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(6, iterator.next());
        assertFalse(iterator.hasNext());
    }
    @Test
    void test_graph_duplicateNode() {
        Node<Integer> splitNode = Node.fabric(7,
                Node.fabric(8),
                Node.fabric(9,
                        Node.fabric(10),
                        Node.fabric(11)
                ),
                Node.fabric(12)
        );
        List<Node<Integer>> nodes = new ArrayList<>();
        nodes.add(Node.fabric(1,
                Node.fabric(2),
                Node.fabric(3),
                Node.fabric(4,
                        Node.fabric(5)
                ),
                splitNode
        ));
        nodes.add(Node.fabric(6));
        nodes.add(splitNode);

        Iterator<Integer> iterator = new DepthIterator<>(nodes.iterator());
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(5, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(4, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(8, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(11, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(9, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(12, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(7, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(6, iterator.next());
        assertFalse(iterator.hasNext());
    }
}
