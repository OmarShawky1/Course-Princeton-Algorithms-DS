/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        int deltaY = that.y - y;
        int deltaX = that.x - x;

        if (deltaX == 0) {
            if (deltaY == 0) {
                return Double.NEGATIVE_INFINITY;
            }
            return Double.POSITIVE_INFINITY;
        }

        return (double) deltaY / deltaX;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        // Less?
        if (y < that.y || (y == that.y && x < that.x)) {
            return -1;
        }

        // Equal?
        if (y == that.y && x == that.x) {
            return 0;
        }

        return 1; // Otherwise, it's more.
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new BySlope();
    }

    private class BySlope implements Comparator<Point> {
        public int compare(Point p0, Point p1) {
            // Never use reguar <, > or == because of the -infty, +infty, NaN...
            return Double.compare(Point.this.slopeTo(p0), Point.this.slopeTo(p1));
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provided for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {

        StdOut.println("###############RandomizedQueue Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");
        // Initializing 5 points: (0,0), (0,1), (1,0), (1,1), (2,2)
        Point p00 = new Point(0, 0);
        Point p01 = new Point(0, 1);
        Point p10 = new Point(1, 0);
        Point p11 = new Point(1, 1);
        Point p22 = new Point(2, 2);

        // Testing CompareTo & SlopeTo
        // Same point
        assert (p00.compareTo(p00) == 0) :
                "p00 = itself but p00.compareTo(p00): " + p00.compareTo(p00);

        assert (p00.slopeTo(p00) == Double.NEGATIVE_INFINITY) :
                "ΔP = -\\infty but p00.slopeTo(p00): " + p00.slopeTo(p00);

        // Horizontal points/lines (00 --> 10) && (01 --> 11)
        assert (p00.compareTo(p10) == -1) :
                "p00 < p10 but p00.compareTo(p10): " + p00.compareTo(p10);

        assert (p01.compareTo(p11) == -1) :
                "p01 = p11 but p01.compareTo(p11): " + p01.compareTo(p11);

        assert (p00.slopeTo(p10) == 0) :
                "ΔP = 0 but p00.slopeTo(p10): " + p00.slopeTo(p10);

        assert (p01.slopeTo(p11) == 0) :
                "ΔP = 0 but p01.slopeTo(p11): " + p01.slopeTo(p11);


        // Vertical points/lines (00 --> 01) && (10 --> 11)
        assert (p00.compareTo(p01) == -1) :
                "p00 = p01 but p00.compareTo(p01): " + p00.compareTo(p01);

        assert (p10.compareTo(p11) == -1) :
                "p10 = p11 but p01.compareTo(p11): " + p10.compareTo(p11);

        assert (p00.slopeTo(p01) == Double.POSITIVE_INFINITY) :
                "ΔP = 0 but p00.slopeTo(p01): " + p00.slopeTo(p01);

        assert (p10.slopeTo(p11) == Double.POSITIVE_INFINITY) :
                "ΔP = 0 but p10.slopeTo(p11): " + p10.slopeTo(p11);


        // Increasing slope (00 --> 11) && (00 --> 22) && (10 --> 22) && (01 --> 22) && (11 --> 22)
        assert (p00.compareTo(p11) == -1) :
                "p00 < p11 but p00.compareTo(p11): " + p00.compareTo(p11);

        assert (p00.compareTo(p22) == -1) :
                "p00 < p22 but p00.compareTo(p22): " + p00.compareTo(p22);

        assert (p10.compareTo(p22) == -1) :
                "p10 < p22 but p10.compareTo(p22): " + p10.compareTo(p22);

        assert (p01.compareTo(p22) == -1) :
                "p01 < p22 but p01.compareTo(p22): " + p01.compareTo(p22);

        assert (p11.compareTo(p22) == -1) :
                "p11 < p22 but p11.compareTo(p22): " + p11.compareTo(p22);

        assert (p00.slopeTo(p11) == 1) :
                "ΔP = 1 but p00.slopeTo(p11): " + p00.slopeTo(p11);

        assert (p00.slopeTo(p22) == 1) :
                "ΔP = 1 but p00.slopeTo(p22): " + p00.slopeTo(p22);

        assert (p10.slopeTo(p22) == 2) :
                "ΔP = 2 but p10.slopeTo(p22): " + p10.slopeTo(p22);

        assert (p01.slopeTo(p22) == 0.5) :
                "ΔP = 0.5 but p01.slopeTo(p22): " + p01.slopeTo(p22);

        assert (p11.slopeTo(p22) == 1) :
                "ΔP = 1 but p11.slopeTo(p22): " + p11.slopeTo(p22);

        // Decreasing slope (01 --> 10)
        assert (p01.compareTo(p10) == 1) :
                "p01 = p10 but p01.compareTo(p10): " + p01.compareTo(p10);

        assert (p01.slopeTo(p10) == -1) :
                "ΔP = 1 but p01.slopeTo(p10): " + p01.slopeTo(p10);

        // Testing Comparator Class (SlopeOrder)
        // Untested; To be tested in any sorting class instead of here.
    }
}