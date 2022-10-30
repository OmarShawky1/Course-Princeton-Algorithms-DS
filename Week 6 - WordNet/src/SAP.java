import edu.princeton.cs.algs4.*;

public class SAP {

    // Global variables
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        this.digraph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        return (new SAPAncestor(v, w, digraph)).distance;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        return (new SAPAncestor(v, w, digraph)).commonAncestor; //TODO: Later, enhance it by storing ancestor to avoid recomputing in length()
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        return 0; // TODO
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        return 0; // TODO
    }

    // Signature: (int * int * Digraph) --> (int * int) (where int is "vertex")
    // provides distance between w & v and the nearest vertex between them
    private static class SAPAncestor {
        public int distance = -1;
        public int commonAncestor = -1;

        public SAPAncestor (int v, int w, Digraph digraph) {
            BreadthFirstDirectedPaths vBFS = new BreadthFirstDirectedPaths(digraph, v);
            BreadthFirstDirectedPaths wBFS = new BreadthFirstDirectedPaths(digraph, w);
            for (int i = 0; i < digraph.V(); i++) {
                // Using Breadth First search

                // if i has path to both v & w
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