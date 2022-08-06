import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    //Global Variables

    //Array object used for storing the enqueued & dequeued items
    private Item[] queue;
    private int numOfItems; //Number of items in an array
    private int lastIndex; //last non-null item in array queue

    private final int FIRST = 0; //first non/null item in array queue, always 0


    // construct an empty randomized queue
    public RandomizedQueue(Item... items) {
        int itemsLength = items.length == 0 ? 1 : items.length;
        queue = (Item[]) new Object[itemsLength];
        numOfItems = items.length;
        lastIndex = -1;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return numOfItems == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return numOfItems;
    }

    // add the item
    public void enqueue(Item item) {
        itemValid(item);

        //check if there is available remaining space before enqueuing
//        StdOut.println("randomizedQueue.queue.length: " + queue.length); //Todo remove it
//        StdOut.println("randomizedQueue.numOfItems: " + numOfItems); //Todo remove it
//        StdOut.println("randomizedQueue.lastIndex before: " + lastIndex); //Todo remove it
        queue[++lastIndex] = item;
//        StdOut.println("randomizedQueue.lastIndex After: " + lastIndex); //Todo remove it
        numOfItems++;
        resize();
    }

    // remove and return a random item
    public Item dequeue() {
        Object[] node = this.returnRandomNode();
        int randomNumber = (int) node[0];
        Item item = (Item) node[1];
        queue[randomNumber] = null;
        numOfItems--;
        resize();
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        return (Item) this.returnRandomNode()[1];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private int current = FIRST;

        public boolean hasNext() {
            return queue[current] != null;
        }

        public Item next() {
            checkArrayEmpty();
            Item item = (Item) queue[current];
            current++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() should not be called");
        }
    }

    //Returns encapsulated node in an object array where first item is index and second is value
    private Object[] returnRandomNode() {
        checkArrayEmpty();
        Item item;
        int randomNumber;
        do {
            randomNumber = this.genRandIndexNumber();
            item = (Item) queue[randomNumber];
        } while (item == null);

        return new Object[] {randomNumber, item};
    }

    private int genRandIndexNumber() {
        return StdRandom.uniform(numOfItems);
    }

    private void checkArrayEmpty() {
        for (Item item : queue) {
            if (item != null) return;
        }
        throw new NoSuchElementException("I was called to remove first/last on an empty list");
    }

    private void resize() {
        if (numOfItems == queue.length) {
            cloneToArrayOfSize(numOfItems * 2);

        } else if (numOfItems == queue.length / 4) {
            cloneToArrayOfSize(numOfItems / 2);
        }
    }

    private void cloneToArrayOfSize(int newSize) {
        Item[] newArr = (Item[]) new Object[newSize];
        int pointer = -1;
        for (Item item : queue) {
            if (item != null) newArr[++pointer] = item;
        }
        queue = newArr;
        lastIndex = pointer;
    }

    // Check if the item is null
    private void itemValid(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("I was called with to add/remove null");
        }
    }

    //TODO: to be removed, this is just for testing purposes to ensure enqueuing is correct
    private String print() {
        String temp = "";
        for (Item item : queue) {
            temp = temp + item + ",";
        }
        return "[" + temp + "]";
    }

    public static void main(String[] args) {
        //TODO!!: main() method must call directly every public constructor and method to verify that they work as prescribed
        StdOut.println("###############RandomizedQueue Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");
        StdOut.println("Initialize an empty RandomizedQueue");
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        StdOut.println("Constructor & hasNext: " +  randomizedQueue);
        StdOut.println("list.size() is zero: " + (randomizedQueue.size() == 0));
        StdOut.println("list.isEmpty() is true: " + randomizedQueue.isEmpty());

        StdOut.println();

        StdOut.println("Start of Error Cases");
        StdOut.println();

        StdOut.print("list.removeFirst() is error: ");
        try {
            StdOut.println(randomizedQueue.dequeue());
        } catch (NoSuchElementException e) {
            StdOut.println(e);
        }

        StdOut.println();
        StdOut.println("End of Error Cases");

        StdOut.println();

        StdOut.println("\"Enqueue()\" Test Cases");
        for (int i = 1; i <= 10; i++) {
//            StdOut.println("This is iteration: " + i + " and i will append " + Integer.toString(i)); //Todo remove it
            randomizedQueue.enqueue(Integer.toString(i));
        }
        StdOut.println("list has only [1,2,3,4,5,6,7,8,9,10]: " + randomizedQueue.print());
        StdOut.println();
    }
}