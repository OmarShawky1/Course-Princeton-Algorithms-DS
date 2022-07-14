import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    //Global Variables
    private Object[] queue;
    private int N;
    private int lastIndex;
    private int first;


    // construct an empty randomized queue
    public RandomizedQueue(Item... items) {

        int itemsLength = items.length;
        queue = new Object[itemsLength];
        N = 0;
        lastIndex = 0;
        first = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N + 1;
    }

    // add the item
    public void enqueue(Item item) {
        itemValid(item);
        queue[++lastIndex] = item;
        N++;
        adjustArraySize();
    }

    // remove and return a random item
    public Item dequeue() {
        checkArrayEmpty();
        Item item;
        int randomNumber;
        do {
            randomNumber = first + StdRandom.uniform(N) + 1;
            item = (Item) queue[randomNumber];
        } while (item == null);
        queue[randomNumber] = null;
        N--;
        adjustArraySize();
        return item;
    }
    // return a random item (but do not remove it)
    public Item sample() {
        checkArrayEmpty();
        Item item;
        int randomNumber;
        do {
            randomNumber = first + StdRandom.uniform(N) + 1;
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

    private void adjustArraySize(){
       if (N == queue.length) {
           cloneArray(N*2);

       } else if (N == queue.length/4) {
           cloneArray(N/2);
       }
   }

   private void cloneArray(int newSize){
       Object[] newArr = new Object[newSize];
       int pointer = 0;
       for (Object object: queue){
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