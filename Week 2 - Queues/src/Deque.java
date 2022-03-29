import java.util.Iterator;
import java.util.LinkedList;

public class Deque<Item> implements Iterable<Item> {

    private LinkedList<Item> list; //TODO to be removed after i finish implementing
    //Global Variables
    private Node first = null;
    private Node last = null;


    //Private Class Node, It contains Next, Previous & Data (because it is double ended)
    private class Node {
        private Node next;
        private Node previous;
        private Item data;
        public Node(Node next, Node previous, Item data) {
            this.next = next;
            this.previous = previous;
            this.data = data;
        }
    }
    // construct an empty deque
    public Deque() {
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
        first.previous = new Node(first, null, item);
        first = first.previous;
    }
    // add the item to the back
    public void addLast(Item item) {
        last.next = new Node(null, last, item);
        last = last.next;
    }
    // remove and return the item from the front
    public Item removeFirst() {
        Item firstData = first.data;
        first = first.next;
        return firstData;
    }
    // remove and return the item from the back
    public Item removeLast() {
        Item lastData = last.data;
        last = last.previous;
        return lastData;
    }
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return list.iterator();
    }
    // unit testing (required)
    public static void main(String[] args) {

    }
}