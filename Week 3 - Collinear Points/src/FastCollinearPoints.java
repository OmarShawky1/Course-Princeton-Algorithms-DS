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
    public FastCollinearPoints(Point[] points) {
        if (invalidPoints(points)) throw new IllegalArgumentException();
        this.points = points.clone(); // Cloning to refrain from being mutable (spotbugs)
        numberOfSegments = 0;
        // Instead of resizing, maximum segments count is length/4 assuming all points make segment
//        lineSegments = new LineSegment[points.length / 4];
        lineSegments = points.length >= 4 ? new LineSegment[points.length / 4] : new LineSegment[0];

        /*
        TODO:
            1. Read the requirements in the assignment (Forgot to read them carefully).
            1. Sort by smallest numbers & then sort by slope (stably).
            2. The slope sort should be done per each entry for input "points" (not like what i did)
         */

        Arrays.sort(points);
        if (repeatedPoints(points)) throw new IllegalArgumentException();

        /*
        TODO: Fix following problems
            1. Below line throws:
             Exception in thread "main" java.lang.IllegalArgumentException: Comparison method violates its general contract!
         */
        Arrays.sort(points, points[0].slopeOrder());

        // Create a list that will remember the last collinear points
        LinkedList<Point> collPoints = new LinkedList<>();

        // Start from index 4 in order to escape checking Array boundaries
        for (int i = 3; i < points.length; i++) {
            StdOut.println("In constructor at loop " + i); // TODO: remove line

            // Store the last 4 consecutive points that will be slope compared
            Point point1 = points[i - 3];
            Point point2 = points[i - 2];
            Point point3 = points[i - 1];
            Point point4 = points[i];

            Double tempCompare = point1.slopeTo(point2); // To shorten the below line
            StdOut.println("collPoints: " + collPoints); // TODO: remove line
            StdOut.println("point1.slopeTo(point2): " + tempCompare); // TODO: remove line
            StdOut.println("point2.slopeTo(point3): " + point2.slopeTo(point3)); // TODO: remove line
            StdOut.println("point3.slopeTo(point4): " + point3.slopeTo(point4)); // TODO: remove line
            // Check if we have 4 consecutive collinear points, if not, create line segment from
            // the last remembered collinear points (if any)
            if (tempCompare == point2.slopeTo(point3) && tempCompare == point3.slopeTo(point4)) {
                StdOut.println("Found 4 collinear points"); // TODO: remove line
                // If there is already collinear points, then add current only.
                StdOut.println("collPoints.size(): " + collPoints.size()); // TODO: remove line
                if (collPoints.size() >= 4) { // Add the fifth point to the old 4 points
                    collPoints.add(point4);
                } else { // Else add the 4 consecutive collinear points
                    collPoints.add(point1);
                    collPoints.add(point2);
                    collPoints.add(point3);
                    collPoints.add(point4);
                    StdOut.println("collPoints after adding: " + collPoints); // TODO: remove line
                }
            } else if (collPoints.size() >= 4) {
                lineSegments[numberOfSegments++]
                        = new LineSegment(collPoints.get(0), collPoints.get(collPoints.size() - 1));
                StdOut.println("1) lineSegments: " + Arrays.toString(lineSegments)); // TODO: remove
                // line
                collPoints = new LinkedList<>();
                StdOut.println("1) collPoints after cleaning: " + collPoints); // TODO: remove line
            } // otherwise skip to next i
        }
        if (collPoints.size() >= 4) {
            lineSegments[numberOfSegments++]
                    = new LineSegment(collPoints.get(0), collPoints.get(collPoints.size() - 1));
            StdOut.println("2) lineSegments: " + Arrays.toString(lineSegments)); // TODO: remove
            // line
            collPoints = new LinkedList<>();
            StdOut.println("2) collPoints after cleaning: " + collPoints); // TODO: remove line
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
        if (points == null) {
            return true;
        }

        // TODO: To be removed, This is an old double for loop (O(n) ~~ N^2) but left just in case.
        /*for (int i = 0; i < points.length; i++) {
            Point pointI = points[i];

            // Return true if a point is null
            if (pointI == null) {
                return true;
            } else {
                // Return true if a point is repeated (Algo: Insertion sort)
                for (int j = i + 1; j < points.length; j++) {
                    Point pointJ = points[j];
                    if (pointJ == null || pointI.compareTo(pointJ) == 0) {
                        return true;
                    }
                }
            }
        }*/

        for (Point point : points) if (point == null) return true;
        return false;
    }

    private boolean repeatedPoints(Point[] points) {
        for (int i = 1; i < points.length; i++)
            if (points[i - 1].compareTo(points[i]) == 0) return true;
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
        assert (fcp.numberOfSegments() == 1) : "Constructor Failed to connect points";


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
    }
}