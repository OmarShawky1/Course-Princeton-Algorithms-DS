import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // Global Variables
    private Node first;
    private Node last;
    private int size;

    // construct deque with initial values
    /*
    public Deque(Item ... items) {
        // Initialize first & last pointers to null nodes
        first = null;
        last = first;

        //Add Initial values
        for (Item item : items) {
            addLast(item);
        }
    }
    */

    public Deque() {
        // Initialize first & last pointers to null nodes
        first = null;
        last = first;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        itemValid(item);

        // Initialize a node that has nothing before it (null) and old first after it.
        Node newNode  = new Node(null, first, item);

        // If list contains items, make current first.previous point to newNode & make it first
        if (!isEmpty()) {
            first.previous = newNode;
            first = newNode; // or newNode
        // If list is empty, then first & last nodes should point to the new node
        } else {
            first = newNode;
            last = newNode;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        itemValid(item);

        // Initialize a node that has old last before it and nothing after it.
        Node newNode = new Node(last, null, item);

        // Initialize a node that has (last) before it and nothing after it (null).
        if (!isEmpty()) {
            last.next = newNode;
            last = last.next;
            // If list is empty, then first & last nodes should point to the new node
        } else {
            first = newNode;
            last = newNode;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("I was called to remove first/last on an empty list");
        }

        Item firstItem = first.item;
        first = first.next;
        size--;

        // if list became empty // (first == null)
        if (isEmpty()) {
            last = first;
        } else {
            first.previous = null;
        }
        return firstItem;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("I was called to remove first/last on an empty list");
        }

        Item lastData = last.item;
        last = last.previous;
        size--;

        // if list became empty (last == null)
        if (isEmpty()) {
            first = last;
        } else {
            last.next = null;
        }
        return lastData;
    }
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // Check if the item is null
    private void itemValid(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("I was called with to add/remove null");
        }
    }

    // Check if list (that is being operated) is empty
    /*
    private void checkListEmpty() {
        if (first == null) {
            throw new NoSuchElementException("I was called to remove first/last on an empty list");
        }
    }
    */

    // Although this is not a required function to implement but it is necessary for debugging
    public String toString() {
        StringBuilder listToString = new StringBuilder("[");
        Iterator<Item> iterator = iterator();
        boolean hasNext = iterator.hasNext();
        while (hasNext) {
            listToString.append(iterator.next());
            hasNext = iterator.hasNext();
            if (hasNext) {
                listToString.append(", ");
            }
        }
        listToString.append("]");
        return listToString.toString();
    }

    // Private Class Node, It contains Next, Previous & Data (because it is double ended)
    private class Node {
        private Node next;
        private Node previous;
        private final Item item;
        public Node(Node previous, Node next, Item item) {
            this.previous = previous;
            this.next = next;
            this.item = item;
        }
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("I was called to remove first/last on an empty list");
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() should not be called");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        StdOut.println("###############Dequeue Tests###############");
        /*
        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");
        StdOut.println("Initialize an empty Double ended LinkedList");
        Deque<String> list = new Deque<>();
        StdOut.println("Constructor & hasNext: " +  list);
        StdOut.println("list.size() is zero: " + (list.size() == 0));
        StdOut.println("list.isEmpty() is true: " + list.isEmpty());

        StdOut.println();

        StdOut.println("Start of Error Cases");
        StdOut.println();

        StdOut.print("list.removeFirst() is error: ");
        try {
            StdOut.println(list.removeFirst());
        } catch (NoSuchElementException e) {
            StdOut.println(e);
        }

        StdOut.print("list.removeLast() is error: ");
        try {
            StdOut.println(list.removeLast());
        } catch (NoSuchElementException e) {
            StdOut.println(e);
        }

        StdOut.println();
        StdOut.println("End of Error Cases");

        StdOut.println();

        StdOut.println("\"Add()\" Test Cases");
        for (int i = 1; i <= 10; i++) {
            list.addLast(Integer.toString(i));
        }
        StdOut.println("list has only [1,2,3,4,5,6,7,8,9,10]: " + list);
        StdOut.println();

        StdOut.println("\"Remove()\" Test Cases");
        for (int i = 1; i < 7; i++) {
            list.removeFirst();
        }
        StdOut.println("list has only [7, 8, 9, 10]: " + list);
        StdOut.println();

        StdOut.println("\"addFirst()\" Test Cases");
        for (int i = 6; i > 0; i--) {
            list.addFirst(Integer.toString(i));
        }
        StdOut.println("list has only [1,2,3,4,5,6,7,8,9,10]: " + list);

        StdOut.print("Trying to remove using iterator().remove(): ");
        try {
            list.iterator().remove();
        } catch (UnsupportedOperationException e) {
            StdOut.println(e);
        }

        StdOut.println("####Test 1 End####");

        StdOut.println();
        StdOut.println();
        */
        /*
        StdOut.println("####Test 2####");
        StdOut.println("Initialize Double ended LinkedList with initial values");
        Deque<String> deque = new Deque<>("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        StdOut.println("Constructor & has next: " +  deque);
        StdOut.println("deque.size(): " + deque.size());
        StdOut.println("####Test 2 End####");

        StdOut.println("##########End of my Tests##########");
        StdOut.println();
        */

        StdOut.println("##########Online Grader Tests Cases##########"); // All that failed only

        StdOut.println("####Test 1####");
        Deque<Integer> deque = new Deque<>();
        StdOut.println("addFirst test");
        deque.addFirst(1);
        StdOut.println("deque: " + deque);

        StdOut.println("removeLast test");
        deque.removeLast();
        StdOut.println("deque: " + deque);
        StdOut.println("####Test 1 End####");

        StdOut.println();

        StdOut.println("####Test 2 Start####");
        deque = new Deque<>();
        deque.addFirst(1);
        deque.removeFirst();
    }
}