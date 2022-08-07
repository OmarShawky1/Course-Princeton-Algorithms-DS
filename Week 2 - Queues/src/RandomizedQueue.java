import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    // Global Variables

    // Array object used for storing the enqueued & dequeued items
    private static final int FIRST = 0; // first non/null item in array queue, always 0
    private static final int BASE_SIZE = 2;

    private Item[] queue;
    private int numOfItems; // Number of items in an array
    private int lastIndex; // last non-null item in array queue


    // construct an empty randomized queue
    /*
    public RandomizedQueue(Item... items) {
        int itemsLength = items.length == 0 ? 1 : items.length;
        queue = (Item[]) new Object[itemsLength];
        numOfItems = 0;
        lastIndex = -1;

        for (Item item : items) {
            enqueue(item);
        }
    }
    */

    public RandomizedQueue() {
        queue = (Item[]) new Object[BASE_SIZE];
        numOfItems = 0;
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

        // check if there is available remaining space before enqueuing
//        StdOut.print("I am enqueue, numOfItems: " + numOfItems + ", queue.length: " + queue.length);
//        StdOut.println(", lastIndex: " + lastIndex);
        queue[++lastIndex] = item;
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
            return current <= lastIndex;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("I was called to remove first/last on an empty list");
            }

            Item item = queue[current];
            current++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() should not be called");
        }
    }

    // Returns encapsulated node in an object array where first item is index and second is value
    private Object[] returnRandomNode() {
        if (isEmpty()) {
            throw new NoSuchElementException("I was called to remove first/last on an empty list");
        }

        Item item;
        int randomNumber;
        do {
            randomNumber = StdRandom.uniform(numOfItems);
            item = queue[randomNumber];
        } while (item == null);

        return new Object[]{randomNumber, item};
    }

    private void resize() {
        /*
        if (numOfItems == 0 && lastIndex == queue.length - 1) {
            cloneToArrayOfSize(BASE_SIZE);
        } else if (numOfItems == queue.length) {
            cloneToArrayOfSize(numOfItems * 2);
        } else if (numOfItems <= queue.length / 4 && queue.length > 3) { // or queue.length != Base
            cloneToArrayOfSize(numOfItems * 2);
        }
         */
        if (lastIndex == queue.length - 1) {
            cloneToArrayOfSize(numOfItems * 2);
        } else if (numOfItems <= queue.length / 4 && queue.length > 3) {
            cloneToArrayOfSize(numOfItems * 2);
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

    // Although this is not a required function to implement, but it is necessary for debugging
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

    public static void main(String[] args) {
        StdOut.println("###############RandomizedQueue Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");
        StdOut.println("Initialize an empty RandomizedQueue");
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        StdOut.println("Constructor & hasNext: " + randomizedQueue);
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

        StdOut.print("enqueuing null item:");
        try {
            randomizedQueue.enqueue(null);
        } catch (IllegalArgumentException e) {
            StdOut.println(e);
        }


        StdOut.println();
        StdOut.println("End of Error Cases");

        StdOut.println();

        StdOut.println("\"Enqueue()\" Test Cases");
        for (int i = 1; i <= 10; i++) {
            randomizedQueue.enqueue(Integer.toString(i));
        }
        StdOut.println("randomizedQueue has only [1,2,3,4,5,6,7,8,9,10]: " + randomizedQueue);
        StdOut.println();

        StdOut.println("\"sample()\" Test Cases");
        for (int i = 1; i <= 10; i++) {
            StdOut.print(randomizedQueue.sample());
            StdOut.print(", ");
        }
        StdOut.println();
        StdOut.println("randomizedQueue has only [1,2,3,4,5,6,7,8,9,10]: " + randomizedQueue);
        StdOut.println();

        StdOut.println("\"dequeue()\" Test Cases");
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

        /*
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
        */
        StdOut.println();

        StdOut.println("##########Online Grader Tests Cases##########"); // All that failed only

        StdOut.println("####Test 1####");
        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        StdOut.println("queue.size(): " + queue.size());
        StdOut.println("will enqueue 18");
        queue.enqueue(18);
        StdOut.println("queue: " + queue);
        StdOut.println("queue.dequeue() is 18? " + (queue.dequeue() == 18));
        StdOut.println("is queue.size() 0? " + (queue.size() == 0));
        StdOut.println("will enqueue 47");
        queue.enqueue(47);
        StdOut.println("####Test 1 End####");

        StdOut.println();

        StdOut.println("####Test 2####");
        queue = new RandomizedQueue<>();
        StdOut.println("Will enqueue 6 & 8");
        queue.enqueue(6);
        queue.enqueue(8);
        StdOut.println("queue: " + queue);
        StdOut.println("Size == 2? " + (queue.size() == 2));
        StdOut.println("queue.isEmpty() is false? " + (!queue.isEmpty()));
        StdOut.println("Will enqueue 24");
        queue.enqueue(24);
        StdOut.println("queue: " + queue);
        StdOut.println("queue.isEmpty() is false? " + (!queue.isEmpty()));
        StdOut.println("Will enqueue 6");
        queue.enqueue(6);
        StdOut.println("queue: " + queue);
        StdOut.println("queue.dequeue(): " + queue.dequeue());
        StdOut.println("queue.size(): " + queue.size());
        StdOut.println("Will enqueue 46");
        queue.enqueue(46);
        StdOut.println("queue: " + queue);
        StdOut.println("####Test 2 End####");

        StdOut.println();

        StdOut.println("####Test 3####");
        queue = new RandomizedQueue<>();
        StdOut.println("I will enqueue 46,44,42,48");
        queue.enqueue(46);
        queue.enqueue(44);
        queue.enqueue(42);
        queue.enqueue(48);
        StdOut.println("queue.dequeue(): " + queue.dequeue());
        StdOut.println("queue: " + queue);
        StdOut.println("I will enqueue 32,25,43,9,26");
        queue.enqueue(32);
        queue.enqueue(25);
        queue.enqueue(43);
        queue.enqueue(9);
        queue.enqueue(26);
        StdOut.println("queue: " + queue);
        StdOut.println("####Test 3 End####");

    }
}