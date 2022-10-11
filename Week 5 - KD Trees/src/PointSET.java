import edu.princeton.cs.algs4.*;

import java.util.Iterator;
import java.util.LinkedList;

public class PointSET {

    // Global Variables
    private final SET<Point2D> rbt;

    // construct an empty set of points
    public PointSET() {
        rbt = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return rbt.isEmpty();
    }

    // number of points in the set
    public int size() {
        return rbt.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("can't insert null");
        rbt.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("can't search null");
        return rbt.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        // TODO: not sure if its implemented correctly
        // Before drawing points
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        // Before splitting lines
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        LinkedList<Point2D> list = new LinkedList<>();

        for (Point2D currentP : rbt) {
            if ((currentP.x() >= rect.xmin() || currentP.x() <= rect.xmax() &&
                    (currentP.y() >= rect.ymin() || currentP.y() <= rect.ymax()))) {
                list.add(currentP);
            }
        }
        return list;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("can't search nearest for null");

        Iterator<Point2D> rbtIterator = rbt.iterator();
        if (rbtIterator.hasNext()) {
            return null;
        }

        // current is root
        Point2D closestPoint = rbtIterator.next();
        double closestDistance = closestPoint.distanceTo(p);
        for (Point2D currentPoint : rbt) {
            if (closestDistance > currentPoint.distanceTo(p)) {
                closestPoint = currentPoint;
                closestDistance = closestPoint.distanceTo(p);
            }
        }
        return closestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        StdOut.println("###############PointSET Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");

        
    }
}