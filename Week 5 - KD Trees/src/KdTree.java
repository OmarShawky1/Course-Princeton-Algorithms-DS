import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

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

        // make point root if list is empty
        if (root == null) {
            root = temp;
            size++;
            return;
        }

        // otherwise, check where to add point by comparing with nodes until reaching null link
        Node current = root;
        // Comparing temp to current; Can't compare "temp" to "current" directly because "isVertical" influences the
        // comparison, and it is fake in temp, so we invert sign
        int comp = current.compareTo(temp) * -1;
        while (true) {
            if (comp > 0) {
                if (current.right != null) {
                    current = current.right;
                    comp = current.compareTo(temp) * -1;
                } else {
                    current.right = new Node(current, p, !current.isVertical);
                    size++;
                    break;
                }
            } else if (comp < 0) {
                if (current.left != null) {
                    current = current.left;
                    comp = current.compareTo(temp) * -1;
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
            if (current.point.compareTo(p) == 0) return true;
            if ((current.compareTo(tempNode) * -1) >= 0) current = current.right;
            else current = current.left;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        Iterator<Node> itr = iterator();
        while (itr.hasNext()) itr.next().draw();
        StdDraw.show();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("can't range for null");
        LinkedList<Point2D> list = new LinkedList<>();
        addPointsInRange(rect, list, root);
        return list;
    }

    private void addPointsInRange(RectHV rect, LinkedList<Point2D> list, Node current) {
        if (current == null) return;

        // check if current lies within rect
        boolean xInRange = current.point.x() >= rect.xmin() && current.point.x() <= rect.xmax();
        boolean yInRange = current.point.y() >= rect.ymin() && current.point.y() <= rect.ymax();
        if (xInRange && yInRange) list.add(current.point);

        // check if current.right lies within rect, if so discover it
        if (current.rightRect.intersects(rect)) addPointsInRange(rect, list, current.right);

        // check if current.left lies within rect, if so discover it
        if (current.leftRect.intersects(rect)) addPointsInRange(rect, list, current.left);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("can't search nearest for null");
        Node champion = root;
        return root != null ? discoverNearest(p, root, champion).point : null;
    }

    private Node discoverNearest(Point2D p, Node current, Node champion) {
        /* Code Logic
            // if rightRec is closer than champion
                // if right not null
                    // if leftRec is closer than champion
                        // if left is not null
                            // if rightRect is closer than leftRec
                                // discover right
                                // if left can be closer than right
                                    // discover left
                            // else, discover left then right
                                // if right can be closer than left
                                    // discover right
                        // else, just discover right
                    // else, just discover right  (*)
                // else, if leftRec is closer than champion (*)
                    // if left is not null
                        // discover left
                    // else, return champion (*)
                // else, return champion  (*)
            // else, if leftRec is closer than champion  (*)
                // if left is not null
                    // discover left
                // else, return champion
            // else, return champion
        */

        // if current node is closer than champion -so far-; assign current to champion
        double distanceToChampion = champion.point.distanceSquaredTo(p);
        if (current.point.distanceSquaredTo(p) < champion.point.distanceSquaredTo(p)) {
            champion = current;
            distanceToChampion = champion.point.distanceSquaredTo(p);
        }
        double distToRight = current.rightRect.distanceSquaredTo(p);
        double distToLeft = current.leftRect.distanceSquaredTo(p);
        boolean distToLeftCloser = distToLeft < distanceToChampion;
        // if rightRec is closer than champion
        // if right not null
        if (distToRight < distanceToChampion && current.right != null) {
            // if leftRec is closer than champion
            // if left is not null
            if (distToLeftCloser && current.left != null) {
                    // if rightRect is closer than leftRec
                    if (distToRight <= distToLeft) {
                        // discover right then left
                        champion = discoverNearest(p, current.right, champion);
                        distanceToChampion = champion.point.distanceSquaredTo(p);
                        if (distanceToChampion > distToLeft) champion = discoverNearest(p, current.left, champion);
                    } else {
                        // else, discover left then right
                        champion = discoverNearest(p, current.left, champion);
                        distanceToChampion = champion.point.distanceSquaredTo(p);
                        if (distanceToChampion > distToRight) champion = discoverNearest(p, current.right, champion);
                    }
            } else champion = discoverNearest(p, current.right, champion); // else, just discover right
        }
        // else, if leftRec is closer than champion
        // if left is not null
        // discover left
        else if (distToLeftCloser && current.left != null) champion = discoverNearest(p, current.left, champion);
        return champion; // else, return champion
    }

    private Iterator<Node> iterator() {
        LinkedList<Node> list = new LinkedList<>();
        addToIteratorList(list, root);
        return list.iterator();
    }

    private static void addToIteratorList(LinkedList<Node> list, Node n) {
        if (n == null) return;
        list.add(n);
        addToIteratorList(list, n.left);
        addToIteratorList(list, n.right);
    }

    private static class Node implements Comparable<Node> {
        private static final boolean VERTICAL = true;
        private final RectHV rightRect, leftRect, canvas;
        private final boolean isVertical;
        private final Point2D point;
        private final Node parent;
        private Node right, left;

        public Node(Node parent, Point2D point, boolean isVertical) {
            this.parent = parent;
            this.point = point;
            this.isVertical = isVertical;

            // instantiating rectangles that will be used to find intersection in range()
            if (parent == null) {
                canvas = new RectHV(0, 0, 1, 1);
                rightRect = new RectHV(point.x(), canvas.ymin(), canvas.xmax(), canvas.ymax());
                leftRect = new RectHV(canvas.xmin(), canvas.ymin(), point.x(), canvas.ymax());
            } else {
                // decide if the node is left or right of parent to calculate canvas size
                // compare using the parent (as isVertical influences comparison) and inverse sign
                int comp = parent.compareTo(this) * -1;

                // Local variables just to shorten the line length below
                double xmin = parent.canvas.xmin();
                double xmax = parent.canvas.xmax();
                double ymin = parent.canvas.ymin();
                double ymax = parent.canvas.ymax();

                // if this node splits canvas vertically, means parent splits horizontally
                if (isVertical) {
                    // if this node is the up-side
                    if (comp >= 0) canvas = new RectHV(xmin, parent.point.y(), xmax, ymax);
                    // if this node is the down-side
                    else canvas = new RectHV(xmin, ymin, xmax, parent.point.y());

                    rightRect = new RectHV(point.x(), canvas.ymin(), canvas.xmax(), canvas.ymax());
                    leftRect = new RectHV(canvas.xmin(), canvas.ymin(), point.x(), canvas.ymax());
                } else { // else it splits canvas horizontally, means parent splits vertically
                    // If this node is the right-side
                    if (comp >= 0) canvas = new RectHV(parent.point.x(), ymin, xmax, ymax);
                    // if this node is the left-side
                    else canvas = new RectHV(xmin, ymin, parent.point.x(), ymax);

                    RectHV upRect = new RectHV(canvas.xmin(), point.y(), canvas.xmax(), canvas.ymax());
                    RectHV downRect = new RectHV(canvas.xmin(), canvas.ymin(), canvas.xmax(), point.y());
                    rightRect = upRect;
                    leftRect = downRect;
                }
            }
        }

        @Override
        public int compareTo(Node node) {
            return isVertical ? Double.compare(point.x(), node.point.x()) : Double.compare(point.y(), node.point.y());
        }

        public void draw() {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            point.draw();

            StdDraw.setPenRadius();
            if (parent != null) {
                int comp = parent.compareTo(this) * -1;
                if (isVertical) {
                    StdDraw.setPenColor(StdDraw.RED);
                    // if point is up, line is from y of (horizontal parent) till parents' ymax; x is constant
                    if (comp >= 0) StdDraw.line(point.x(), parent.point.y(), point.x(), canvas.ymax());
                    // if point is down, line is from parents' ymin till y of (horizontal parent); x is constant
                    else StdDraw.line(point.x(), canvas.ymin(), point.x(), parent.point.y());
                } else {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    // if point is right, line from x of (vertical parent) till parents' xmax; y is constant
                    if (comp >= 0) StdDraw.line(parent.point.x(), point.y(), canvas.xmax(), point.y());
                    // if point is right, line from parents' xmin till x of (vertical parent); y is constant
                    else StdDraw.line(canvas.xmin(), point.y(), parent.point.x(), point.y());
                }
            } else StdDraw.line(point.x(), 0, point.x(), 1); // parent's line has main canvas size
        }
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        /*
        StdOut.println("###############KdTree Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");
        int numberOfTests = 0;

        // Testing insert
        // inserting to empty tree
        KdTree kdTree = new KdTree();
        Point2D P55 = new Point2D(0.5, 0.5);
        kdTree.insert(P55);
        if (kdTree.root.point.compareTo(P55) != 0) throw new RuntimeException("root should've " +
                "been: " + P55 + " instead it is " + kdTree.root.point);
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
        kdTree.insert(P55);
        if (kdTree.size() != 1) throw new RuntimeException("Size should've been 1 but instead it " +
                "is: " + kdTree.size());
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // inserting new point above root
        Point2D P56 = new Point2D(0.5, 0.6);
        kdTree.insert(P56);
        if (kdTree.root.right.point.compareTo(P56) != 0) throw new RuntimeException("root.right " +
                "should've been " + P56 + " but instead it is " + kdTree.root.right.point);
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // inserting new point to the left
        Point2D P44 = new Point2D(0.4, 0.4);
        kdTree.insert(P44);
        if (kdTree.root.left.point.compareTo(P44) != 0) throw new RuntimeException("root.left " +
                "should've been " + P44 + " but instead it is: " + kdTree.root.left.point);
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // inserting new point to the left
        Point2D P65 = new Point2D(0.6, 0.5);
        kdTree.insert(P65);
        if (kdTree.root.right.left.point.compareTo(P65) != 0)
            throw new RuntimeException("root.right.right " +
                    "should've been " + P65 + " but instead it is: " + kdTree.root.right.left.point);
        StdOut.println("Test: " + ++numberOfTests + " passed");


        // Testing Contains
        // checking if root is available
        if (!kdTree.contains(P55)) throw new RuntimeException("Should've contained origin");
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // checking for a non-existing point
        if (kdTree.contains(new Point2D(0, 1)))
            throw new RuntimeException("Shouldn't contained (0,1)");
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // checking for point on left
        if (!kdTree.contains(P44)) throw new RuntimeException("Should've contained " + P44);
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // inserting new point to the right
        if (!kdTree.contains(P65)) throw new RuntimeException("Should've contained " + P65);
        StdOut.println("Test: " + ++numberOfTests + " passed");

        if (!kdTree.contains(P56)) throw new RuntimeException("Should've contained " + P56);
        StdOut.println("Test: " + ++numberOfTests + " passed");

         Testing Iterator
        Iterator<Node> itr = kdTree.iterator();
        while (itr.hasNext()) {
            StdOut.println(itr.next().point);
        }

        // Testing range
        Point2D P66 = new Point2D(0.6, 0.6);
        Point2D P76 = new Point2D(0.7, 0.6);
        RectHV rectHV = new RectHV(0.5, 0.5, 0.6, 0.6);

        kdTree.insert(P66);
        kdTree.insert(P76);

        if (((LinkedList) kdTree.range(rectHV)).size() != 4)
            throw new RuntimeException("Should've gotten exactly 4 points but instead: " + ((LinkedList) kdTree.range(rectHV)).size());
        StdOut.println("Test: " + ++numberOfTests + " passed");

        // Testing Draw
        kdTree.draw();

        // Creating a simpler points to debug errors
        kdTree = new KdTree();
        kdTree.insert(P55); // Origin
        kdTree.insert(P44); // Left
        kdTree.insert(P66); // right
        kdTree.insert(P76); // up
        kdTree.insert(P65); // down

        // Testing nearest
        if (kdTree.nearest(new Point2D(0.7, 0.7)).compareTo(P76) != 0) throw new RuntimeException(
                "Nearest should've been P76 but instead it is " + kdTree.nearest(P76));
        StdOut.println("Test: " + ++numberOfTests + " passed");

        if (kdTree.nearest(new Point2D(0.8, 0.6)).compareTo(P76) != 0) throw new RuntimeException(
                "Nearest should've been P76 but instead it is " + kdTree.nearest(P76));
        StdOut.println("Test: " + ++numberOfTests + " passed");

        if (kdTree.nearest(new Point2D(0.8, 0.8)).compareTo(P76) != 0) throw new RuntimeException(
                "Nearest should've been P76 but instead it is " + kdTree.nearest(P76));
        StdOut.println("Test: " + ++numberOfTests + " passed");
        */
    }
}