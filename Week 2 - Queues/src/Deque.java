import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
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
        return first.item == null;
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

        //if list became empty (first.item = null)
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

        //if list became empty (last.item = null)
        if(last.item == null){
            first = last;
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
        if (first.item == null) {
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

        StdOut.println("###############Dequeue Tests###############");

        StdOut.println("##########My Own Test Cases##########");
        StdOut.println("####Test 1####");
        StdOut.println("Initialize an empty Double ended LinkedList");
        Deque<String> temp = new Deque<>();
        StdOut.println("List: " +  temp);
        StdOut.println("temp.size() is zero: " + (temp.size() == 0));
        StdOut.println("temp.isEmpty() is true: " + temp.isEmpty());
        try {
            StdOut.println("temp.removeFirst() is error: " + temp.removeFirst());
        } catch (NoSuchElementException e) {
            StdOut.println("temp.removeFirst() is error: " + e);
        };

        try {
            StdOut.println("temp.removeLast() is error: " + temp.removeLast());
        } catch (NoSuchElementException e) {
            StdOut.println("temp.removeLast() is error: " + e);
        };
        StdOut.println("####Test 1 End####");

        StdOut.println();

        StdOut.println("####Test 2####");
        StdOut.println("Initialize Double ended LinkedList with initial values");
        Deque<String> deque = new Deque<>("1", "2", "3");
        StdOut.println("Constructor: " +  deque);
        StdOut.println("hasNext test: " + deque);
        StdOut.println("####Test 2 End####");

        StdOut.println("##########End of my Tests##########");
        StdOut.println();

    }
}