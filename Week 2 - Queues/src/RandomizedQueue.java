import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    //Global Variables
    Deque<Item> deque;

    // construct an empty randomized queue
    public RandomizedQueue() {
        deque = new Deque<>();
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    // return the number of items on the randomized queue
    public int size() {
        return deque.size();
    }

    // add the item
    public void enqueue(Item item) {
        deque.addLast(item);
    }

    // remove and return a random item
    public Item dequeue() {
        //TODO
    }

    // return a random item (but do not remove it)
    public Item sample() {

        int randomIndex = StdRandom.uniform(deque.size()) +1;
        Iterator<Item> iterator = deque.iterator();
        Item randomItem = null;
        for (int i = 0; i< randomIndex; i++) {
            randomItem = iterator.next();
        }
        return randomItem;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {

    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}