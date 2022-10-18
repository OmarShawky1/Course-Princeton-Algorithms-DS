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

        // TODO: Enhance checking speed as per FAQs advice (checking the splitting line instead of rect

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
        Node champion = root;
        return root != null ? discoverNearest(p, root, champion) : null;
    }

    private Point2D discoverNearest(Point2D p, Node current, Node champion) {
        
        /* Code Logic
            // if rightRec is closer than champion
                // if right not null
                    // if leftRec is closer than champion
                        // if left is not null
                            // if rightRect is closer than leftRec
                                // discover right then left
                            // else, discover left then right
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
        if (distToRight < distanceToChampion) {
            StdOut.println("rightRec is closer than champion"); //TODO: remove line
            // if right not null
            if (current.right != null) {
                StdOut.println("right not null"); //TODO: remove line
                // if leftRec is closer than champion
                if (distToLeftCloser) {
                    StdOut.println("leftRec is closer than champion"); //TODO: remove line
                    // if left is not null
                    if (current.left != null) {
                        StdOut.println("left is not null"); //TODO: remove line
                        // if rightRect is closer than leftRec
                        if (distToRight <= distToLeft) {
                            StdOut.println("rightRect is closer than leftRec"); //TODO: remove line
                            // discover right then left
                            discoverNearest(p, current.right, champion);
                            discoverNearest(p, current.left, champion);
                        } else {
                            // else, discover left then right
                            StdOut.println("leftRec is closer than rightRect"); //TODO: remove line
                            discoverNearest(p, current.left, champion);
                            discoverNearest(p, current.right, champion);
                        }
                    } else {
                        StdOut.println("left is null"); //TODO: remove line
                        discoverNearest(p, current.right, champion); // else, just discover right
                    }
                } else {
                    StdOut.println("leftRec is not closer than champion"); //TODO: remove line
                    discoverNearest(p, current.right, champion); // else, just discover right(*)
                }
            }
            // else, if leftRec is closer than champion (*)
            else if (distToLeftCloser) {
                StdOut.println("leftRec is closer than champion"); //TODO: remove line
                // if left is not null
                if (current.left != null) {
                    StdOut.println("left is not null"); //TODO: remove line
                    discoverNearest(p, current.left, champion); // discover left
                } else {
                    StdOut.println("else, return champion"); //TODO: remove line
                    return champion.point; // else, return champion (*)
                }
            } else {
                StdOut.println("else, return champion"); //TODO: remove line
                return champion.point; // else, return champion (*)
            }
        } // else, if leftRec is closer than champion (*)
        else if (distToLeftCloser) {
            StdOut.println("leftRec is closer than champion"); //TODO: remove line
            // if left is not null
            if (current.left != null) {
                StdOut.println("left is not null"); //TODO: remove line
                discoverNearest(p, current.left, champion); // discover left
            } else {
                StdOut.println("else, return champion"); //TODO: remove line
                return champion.point; // else, return champion
            }
        }
        StdOut.println("else, return champion"); //TODO: remove line
        return champion.point; // else, return champion
    }

    /*private Point2D discoverNearestLeft(Point2D p, Node current, Node champion, double distToLeft, double distanceToChampion) {
        if (distToLeft > distanceToChampion) {
            StdOut.println("leftRec is closer than champion"); //TODO: remove line
            // if left is not null
            if (current.left != null) {
                StdOut.println("left is not null"); //TODO: remove line
                discoverNearest(p, current.left, champion); // discover left
            }
            else {
                StdOut.println("else, return champion"); //TODO: remove line
                return champion.point; // else, return champion
            }
        }
        StdOut.println("else, return champion"); //TODO: remove line
        return champion.point; // else, return champion
    }*/

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
        private final RectHV rightRect, leftRect, canvas;

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

                    rightRect = new RectHV(point.x(), canvas.ymin(), canvas.xmax(), canvas.ymax()); // right-side
                    leftRect = new RectHV(canvas.xmin(), canvas.ymin(), point.x(), canvas.ymax()); // left-side
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
            return isVertical ? Double.compare(point.x(), node.point.x()) :
                    Double.compare(point.y(), node.point.y());
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
                    if (comp > 0) StdDraw.line(point.x(), parent.point.y(), point.x(), canvas.ymax());
                    else StdDraw.line(point.x(), canvas.ymin(), point.x(), parent.point.y());

                } else {
                    StdDraw.setPenColor(StdDraw.BLUE);
                    if (comp > 0) StdDraw.line(parent.point.x(), point.y(), canvas.xmax(), point.y());
                    else StdDraw.line(canvas.xmin(), point.y(), parent.point.x(), point.y());
                }
            } else StdDraw.line(point.x(), 0, point.x(), 1);
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

        /*// Testing Iterator
        Iterator<Node> itr = kdTree.iterator();
        while (itr.hasNext()) {
            StdOut.println(itr.next().point);
        }*/

        // Testing range
        Point2D P66 = new Point2D(0.6, 0.6);
        Point2D P76 = new Point2D(0.7, 0.6);
        RectHV rectHV = new RectHV(0.5, 0.5, 0.6, 0.6);

        kdTree.insert(P66);
        kdTree.insert(P76);

        if (((LinkedList) kdTree.range(rectHV)).size() != 4)
            throw new RuntimeException("Should've gotten exactly 4 points but instead: " + ((LinkedList) kdTree.range(rectHV)).size());
        StdOut.println("Test: " + ++numberOfTests + " passed");

        /* TODO: uncomment it when you test nearest
        // Testing Draw
        kdTree.draw();*/

        // Creating a simpler points to debug errors
        kdTree = new KdTree();
        kdTree.insert(P55); // Origin
        // kdTree.insert(P56);
        kdTree.insert(P44); // Left
        kdTree.insert(P66); // right
        kdTree.insert(P76); // up
        kdTree.insert(P65); // down

        // Testing Draw
        kdTree.draw();


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
    }
}