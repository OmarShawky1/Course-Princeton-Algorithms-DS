import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    //Global Variables
    private Node first = null;
    private Node last = null;

    //Private Class Node, It contains Next, Previous & Data (because it is double ended)
    private class Node {
        private Node next;
        private Node previous;
        private Item item;
        public Node(Node next, Node previous, Item item) {
            this.next = next;
            this.previous = previous;
            this.item = item;
        }
    }

    private class ListIterator<Item> implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current.next != null;
        }

        public Item next() {
            checkListEmpty();
            Item item = (Item) current.item;
            current = current.next;
            return item;
        }

        public void remove(){
            throw new UnsupportedOperationException("remove() should not be called");
        }
    }
    // construct an empty deque
    public Deque(Node ...nodes) {
        Node pointer = first;
        for (Node node: nodes
             ) {
            pointer.next = node;
            node.previous = pointer;
            pointer = pointer.next;
        }
        last = pointer; //pointer will stop at the last node that has been added
    }
    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }
    // return the number of items on the deque
    public int size() {
        int counter = 0;
        while (first.next != null){
            first = first.next;
            counter++;
        }
        return counter;
    }
    // add the item to the front
    public void addFirst(Item item) {
        itemValid(item);
        first.previous = new Node(first, null, item);
        first = first.previous;
    }
    // add the item to the back
    public void addLast(Item item) {
        itemValid(item);
        last.next = new Node(null, last, item);
        last = last.next;
    }
    // remove and return the item from the front
    public Item removeFirst() {
        checkListEmpty();
        Item firstItem = first.item;
        first = first.next;
        return firstItem;
    }
    // remove and return the item from the back
    public Item removeLast() {
        checkListEmpty();
        Item lastData = last.item;
        last = last.previous;
        return lastData;
    }
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator<>();
    }

    // Check if the item is null
    private void itemValid(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("I was called with to add/remove null");
        }
    }

    // Check if list, being operated, is empty
    private void checkListEmpty() {
        if (first == null) {
            throw new NoSuchElementException("I was called to remove first/last on an empty list");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}