import edu.princeton.cs.algs4.*;

import java.util.Iterator;
import java.util.LinkedList;

public class KdTree {

    // Global Variables
    private Node root;
    private int size;

    // construct an empty set of points
    public KdTree() {
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("can't insert null");

        Node temp = new Node(null, p, Node.VERTICAL);

        if (root == null) {
            root = temp;
            size++;
            return;
        }

        Node current = root;
        int comp = current.compareTo(temp);
        while (true) {
            if (comp > 0) {
                if (current.right != null) {
                    current = current.right;
                } else {
                    current.right = new Node(current, p, !current.isVertical);
                    size++;
                    break;
                }
            } else if (comp < 0) {
                if (current.left != null) {
                    current = current.left;
                } else {
                    current.left = new Node(current, p, !current.isVertical);
                    size++;
                    break;
                }
            } else {
                // check if it is the same point
                if (current.point.compareTo(p) == 0)
                    break; // new point is already stored

                // Else pass it to comparison bigger than
                comp = 1;
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("can't search null");
        // TODO
        // return twoDTree.contains(new Node(null, p, Node.VERTICAL)); //TODO: this is wrong
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        // TODO: draw the horizontal and vertical lines

        //TODO: draw rectangles

        StdDraw.show();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        LinkedList<Point2D> list = new LinkedList<>();
        // TODO: implement it

        return list;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("can't search nearest for null");

        // TODO
        /*Iterator<Node> twoDIterator = twoDTree.iterator();
        if (!twoDIterator.hasNext()) {
            return null;
        }

        // current is root
        Node closestNode = twoDIterator.next();
        Point2D closestPoint = closestNode.point;
        double closestDistance = closestPoint.distanceTo(p);
        // TODO: Implement

        return closestPoint;*/
        return p;
    }

    private class Node implements Comparable<Node> {
        //TODO revise on everything here
        private Node parent, right, left;
        private boolean isVertical = false;
        private Point2D point;
        private static final boolean VERTICAL = true;
        private static final boolean HORIZONTAL = false;

        public Node(Node parent, Point2D point, boolean isVertical) {
            this.parent = parent;
            this.point = point;
            this.isVertical = isVertical;
        }

        @Override
        public int compareTo(Node node) {
            return isVertical ? Double.compare(point.x(), node.point.x()) :
                    Double.compare(point.y(), node.point.y());
        }

        public void draw() {
            // TODO
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        StdOut.println("###############KdTree Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");
        int numberOfTests = 0;

        // Testing insert

        // inserting to empty tree
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0, 0));
        if (kdTree.size() != 1) throw new RuntimeException("Size should've been 1 but instead it " +
                "is: " + kdTree.size());
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // inserting null
        try {
            kdTree.insert(null);
        } catch (IllegalArgumentException e) {
            StdOut.println(e);
        }
        if (kdTree.size() != 1) throw new RuntimeException("Size should've been 1 but instead it " +
                "is: " + kdTree.size());
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // inserting same point twice
        kdTree.insert(new Point2D(0, 0));
        if (kdTree.size() != 1) throw new RuntimeException("Size should've been 1 but instead it " +
                "is: " + kdTree.size());
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // inserting new point above root
        kdTree.insert(new Point2D(0, 0.1));
        if (kdTree.size() != 2) throw new RuntimeException("Size should've been 2 but instead it " +
                "is: " + kdTree.size());
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // inserting new point to the left
        kdTree.insert(new Point2D(-0.1, 0));
        if (kdTree.size() != 3) throw new RuntimeException("Size should've been 3 but instead it " +
                "is: " + kdTree.size());
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // inserting new point to the right
        kdTree.insert(new Point2D(0.1, 0));
        if (kdTree.size() != 4) throw new RuntimeException("Size should've been 4 but instead it " +
                "is: " + kdTree.size());
        StdOut.println("Test: " + ++numberOfTests + " passed");

        /*
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
        kdTree.draw();*/
    }
}
