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
        int comp = temp.compareTo(current);
        while (true) {
            // StdOut.println("current: " + current.point); //TODO remove line
            // StdOut.println("I will try to insert: " + p + " and comparison is: " + comp); //TODO remove line
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
                if (current.point.compareTo(p) == 0) break; // new point is already stored

                comp = 1; // Else pass it to comparison greater than
            }
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("can't search null");

        Node current = root;
        Node tempNode = new Node(null, p, Node.VERTICAL); // direction doesn't matter
        while (current != null) {
            // StdOut.println("current.point is: " + current.point + " while p: " + p); //TODO remove line
            // StdOut.println("current.isVertical: " + current.isVertical); //TODO remove line
            if (current.point.compareTo(p) == 0) return true;
            if (tempNode.compareTo(current) >= 0) current = current.right;
            else current = current.left;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        Iterator<Node> itr = iterator();
        while (itr.hasNext()) {
            itr.next().draw();
        }
        StdDraw.show();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        LinkedList<Point2D> list = new LinkedList<>();
        addPointsInRange(rect, list, root);
        return list;
    }

    private void addPointsInRange(RectHV rect, LinkedList<Point2D> list, Node current) {
        if (current == null) return;

        // check if current lies within rect
        if (current.point.x() >= rect.xmin() && current.point.x() <= rect.xmax()
                && current.point.y() >= rect.ymin() && current.point.y() <= rect.ymax()) {
            list.add(current.point);
        }

        // check if current.right lies within rect, if so discover it
        if (current.rightRect.intersects(rect)) {
            addPointsInRange(rect, list, current.right);
        }

        // check if current.left lies within rect, if so discover it
        if (current.leftRect.intersects(rect)) {
            addPointsInRange(rect, list, current.left);
        }
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

    private Iterator<Node> iterator() {
        LinkedList<Node> l = new LinkedList<>();
        addToIteratorList(l, root);
        return l.iterator();
    }

    private static void addToIteratorList(LinkedList<Node> l, Node n) {
        if (n == null) return;
        l.add(n);
        addToIteratorList(l, n.left);
        addToIteratorList(l, n.right);
    }

    private static class Node implements Comparable<Node> {
        private final Node parent;
        private Node right;
        private Node left;
        private final boolean isVertical;
        private final Point2D point;
        private static final boolean VERTICAL = true;
        private final RectHV rightRect, leftRect;

        public Node(Node parent, Point2D point, boolean isVertical) {
            this.parent = parent;
            this.point = point;
            this.isVertical = isVertical;

            // instantiating rectangles that will be used to find intersection in range()

            // if the point cuts canvas vertically, create right & left rectangles
            if (isVertical) {
                boolean isParentNull = parent == null;
                double yMinLimit = !isParentNull ? parent.point.y() : 0;
                double yMaxLimit = !isParentNull ? parent.point.y() : 1;
                rightRect = new RectHV(point.x(), yMinLimit, point.x(), 1);
                leftRect = new RectHV(point.x(), 0, point.x(), yMaxLimit);
            } else { // otherwise, point cuts canvas horizontally, create up (right) & down (left) r
                rightRect = new RectHV(parent.point.x(), point.y(), 1, point.y());
                leftRect = new RectHV(0, point.y(), parent.point.x(), point.y());
            }
        }

        @Override
        public int compareTo(Node node) {
            return isVertical ? Double.compare(point.x(), node.point.x()) :
                    Double.compare(point.y(), node.point.y());
        }

        public void draw() {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            point.draw();

            StdDraw.setPenRadius();
            if (isVertical) {
                StdDraw.setPenColor(StdDraw.RED);
                if (parent != null) {
                    int comp = point.compareTo(parent.point);
                    if (comp > 0) StdDraw.line(point.x(), parent.point.y(), point.x(), 1);
                    else StdDraw.line(point.x(), -1, point.x(), parent.point.y());
                } else StdDraw.line(point.x(), -1, point.x(), 1);

            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                int comp = point.compareTo(parent.point);
                if (comp > 0) StdDraw.line(parent.point.x(), point.y(), 1, point.y());
                else StdDraw.line(-1, point.y(), parent.point.x(), point.y());
            }
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
        Point2D P00 = new Point2D(0, 0);
        kdTree.insert(P00);
        if (kdTree.root.point.compareTo(P00) != 0) throw new RuntimeException("root should've " +
                "been: " + P00 + " instead it is " + kdTree.root.point);
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
        Point2D P01 = new Point2D(0, 0.1);
        kdTree.insert(P01);
        if (kdTree.root.right.point.compareTo(P01) != 0) throw new RuntimeException("root.right " +
                "should've been " + P01 + " but instead it is " + kdTree.root.right.point);
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // inserting new point to the left
        Point2D Pn10 = new Point2D(-0.1, 0);
        kdTree.insert(Pn10);
        if (kdTree.root.left.point.compareTo(Pn10) != 0) throw new RuntimeException("root.left " +
                "should've been " + Pn10 + " but instead it is: " + kdTree.root.left.point);
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // inserting new point to the right
        Point2D P10 = new Point2D(0.1, 0);
        kdTree.insert(P10);
        if (kdTree.root.right.right.point.compareTo(P10) != 0)
            throw new RuntimeException("root.right.right " +
                    "should've been " + P10 + " but instead it is: " + kdTree.root.right.right.point);
        StdOut.println("Test: " + ++numberOfTests + " passed");


        // Testing Contains
        // checking if root is available
        if (!kdTree.contains(P00)) throw new RuntimeException("Should've contained origin");
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // checking for a non-existing point
        if (kdTree.contains(new Point2D(0, 1)))
            throw new RuntimeException("Shouldn't contained (0,1)");
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // checking for point on left
        if (!kdTree.contains(Pn10)) throw new RuntimeException("Should've contained " + Pn10);
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // inserting new point to the right
        if (!kdTree.contains(P10)) throw new RuntimeException("Should've contained " + P10);
        StdOut.println("Test: " + ++numberOfTests + " passed");

        if (!kdTree.contains(P01)) throw new RuntimeException("Should've contained " + P01);
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // Testing Iterator
        Iterator<Node> itr = kdTree.iterator();
        while (itr.hasNext()) {
            StdOut.println(itr.next().point);
        }


        // Testing range
        Point2D P11 = new Point2D(0.1, 0.1);
        Point2D P21 = new Point2D(0.2, 0.1);
        RectHV rectHV = new RectHV(0, 0, 0.1, 0.1);

        kdTree.insert(P11);
        kdTree.insert(P21);

        if (((LinkedList) kdTree.range(rectHV)).size() != 4)
            throw new RuntimeException("Should've gotten exactly 4 points but instead: " + ((LinkedList) kdTree.range(rectHV)).size());
        StdOut.println("Test: " + ++numberOfTests + " passed");

        /*
        // Testing nearest
        if (kdTree.nearest(new Point2D(0.2, 0.2)).compareTo(P21) != 0) throw new RuntimeException(
                "Nearest should've been P21 but instead it is " + kdTree.nearest(P21));
        StdOut.println("Test: " + ++numberOfTests + " passed");
        */
        // Testing Draw
        kdTree.draw();
    }
}
