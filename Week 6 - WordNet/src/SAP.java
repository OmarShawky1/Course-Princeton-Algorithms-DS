import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

public class SAP {

    // Global variables
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {

        // Using Breadth First search
        // enqueue all adjacent -that are not visited- if they are not (node to find)
        // deque and repeat above step

        int counter = 0;
        boolean[] marked = new boolean[digraph.V()];
        Queue<Integer> queue = new Queue<>();

        // enqueue item and iterate until its empty
        queue.enqueue(v);
        while (!queue.isEmpty()) {
            // int current = queue.dequeue(); // Use it in case queue dequeues each time its called
            // iterate over adjacent of current node
            for (int i : digraph.adj(queue.dequeue())) {
                // return current adjacent if it is w
                if (i == w) return counter;

                // else if they are not marked, enqueue it and mark that its enqueued
                if (!marked[i]) {
                    queue.enqueue(i);
                    marked[i] = true;
                }
            }
            counter++; // Increase distance as we finished current node and will move to next
        }
        return -1; // return not found, if code reached this points means it never found w
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return 0; // TODO
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return 0; // TODO
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return 0; // TODO
    }

    // do unit testing of this class
    public static void main(String[] args) {

    }
}