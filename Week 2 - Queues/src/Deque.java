import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    //Global Variables
    private Node first;
    private Node last;

    // construct deque with initial values
    public Deque(Item ...items) {

        // Initialize first & last pointers to null nodes
        first = new Node(null, null, null);
        last = new Node(null, null, null);

        //Add Initial values
        for (Item item : items) {
            addFirst(item);
        }
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

        // Initialize a node that has nothing before it (null) and old first after it.
        Node newNode  = new Node(first, null, item);

        // If list contains items, make current first.previous point to newNode & make it first
        if (!isEmpty()){
            first.previous = newNode;
            first = first.previous;
        }
        //If list is empty, then first & last nodes should point to the new node
        else {
            first = newNode;
            last = newNode;
        }

    }
    // add the item to the back
    public void addLast(Item item) {
        itemValid(item);

        Node newNode = new Node(null, last, item);
        if (!isEmpty()){
            last.next = newNode;
            last = last.next;
        } else {
            first = newNode;
            last = newNode;
        }
    }
    // remove and return the item from the front
    public Item removeFirst() {
        checkListEmpty();
        Item firstItem = first.item;
        first = first.next;

        //if list became empty (first = null), make last = null
        if (isEmpty()){
            last = first;
        }
        return firstItem;
    }
    // remove and return the item from the back
    public Item removeLast() {
        checkListEmpty();
        Item lastData = last.item;
        last = last.previous;

        //if list became empty (last = null), make first = null
        if(last == null){
            first = null;
        }
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

    public String toString() {
        StringBuilder listToString = new StringBuilder("[");
        Iterator<Item> iterator = iterator();
        boolean hasNext = iterator.hasNext();
        while (hasNext){
            listToString.append(iterator.next());
            hasNext = iterator.hasNext();
            if (hasNext) {
                listToString.append(", ");
            }
        }
        listToString.append("]");
        return listToString.toString();
    }

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

    // unit testing (required)
    public static void main(String[] args) {

        System.out.println("###############Dequeue Tests###############");

        System.out.println("##########My Own Test Cases##########");
        System.out.println("####Test 1####");
        System.out.println("Initialize an empty Double ended LinkedList");
        Deque<String> temp = new Deque<>();
        System.out.println("List: " +  temp);
        System.out.println("####Test 1 End####");

        System.out.println();

        System.out.println("####Test 2####");
        System.out.println("Initialize Double ended LinkedList with initial values");
        Deque<String> deque = new Deque<>("1", "2", "3");
        System.out.println("List after initialization: " +  deque);
        System.out.println("hasNext test: " + deque);
        System.out.println("####Test 2 End####");

        System.out.println("##########End of my Tests##########");
        System.out.println();

    }
}