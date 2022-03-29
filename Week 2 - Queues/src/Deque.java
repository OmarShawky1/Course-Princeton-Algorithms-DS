import java.util.Iterator;
import java.util.LinkedList;

public class Deque<Item> implements Iterable<Item> {

    //Global Variables
    private LinkedList<Item> list;

    // construct an empty deque
    public Deque() {
    }
    // is the deque empty?
    public boolean isEmpty() {
        return list.isEmpty();
    }
    // return the number of items on the deque
    public int size() {
        return list.size();
    }
    // add the item to the front
    public void addFirst(Item item) {
        list.addFirst(item);
    }
    // add the item to the back
    public void addLast(Item item) {
        list.addLast(item);
    }
    // remove and return the item from the front
    public Item removeFirst() {
        return list.removeFirst();
    }
    // remove and return the item from the back
    public Item removeLast() {
        return list.removeLast();
    }
    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return list.iterator();
    }
    // unit testing (required)
    public static void main(String[] args) {

    }
}