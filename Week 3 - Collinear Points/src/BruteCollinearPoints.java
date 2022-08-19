import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {

    // Global Variables
    private int numberOfSegments;
    private Point[] points;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (!validPoints(points)) throw new IllegalArgumentException();
        this.points = points;
        numberOfSegments = 0;

        // Didn't check that input is >= 4; It might blow
        for (int i = 0; i < points.length; i++) {
            Point pointI = points[i];
//            StdOut.println("I am loop i: " + i); // TODO: remove it
            for (int j = i + 1; j < points.length; j++) {
                Point pointJ = points[j];
//                StdOut.println("I am loop j: " + j); // TODO: remove it
                for (int k = j + 1; k < points.length; k++) {
                    Point pointK = points[k];
//                    StdOut.println("I am loop k: " + k); // TODO: remove it
//                    StdOut.println("pointI.slopeTo(pointJ) == pointJ.slopeTo(pointK): " + (pointI.slopeTo(pointJ) == pointJ.slopeTo(pointK))); // TODO: remove it
                    if (pointI.slopeTo(pointJ) == pointJ.slopeTo(pointK)) { //Two lines, same slope
                        for (int l = k + 1; l < points.length; l++) {
//                            StdOut.println("I am loop l: " + l); // TODO: remove it
                            Point pointL = points[l];
//                            StdOut.println("pointK.slopeTo(pointL) == pointJ.slopeTo(pointL)" + (pointK.slopeTo(pointL) == pointJ.slopeTo(pointL))); // TODO: remove it
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
        LineSegment[] tempLines = lineSegments == null ? new LineSegment[1] :
                new LineSegment[numberOfSegments + 1]; // Initializing Array

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

    private boolean validPoints(Point[] points) {
        // Return false if array is null
        if (points == null) {
            return false;
        }

        // Return false if a point is null
        for (Point p : points) {
            if (p == null) return false;
        }

        // Return false if a point is repeated (Algo: Insertion sort)
        for (int i = 0; i < points.length; i++) {
            Point tempPoint = points[i];
            for (int j = i + 1; j < points.length; j++) {
                if (tempPoint.compareTo(points[j]) == 0) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        StdOut.println("###############RandomizedQueue Tests###############");

        StdOut.println("##########My Own Test Cases##########");

        StdOut.println("####Test 1####");


        StdOut.println("####Initializing a correct Input####");
        // Initializing with points (1,1) till (4,4)
        int n = 4;
        Point[] points1 = new Point[n];
        for (int i = 0; i < n; i++) {
            points1[i] = new Point(i, i);
        }

        //Testing if the line segment worked
        BruteCollinearPoints br = new BruteCollinearPoints(points1);
        assert (br.numberOfSegments() == 1): "Constructor Failed to connect points";


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
        points1[2] = new Point(1,1);
        try {
            br = new BruteCollinearPoints(points1);
        } catch (IllegalArgumentException e) {
            StdOut.println("Repeated points exception test succeeded");
        }
        StdOut.println("####End of Error Test Cases####");
    }
}