import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    //Global Variables

    //Array object used for storing the enqueued & dequeued items
    private Object[] queue;
    private int numOfItems; //Number of items in an array
    private int lastIndex; //last non-null item in array queue
    private int first; //first non/null item in array queue


    // construct an empty randomized queue
    public RandomizedQueue(Item... items) {
        int itemsLength = items.length;
        queue = new Object[itemsLength];
        numOfItems = 0;
        lastIndex = 0;
        first = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return numOfItems == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return numOfItems + 1;
    }

    // add the item
    public void enqueue(Item item) {
        itemValid(item);
        queue[++lastIndex] = item;
        numOfItems++;
        adjustArraySize();
    }

    // remove and return a random item
    public Item dequeue() {
        checkArrayEmpty();
        Item item;
        int randomNumber;
        do {
            randomNumber = first + StdRandom.uniform(numOfItems) + 1;
            item = (Item) queue[randomNumber];
        } while (item == null);
        queue[randomNumber] = null;
        numOfItems--;
        adjustArraySize();
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        checkArrayEmpty();
        Item item;
        int randomNumber;
        do {
            randomNumber = first + StdRandom.uniform(numOfItems) + 1;
            item = (Item) queue[randomNumber];
        } while (item == null);
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

      private int current = first;

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

    private void checkArrayEmpty() {
        if (queue[first] == null) {
            throw new NoSuchElementException("I was called to remove first/last on an empty list");
        }
    }

    private void adjustArraySize() {
       if (numOfItems == queue.length) {
           cloneArray(numOfItems * 2);

       } else if (numOfItems == queue.length / 4) {
           cloneArray(numOfItems / 2);
       }
   }

   private void cloneArray(int newSize) {
       Object[] newArr = new Object[newSize];
       int pointer = 0;
       for (Object object : queue) {
           if (object != null) newArr[++pointer] = object;
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

    public static void main(String[] args) {
       StdOut.println("###############RandomizedQueue Tests###############");

       StdOut.println("##########My Own Test Cases##########");

       StdOut.println("####Test 1####");
       //TODO!!: main() method must call directly every public constructor and method to verify that they work as prescribed
    }
}