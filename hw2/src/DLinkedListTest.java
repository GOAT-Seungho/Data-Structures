import java.util.Comparator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DLinkedListTest {

    DLinkedList<Term> list;

    @Test
    void should_create_a_new_node() {
        Node<Term> node = new Node<>(new Term(1.0, 2));

        assertEquals(new Term(1.0, 2), node.getInfo());
        assertEquals(1.0, node.getInfo().coeff);
        assertEquals(2, node.getInfo().expo);
        assertNull(node.getNext());
        assertNull(node.getPrev());
    }

    @Test
    void should_create_an_empty_list() {

        DLinkedList<Term> emptyList = new DLinkedList<>();

        assertTrue(emptyList.isEmpty());
        assertEquals(0, emptyList.size());
    }

    @Test
    void should_increment_size_if_new_node_added_at_front() {

        DLinkedList<Term> list = new DLinkedList<>();

        list.addFirst(new Node<>(new Term(2.0, 2)));
        assertEquals(new Term(2.0, 2), list.getFirstNode().getInfo());
        assertEquals(1, list.size());

        list.addFirst(new Node<>(new Term(1.0, 1)));
        assertEquals(new Term(1.0, 1), list.getFirstNode().getInfo());
        assertEquals(2, list.size());
    }

    @Test
    void should_increment_size_if_new_node_added_at_rear() {

        DLinkedList<Term> list = new DLinkedList<>();

        list.addLast(new Node<>(new Term(2.0, 2)));
        assertEquals(new Term(2.0, 2), list.getLastNode().getInfo());
        assertEquals(1, list.size());

        list.addLast(new Node<>(new Term(1.0, 1)));
        assertEquals(new Term(1.0, 1), list.getLastNode().getInfo());
        assertEquals(2, list.size());
    }

    @Test
    void should_return_the_node_with_equal_info() {

        DLinkedList<Term> list = new DLinkedList<>();

        list.addFirst(new Node<>(new Term(100.0, 100)));
        list.addFirst(new Node<>(new Term(50.0, 50)));
        list.addFirst(new Node<>(new Term(1.0, 1)));

        Comparator<Term> comparator = (t1, t2) -> {
            return t1.equals(t2) ? 0 : 1;
        };

        Node<Term> node = list.find(new Term(50.0, 50), comparator);
        assertEquals(new Term(50.0, 50), node.getInfo());
    }

    @Test
    void should_return_empty_if_no_node_with_equal_info_exists() {

        DLinkedList<Term> list = new DLinkedList<>();

        list.addFirst(new Node<>(new Term(100.0, 100)));
        list.addFirst(new Node<>(new Term(50.0, 50)));
        list.addFirst(new Node<>(new Term(1.0, 1)));

        Comparator<Term> comparator = (t1, t2) -> {
            return t1.equals(t2) ? 0 : 1;
        };

        Node<Term> node = list.find(new Term(30.0, 50), comparator);
        assertNull(node);
    }

    @Test
    void should_return_the_node_with_matching_exponent() {
        DLinkedList<Term> list = new DLinkedList<>();

        list.addFirst(new Node<>(new Term(100.0, 100)));
        list.addFirst(new Node<>(new Term(50.0, 50)));
        list.addFirst(new Node<>(new Term(1.0, 1)));

        // the coefficient is arbitrary in this case
        Node<Term> node = list.find(new Term(0, 50), Term::compare);
        assertEquals(new Term(50.0, 50), node.getInfo());
    }

    @Test
    void should_add_new_node_in_the_middle_of_the_list_case1() {
        DLinkedList<Term> list = new DLinkedList<>();

        list.addFirst(new Node<>(new Term(1.0, 1)));
        list.addFirst(new Node<>(new Term(50.0, 50)));
        list.addFirst(new Node<>(new Term(100.0, 100)));

        // the coefficient is arbitrary in this case
        Node<Term> current = list.find(new Term(0, 50), Term::compare);

        list.addBefore(current, new Node<>(new Term(15.0, 15)));

        assertEquals(4, list.size());
        assertEquals(new Term(15.0, 15), list.getPrevNode(current).getInfo());
    }

    @Test
    void should_add_new_node_in_the_middle_of_the_list_case2() {
        DLinkedList<Term> list = new DLinkedList<>();

        list.addFirst(new Node<>(new Term(1.0, 1)));
        list.addFirst(new Node<>(new Term(50.0, 50)));
        list.addFirst(new Node<>(new Term(100.0, 100)));

        // the coefficient is arbitrary in this case
        Node<Term> current = list.find(new Term(0, 50), Term::compare);

        list.addAfter(current, new Node<>(new Term(15.0, 15)));

        assertEquals(4, list.size());
        assertEquals(new Term(15.0, 15), list.getNextNode(current).getInfo());
    }

    @Test
    void should_decrement_size_if_a_node_is_removed() {
        DLinkedList<Term> list = new DLinkedList<>();

        list.addFirst(new Node<>(new Term(1.0, 1)));
        list.addLast(new Node<>(new Term(50.0, 50)));
        list.addFirst(new Node<>(new Term(100.0, 100)));
        list.addLast(new Node<>(new Term(75.0, 75)));

        assertEquals(4, list.size());

        Node<Term> current = list.find(new Term(0, 50), Term::compare);
        list.remove(current);

        assertEquals(3, list.size());
    }
}
