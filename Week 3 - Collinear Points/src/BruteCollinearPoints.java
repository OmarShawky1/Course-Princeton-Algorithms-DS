public class BruteCollinearPoints {

    // Global Variables
    int numberOfSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (!validPoints(points)) throw new IllegalArgumentException("Called constructor with " +
                "either a null array, null point or a repeated point");
        numberOfSegments = 0;
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        // TODO
        return new LineSegment[0];
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
                if (tempPoint == points[j]) return false;
            }
        }

        /* Decision List
         1. First sort them stably via Top-bottom Merge; This will cost ~NlgN (Better than N^2/2
         in selection sort)
            a. That is correct, but then you will need to visit the entire array again which costs N
         2. He does not want me to implement mergesort as per colleagues
        */
        /* Code Implementation of old validation
        // First, Sort (mergesort)
        // !!
        sort(points, new Point[points.length], 0, points.length - 1);

        // Second search in merged result
        // !!
         */
        return true;
    }

    /*
    private static void sort(Point[] points, Point[] aux, int lo, int hi) {
        if (lo >= hi) return;
        int mid = (hi - lo) / 2;
        sort(points, aux, lo, mid);
        sort(points, aux, mid + 1, hi);
        merge(points, aux, lo, mid, hi);
    }

    private static void merge(Point[] points, Point[] aux, int lo, int mid, int hi) {
        // Merge should not occur for an unsorted arrays
        assert isSorted(points, lo, hi / 2);
        assert isSorted(points, hi / 2 + 1, hi);

        for (int k = lo; k < hi; k++) aux[k] = points[k]; // Copying Array

        // int i, j = 0; // Might cause checkstyle error, to be checked later
        int i = 0; // Pointer to the right half of the array
        int j = 0; // Pointer to the left half of the array
        for (int k = 0; k < hi; k++) { // Merging two halves of aux into points array
            if (i >= mid) points[k] = aux[j++]; // Append remaining right half as left is empty
            else if (j >= hi) points[k] = aux[i++]; // Append remaining left half as right is empty
            else if (less(aux[i], aux[j])) points[k] = aux[i++];
            else points[k] = aux[j++];
        }
        // Checking that sorting works
        assert isSorted(points, lo, hi);
    }

    private static boolean less(Point aux, Point aux1) {
        // !!
        return true;
    }

    private static boolean isSorted(Point[] points, int lo, int hi) {
        // !!
        return true;
    }
     */

    public static void main(String[] args) {

    }
}