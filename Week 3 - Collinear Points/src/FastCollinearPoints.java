import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class FastCollinearPoints {

    // Global Variables
    private int numberOfSegments;
    private Point[] points;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] pts) {
        if (invalidPoints(pts)) throw new IllegalArgumentException();
        this.points = pts.clone(); // Cloning to refrain from being mutable (spotbugs)
        numberOfSegments = 0;
        // Instead of resizing, maximum segments count is length/4 assuming all points make segment
        lineSegments = points.length >= 4 ? new LineSegment[points.length / 4] : new LineSegment[0];
//        lineSegments = points.length >= 4 ? new LineSegment[points.length * points.length] : new LineSegment[0];


        Arrays.sort(points);
        if (repeatedPoints(points)) throw new IllegalArgumentException();

        // Sorting by smallest to find repetition instead of insertion sort.
        // Sort points w.r.t the slope with origin where origin is each individual point in input
        // for (Point point : points) Arrays.sort(points, point.slopeOrder());

        // Create a list that will remember the last collinear points
        LinkedList<Point> collPoints = new LinkedList<>();
        double collSlope = Double.NaN;

        // Iterate over points itself from smallest to largest point
        for (int i = 0; i < points.length; i++) {
            Point origin = points[i]; //AKA origin in HW

            // Detect collinear points
            /*
            Technique:
                1. Sort the Array by slope in pointsClone.
                2. Begin a for loop
                3. Try to detect 3 consecutive points having same slope.
                3. If found, Store them in collPoints; Store their slope in collSlope.
                4. Check if there is more points having the same slope as collSlope; Add if found.
                5. If not found, Add collPoints to lineSegm[numsegm++]; Erase collSlope & collPoints
             */

            Point[] pointsClone = points.clone();
            Arrays.sort(pointsClone, origin.slopeOrder());

            for (int j = 0; j < points.length - 2; j++) {
                Point p1 = points[j];
                Point p2 = points[j + 1];
                Point p3 = points[j + 2];
                double tempSlope = origin.slopeTo(p3);

                // If there is already 3 collinear points
                if (!Double.isNaN(collSlope)) {
                    // check if this point is also on same line
                    if (collSlope == tempSlope) {
                        collPoints.add(p3);
                    }
                    // Else, add collPoints to lineSegments[numsegm++]; Flush collPoints & collSlope
                    else {
                        // Adding
                        StdOut.println("lineSegments.length: " + lineSegments.length +
                                " ; numberOfSegments: " + numberOfSegments + " ; points.length: " + points.length);
                        lineSegments[numberOfSegments++] =
                                new LineSegment(origin, collPoints.peekLast());
                        // Flushing
                        collPoints = new LinkedList<>();
                        collSlope = Double.NaN;
                    }
                }
                // If not, Check if the current points have same slope to origin.
                else if ((tempSlope == origin.slopeTo(p2)) && (tempSlope == origin.slopeTo(p3))) {
                    // Yes, then add them to CollPoints
                    collPoints.add(p1);
                    collPoints.add(p2);
                    collPoints.add(p3);
                    collSlope = tempSlope;
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments; // Passing by reference, should've created local int but not imp.
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] temp = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            temp[i] = lineSegments[i];
        }
        return temp;
    }

    private boolean invalidPoints(Point[] points) {
        // Return true if array is null
        if (points == null) return true;

        for (Point point : points) if (point == null) return true;

        return false;
    }

    private boolean repeatedPoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++)
            if (points[i].compareTo(points[i + 1]) == 0) return true;
        return false;
    }


    public static void main(String[] args) {

        StdOut.println("###############FastCollinearPoints Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");


        StdOut.println("####Initializing a correct Input####");
        // Initializing with points (1,1) till (4,4)
        int num = 4;
        Point[] points1 = new Point[num];
        for (int i = 0; i < num; i++) {
            points1[i] = new Point(i, i);
        }

        //Testing if the line segment worked
        FastCollinearPoints fcp = new FastCollinearPoints(points1);
        assert (fcp.numberOfSegments() == 1) :
                "Constructor Failed to connect points; it should've returned " +
                        "fcp.numberOfSegments(): 1 but it returned " + fcp.numberOfSegments();


        StdOut.println("####End of correct Input Test####");

        StdOut.println();

        StdOut.println("####Error Test Cases####");
        // Trying to put a null input to constructor
        try {
            new FastCollinearPoints(null);
        } catch (IllegalArgumentException e) {
            StdOut.println("Null Input exception test succeeded");
        }

        // Trying to put an array that has a null point
        Point[] points2 = new Point[2];
        points2[0] = points1[0];
        try {
            new FastCollinearPoints(points2);
        } catch (IllegalArgumentException e) {
            StdOut.println("Null point input exception test succeeded");
        }

        //Trying to put a repeated point
        points1[2] = new Point(1, 1);
        try {
            new FastCollinearPoints(points1);
        } catch (IllegalArgumentException e) {
            StdOut.println("Repeated points exception test succeeded");
        }
        StdOut.println("####End of Error Test Cases####");

        StdOut.println();


        StdOut.println("####Assignment Instructor Testing method####");
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        StdOut.println("I'm Done");
        /**/
    }
}