import edu.princeton.cs.algs4.*;

import java.util.Iterator;
import java.util.LinkedList;

public class KdTree {

    // Global Variables
    // private final SET<Point2D> rbt;
    // TODO

    // construct an empty set of points
    public KdTree() {
        rbt = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        // TODO
        return rbt.isEmpty();
    }

    // number of points in the set
    public int size() {
        // TODO
        return rbt.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("can't insert null");
        // TODO
        rbt.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("can't search null");
        // TODO
        return rbt.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        // TODO

        StdDraw.show();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        LinkedList<Point2D> list = new LinkedList<>();

        // TODO
        return list;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("can't search nearest for null");

        Iterator<Point2D> rbtIterator = rbt.iterator();
        if (!rbtIterator.hasNext()) {
            return null;
        }

        // current is root
        Point2D closestPoint = rbtIterator.next();
        double closestDistance = closestPoint.distanceTo(p);
        // TODO
        
        return closestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        StdOut.println("###############PointSET Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");
        int numberOfTests = 0;

        // Testing insert
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0, 0));
        StdOut.println("Test: " + ++numberOfTests + " passed");

        try {
            kdTree.insert(null);
        } catch (IllegalArgumentException e) {
            StdOut.println(e);
        }
        StdOut.println("Test: " + ++numberOfTests + " passed");


        // Testing Contains
        if (!kdTree.contains(new Point2D(0, 0)))
            throw new RuntimeException("Should've contained origin");
        StdOut.println("Test: " + ++numberOfTests + " passed");

        if (kdTree.contains(new Point2D(0, 1)))
            throw new RuntimeException("Shouldn't contain (0,1)");
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // Testing range
        Point2D origin = new Point2D(0, 0);
        Point2D P01 = new Point2D(0, 0.1);
        Point2D P10 = new Point2D(0.1, 0);
        Point2D P11 = new Point2D(0.1, 0.1);
        Point2D P21 = new Point2D(0.2, 0.1);
        RectHV rectHV = new RectHV(0, 0, 0.1, 0.1);

        kdTree = new KdTree();
        kdTree.insert(origin);
        kdTree.insert(P01);
        kdTree.insert(P10);
        kdTree.insert(P11);
        kdTree.insert(P21);

        if (((LinkedList) kdTree.range(rectHV)).size() != 4)
            throw new RuntimeException("Should've gotten exactly 4 points but instead: " + ((LinkedList) kdTree.range(rectHV)).size());
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // Testing nearest
        if (kdTree.nearest(new Point2D(0.2, 0.2)).compareTo(P21) != 0) throw new RuntimeException(
                "Nearest should've been P21 but instead it is " + kdTree.nearest(P21));
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // Testing Draw
        kdTree.draw();
    }
}
