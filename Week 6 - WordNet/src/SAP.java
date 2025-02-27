import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {

    // Global variables
    private final Digraph digraph;
    // If you want to speed things up (according to the FAQs), use a Hash-table and store every Closest Ancestor
    // You need to implement hash which is tedious job for little reward in case of Iterable

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.digraph = new Digraph(G); // Make it immutable instead of passing by reference
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return (new ClosestAncestor(v, w, digraph)).distance;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return (new ClosestAncestor(v, w, digraph)).commonAncestor;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return (new ClosestAncestor(v, w, digraph)).distance;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return (new ClosestAncestor(v, w, digraph)).commonAncestor;
    }

    private static class ClosestAncestor {
        public int distance = -1;
        public int commonAncestor = -1;

        // Signature: (int * int * Digraph) --> (int * int) (where int is "vertex")
        // provides distance between w & v and the nearest vertex between them
        public ClosestAncestor(int v, int w, Digraph di) {
            if (!inRange(v, di.V()) && !inRange(w, di.V())) throw new IllegalArgumentException("you inputted null!");
            BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(di, v);
            BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(di, w);
            findClosest(vBFS, wBFS, di);
        }

        // Signature: (list of int * list of int * Digraph) --> (int * int) (where int is "vertex")
        // provides distance between w & v and the nearest vertex between them
        public ClosestAncestor(Iterable<Integer> v, Iterable<Integer> w, Digraph di) {
            if (v == null || w == null) throw new IllegalArgumentException("you inputted wrong range");

            // Check non-zero list size
            if (v.iterator().hasNext() && w.iterator().hasNext()) {
                BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(di, v);
                BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(di, w);
                findClosest(vBFS, wBFS, di);
            }
        }

        private void findClosest(BreadthFirstDirectedPaths vBFS, BreadthFirstDirectedPaths wBFS, Digraph di) {
            for (int i = 0; i < di.V(); i++) {
                // Using Breadth First search

                // if "i" has path to both v & w
                if (!vBFS.hasPathTo(i) || !wBFS.hasPathTo(i)) continue; // skip loop if they don't
                // and distance from i-->v + i-->w is less than old distance
                int currentDist = vBFS.distTo(i) + wBFS.distTo(i);
                if (currentDist >= distance && distance != -1) continue; // skip loop if its farther
                // save current distance and ancestor to be the nearest vertex so far
                distance = currentDist;
                commonAncestor = i;

                /*
                // Same code logic as above yet has so much nesting
                // if i has path to both v & w
                if (vBFS.hasPathTo(i) && wBFS.hasPathTo(i)) {
                    // and distance from i-->v + i-->w is less than old distance
                    int currentDist = vBFS.distTo(i) + wBFS.distTo(i);
                    if (currentDist < distance || distance == -1){
                        // save current distance and ancestor to be the nearest vertex so far
                        distance = currentDist;
                        commonAncestor = i;
                    }
                */
            }
        }

        private boolean inRange(int i, int graphLength) {
            return 0 <= i && i < graphLength;
        }
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}