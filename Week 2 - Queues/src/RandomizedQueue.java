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
        numOfItems = 0;
        lastIndex = -1;

        for (Item item : items) {
//            StdOut.println("Adding item " + item); //Todo remove it
            enqueue(item);
        }
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
//        StdOut.println("randomizedQueue.queue.length Before: " + queue.length); //Todo remove it
//        StdOut.println("randomizedQueue.numOfItems Before: " + numOfItems); //Todo remove it
//        StdOut.println("randomizedQueue.lastIndex before: " + lastIndex); //Todo remove it
        queue[++lastIndex] = item;
//        StdOut.println("randomizedQueue.lastIndex After: " + lastIndex); //Todo remove it
        numOfItems++;
//        StdOut.println("randomizedQueue.queue.length After: " + queue.length); //Todo remove it
//        StdOut.println("randomizedQueue.numOfItems After: " + numOfItems); //Todo remove it
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

        } else if (numOfItems <= queue.length / 4) {
//            StdOut.println("queue.length / 4 = " + (queue.length / 4));//Todo remove it
//            StdOut.println("numOfItems = " + numOfItems);//Todo remove it
            cloneToArrayOfSize(numOfItems * 2);
        }
    }

    private void cloneToArrayOfSize(int newSize) {
        Item[] newArr = (Item[]) new Object[newSize];
        int pointer = -1;
        for (Item item : queue) {
//            StdOut.println("pointer+1: " + (pointer+1));
//            StdOut.println("newSize: " + newSize);
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
    public String toString() {
        String temp = "";
        for (Item item : queue) {
            temp = temp + item + ",";
        }
        return "[" + temp + "]";
    }

    public static void main(String[] args) {
        StdOut.println("###############RandomizedQueue Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");
        StdOut.println("Initialize an empty RandomizedQueue");
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        StdOut.println("Constructor & hasNext: " +  randomizedQueue);
        StdOut.println("randomizedQueue.size() is zero: " + (randomizedQueue.size() == 0));
        StdOut.println("randomizedQueue.isEmpty() is true: " + randomizedQueue.isEmpty());

        StdOut.println();

        StdOut.println("Start of Error Cases");
        StdOut.println();

        StdOut.print("randomizedQueue.dequeue() is error: ");
        try {
            StdOut.println(randomizedQueue.dequeue());
        } catch (NoSuchElementException e) {
            StdOut.println(e);
        }

        StdOut.println("enqueuing null item:");
        try {
            randomizedQueue.enqueue(null);
        } catch (IllegalArgumentException e){
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
        StdOut.println("randomizedQueue has only [1,2,3,4,5,6,7,8,9,10]: " + randomizedQueue);
        StdOut.println();

        StdOut.println("\"Dequeue()\" Test Cases");
        randomizedQueue.dequeue();
        StdOut.println("randomizedQueue has removed one item " + randomizedQueue);
        StdOut.println();

        StdOut.print("Trying to remove using iterator().remove(): ");
        try {
            randomizedQueue.iterator().remove();
        } catch (UnsupportedOperationException e) {
            StdOut.println(e);
        }

        StdOut.println("####Test 1 End####");

        StdOut.println();
        StdOut.println();

        StdOut.println("####Test 2####");
        StdOut.println("Initialize RandomizedQueue Array List with initial values");
        RandomizedQueue<String> randomizedQueue2 = new RandomizedQueue<>("1", "2", "3", "4", "5",
                "6", "7", "8", "9", "10");
        StdOut.println("Constructor & has next: " +  randomizedQueue2);
        StdOut.println("randomizedQueue2.size(): " + randomizedQueue2.size());

        StdOut.println("Enqueue after (initialize with initial values) test");
        randomizedQueue2.enqueue("11");
        StdOut.println("Constructor & has next: " +  randomizedQueue2);
        StdOut.println("randomizedQueue2.size(): " + randomizedQueue2.size());
        StdOut.println("####Test 2 End####");

        StdOut.println("##########End of my Tests##########");
        StdOut.println();
    }
}