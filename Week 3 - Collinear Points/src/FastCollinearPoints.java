 import edu.princeton.cs.algs4.In;
 import edu.princeton.cs.algs4.StdDraw;
 import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class FastCollinearPoints {

    // Global Variables
    private final int numberOfSegments;
    private final LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] pts) {
        if (invalidPoints(pts)) {
            throw new IllegalArgumentException();
        }
        Point[] points = pts.clone(); // Cloning to refrain from being mutable (spotbugs)
        int numbOfSegments = 0;
        // Instead of resizing, maximum segments count is length^2 as each point can create a
        // whole new line with the remaining other points
        lineSegments = points.length >= 4
                ? new LineSegment[points.length * points.length] : new LineSegment[0];

        // Sorting by smallest to find repetition instead of insertion sort.
        Arrays.sort(points);
        if (repeatedPoints(points)) {
            throw new IllegalArgumentException();
        }

        // Iterate over points itself from smallest to largest point
        // Don't need to iterate over the last 4 index because we are sure that they are added
        // Furthermore, they cause errors as point can't verify turning point if it is the last
        for (int i = 0; i < points.length - 3; i++) {
            Point origin = points[i];
            StdOut.println("origin is " + origin + " at index: " + i); // TODO: remove line

            // Detect collinear points
            /*
            Technique:
                1. Sort the Array by slope in pointsClone.
                2. Begin a for loop
                3. Try to detect 3 consecutive points having same slope.
                4. If found, check if they are points of an existing/added slope.
                    1) Detect a point smaller than origin (origin.compareTo(p) < 0).
                    2) & Detect a point bigger than origin.
                    3) If both found, then seg is already added; Therefore set isRefusedSlope = true
                    4) Detect if origin is at end of segment; If true, don't add segment (its added)
                5. Store them in collPoints; Store their slope in collSlope.
                6. Check if there is more points having the same slope as collSlope; Add if found.
                7. If not found, check if seg is accepted (!isRefusedSlope) to add collPoints to
                   lineSegment[numSegments++]
                8. Erase collSlope & collPoints
             */

            // 1. Sort the Array by slope in pointsClone.
            Point[] pointsClone = points.clone();
            StdOut.println("pointsClone Before sorting: " + Arrays.toString(pointsClone)); //TODO remove Line
            // Sort points w.r.t slope with origin where origin is each individual point in input
            Arrays.sort(pointsClone, origin.slopeOrder());
            StdOut.println("pointsClone After sorting: " + Arrays.toString(pointsClone)); //TODO remove

            // 4. If found, check if they are points of an existing/added slope.
            // Detect turning point (from small to big) (to detect repeated segments))
            boolean thereIsBiggerP = false;
            boolean thereIsSmallerP = false;
            boolean isRefusedSlope = false;

            // 5. Store them in collPoints; Store their slope in collSlope.
            // Create a list that will remember the last collinear points
            LinkedList<Point> collPoints = new LinkedList<>();
            double collSlope = Double.NaN;

            // 2. Begin a for loop
            // Start from 1 because 0 is origin
            for (int j = 1; j < pointsClone.length - 2; j++) {
                Point p1 = pointsClone[j];
                Point p2 = pointsClone[j + 1];
                Point p3 = pointsClone[j + 2];

                // Calculate slope from smallP to bigP.
                // Don't multiply by -1 because this is duck typing (double isn't always a number)
                // To detect number smallP or bigP, either origin.compare(p) or i < <jCurrent>

                double slope1 = origin.compareTo(p1) < 0 ? p1.slopeTo(origin) : origin.slopeTo(p1);
                double slope2 = origin.compareTo(p2) < 0 ? p2.slopeTo(origin) : origin.slopeTo(p2);
                double slope3 = origin.compareTo(p3) < 0 ? p3.slopeTo(origin) : origin.slopeTo(p3);
                StdOut.println("I am in j: " + j + " & slope1: " + slope1); // TODO: remove line

                // If there is already existing 3 collinear points from a previous iteration
                if (!Double.isNaN(collSlope)) {

                    // 6. Check if there is more points having the same slope as collSlope; Add if
                    // found.
                    // check if this point is also on same line
                    if (collSlope == slope3) {

                        // Detect turning point in the current collinear points
                        if (origin.compareTo(p3) < 0) {
                            thereIsBiggerP = true;

                            StdOut.println("smallerP = true due to P3: " + p3); // TODO: remove line
                        } else {
                            thereIsSmallerP = true;
                            StdOut.println("biggerP = true due to P3: " + p3); // TODO: remove line
                        }

                        collPoints.add(p3);
                        StdOut.println("collpoints stored " + collPoints.size() + " points which are: " + collPoints); // TODO: remove line
                    } else {
                        // 5. Store them in collPoints; Store their slope in collSlope.
                        // Else, add collPoints to lineSegm[numsegm++]; Flush collPoints & collSlope
                        StdOut.println("I will add " + collPoints.size() + " to Linesegment which are: " + collPoints); // TODO: remove line

                        // Adding
                        isRefusedSlope = thereIsSmallerP && thereIsBiggerP;
                        StdOut.println("Before Adding: lineSegments.length: " + lineSegments.length + " ; numberOfSegments: " + numbOfSegments + " ; pointsClone.length: " + pointsClone.length);

                        // 7. If not found, check if seg is accepted (!isRefusedSlope) to add
                        // collPoints to lineSegment[numSegments++]
                        // Add current lineSegment to lineSegments only if !isRefusedSlope
                        if (!isRefusedSlope) {
                            StdOut.println("collPoints before sorting: " + collPoints); // TODO: remove line
                            Collections.sort(collPoints); // Sort before taking first & last Points

                            // 4) Detect if origin is at end of segment; If true, don't add segment
                            // (its added)
                            if (!(collPoints.peekLast().compareTo(origin) == 0)) {
                                lineSegments[numbOfSegments++] =
                                        new LineSegment(collPoints.peek(), collPoints.peekLast());
                            }
                        }
                        StdOut.println("After Adding: LineSegment: " + Arrays.toString(lineSegments)); // TODO: remove line

                        // 8. Erase collSlope & collPoints
                        // Flushing
                        collPoints = new LinkedList<>();
                        collSlope = Double.NaN;
                        thereIsSmallerP = false;
                        thereIsBiggerP = false;
                        isRefusedSlope = false;
                    }
                } else if (slope1 == slope2 && slope2 == slope3) {
                    // 3. Try to detect 3 consecutive points having same slope.
                    // If not, Check if the current points have same slope to origin.

                    // If yes, check if points are of an existing segment by detecting turning point
                    int compP1 = origin.compareTo(p1);
                    int compP2 = origin.compareTo(p2);
                    int compP3 = origin.compareTo(p3);

                    // TODO: What if they are equal? it will be smaller
                    if (compP1 < 0 || compP2 < 0 || compP3 < 0) {
                        thereIsBiggerP = true;
                        if (compP1 < 0) StdOut.println("biggerP = true due to P1: " + p1); // TODO: remove line
                        if (compP2 < 0) StdOut.println("biggerP = true due to P2: " + p2); // TODO: remove line
                        if (compP3 < 0) StdOut.println("biggerP = true due to P3: " + p3); // TODO: remove line
                    }

                    if (compP1 > 0 || compP2 > 0 || compP3 > 0) {
                        thereIsSmallerP = true;
                        if (compP1 > 0) StdOut.println("smallerP = true due to P1: " + p1); // TODO: remove line
                        if (compP2 > 0) StdOut.println("smallerP = true due to P2: " + p2); // TODO: remove line
                        if (compP3 > 0) StdOut.println("smallerP = true due to P3: " + p3); // TODO: remove line
                    }

                    // then add them to CollPoints
                    collPoints.add(origin);
                    collPoints.add(p1);
                    collPoints.add(p2);
                    collPoints.add(p3);
                    collSlope = slope3;
                    StdOut.println("collPoints stored 4 points which are: " + collPoints); // TODO: remove line
                }
            }
            // This is repeated from loop j
            StdOut.println("Outside j Loop but in loop i: " + i);

            // 5. Store them in collPoints; Store their slope in collSlope.
            // Else, add collPoints to lineSegm[numsegm++]; Flush collPoints & collSlope
            StdOut.println("I will add " + collPoints.size() + " to Linesegment which are: " + collPoints); // TODO: remove line

            // Adding
            isRefusedSlope = thereIsSmallerP && thereIsBiggerP;
            StdOut.println("Before Adding: lineSegments.length: " + lineSegments.length + " ; numberOfSegments: " + numbOfSegments + " ; pointsClone.length: " + pointsClone.length);

            // 7. If not found, check if seg is accepted (!isRefusedSlope) to add
            // collPoints to lineSegment[numSegments++]
            // Add current lineSegment to lineSegments only if !isRefusedSlope
            if (!isRefusedSlope && !Double.isNaN(collSlope)) {
                StdOut.println("collPoints before sorting: " + collPoints); // TODO: remove line
                Collections.sort(collPoints); // Sort before taking first & last Points

                // 4) Detect if origin is at end of segment; If true, don't add segment
                // (its added)
                if (!(collPoints.peekLast().compareTo(origin) == 0)) {
                    lineSegments[numbOfSegments++] =
                            new LineSegment(collPoints.peek(), collPoints.peekLast());
                }
            }
            StdOut.println("After Adding: LineSegment: " + Arrays.toString(lineSegments)); // TODO: remove line

            /*
            // 8. Erase collSlope & collPoints
            // Flushing (Although there is no need as the program finished and won't use them)
            collPoints = new LinkedList<>();
            collSlope = Double.NaN;
            thereIsSmallerP = false;
            thereIsBiggerP = false;
            isRefusedSlope = false;
            */
        }
        this.numberOfSegments = numbOfSegments; // Autograder's request (finalize numberOfSegments)
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

        for (Point point : points) {
            if (point == null) {
                return true;
            }
        }

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
        int num = 5;
        Point[] points1 = new Point[num];
        for (int i = 0; i < num; i++) {
            points1[i] = new Point(i, i);
        }

        // Testing if the line segment worked
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

        // Trying to put a repeated point
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