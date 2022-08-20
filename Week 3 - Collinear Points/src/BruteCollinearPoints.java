import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays; // TODO: remove it

public class BruteCollinearPoints {

    // Global Variables
    private int numberOfSegments;
    private Point[] points;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        StdOut.println("Constructor received Point[]: " + Arrays.toString(points)); // TODO: remove it
        if (invalidPoints(points)) throw new IllegalArgumentException();
        this.points = points;
        numberOfSegments = 0;

        // Didn't check that input is >= 4; It might blow
        for (int i = 0; i < points.length; i++) {
            Point pointI = points[i];
            for (int j = i + 1; j < points.length; j++) {
                Point pointJ = points[j];
                for (int k = j + 1; k < points.length; k++) {
                    Point pointK = points[k];
                    if (pointI.slopeTo(pointJ) == pointJ.slopeTo(pointK)) { //Two lines, same slope
                        for (int l = k + 1; l < points.length; l++) {
                            Point pointL = points[l];
                            if (pointK.slopeTo(pointL) == pointJ.slopeTo(pointL)) {
                                if (lineSegments == null || lineSegments.length <= numberOfSegments + 1) {
                                    cloneArray();
                                }
                                lineSegments[numberOfSegments++] = new LineSegment(pointI, pointL);
                            }
                        }
                    }
                }
            }
        }
    }

    private void cloneArray() {
        LineSegment[] tempLines = lineSegments == null ?
                new LineSegment[1] : new LineSegment[numberOfSegments + 1];

        for (int i = 0; i < numberOfSegments; i++) {
            tempLines[i] = lineSegments[i];
        }
        lineSegments = tempLines;
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }

    private boolean invalidPoints(Point[] points) {
        // Return true if array is null
        StdOut.println("I received Point[]: " + Arrays.toString(points));
        if (points == null) {
            StdOut.println("Invalid points because it is null; Points[]:" + Arrays.toString(points)); // TODO: remove it
            return true;
        }

        // Return true if a point is null
        for (Point p : points) {
//            StdOut.println("p is:" + p + " & (p == " +
//                    "null): " + (p == null)); // TODO: remove it
            if (p == null) {
                StdOut.println("I will return true from single point in points"); // TODO: remove it
                return true;
            }
        }

        // Return true if a point is repeated (Algo: Insertion sort)
        for (int i = 0; i < points.length; i++) {
            Point tempPoint = points[i];
            for (int j = i + 1; j < points.length; j++) {
                if (tempPoint.compareTo(points[j]) == 0) {
                    StdOut.println("Invalid points because repeated; points[" + i + "]: " + tempPoint +
                            "; points[" + j + "]: " + points[j]); // TODO: remove it
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        StdOut.println("###############RandomizedQueue Tests###############");

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
        BruteCollinearPoints br = new BruteCollinearPoints(points1);
        assert (br.numberOfSegments() == 1) : "Constructor Failed to connect points";


        StdOut.println("####End of correct Input Test####");

        StdOut.println();

        StdOut.println("####Error Test Cases####");
        // Trying to put a null input to constructor
        try {
            br = new BruteCollinearPoints(null);
        } catch (IllegalArgumentException e) {
            StdOut.println("Null Input exception test succeeded");
        }

        // Trying to put an array that has a null point
        Point[] points2 = new Point[2];
        points2[0] = points1[0];
        try {
            br = new BruteCollinearPoints(points2);
        } catch (IllegalArgumentException e) {
            StdOut.println("Null point input exception test succeeded");
        }

        //Trying to put a repeated point
        points1[2] = new Point(1, 1);
        try {
            br = new BruteCollinearPoints(points1);
        } catch (IllegalArgumentException e) {
            StdOut.println("Repeated points exception test succeeded");
        }
        StdOut.println("####End of Error Test Cases####");

        StdOut.println();


        StdOut.println("####Assignment Instructor Testing method####"); // TODO: remove it
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        StdOut.println("main() read in.readInt(): " + n); // TODO: remove it
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        StdOut.println("main() created points: " + Arrays.toString(points)); // TODO: remove it
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        StdOut.println("collinear.numberOfSegments(): " + collinear.numberOfSegments()); // TODO: remove it
        // Doomed to throw nullPExc if no segms. Didn't solve it because it is a supplied code.
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment); // TODO: remove it
            segment.draw();
        }
        StdDraw.show();
    }
}