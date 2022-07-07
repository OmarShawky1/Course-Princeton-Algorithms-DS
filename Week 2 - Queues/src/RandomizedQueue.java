import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    //Global Variables
    Object[] queue;
    int N;
    private int first;


    // construct an empty randomized queue
    public RandomizedQueue(Item... items) {

        int itemsLength = items.length;
        queue = new Object[itemsLength];
        first = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N==0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return N+1;
    }

    // add the item
    public void enqueue(Item item) {
        queue[++N] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        Item item = (Item) queue[first];
        queue[first] = null;
        first++;
        return item;
    }
    // return a random item (but do not remove it)
    public Item sample() {
        return (Item) queue[first+ StdRandom.uniform(N) + 1];
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    private void checkListEmpty() {
       if (queue[first] == null) {
          throw new NoSuchElementException("I was called to remove first/last on an empty list");
       }
    }

   private class ListIterator implements Iterator<Item> {

      private int current = first;

      public boolean hasNext() {
         return queue[current] != null;
      }

      public Item next() {
         checkListEmpty();
         Item item = (Item) queue[current];
         current++;
         return item;
      }

      public void remove() {
         throw new UnsupportedOperationException("remove() should not be called");
      }
   }

    public static void main(String[] args) {
       StdOut.println("###############RandomizedQueue Tests###############");

       StdOut.println("##########My Own Test Cases##########");

       StdOut.println("####Test 1####");
    }
}