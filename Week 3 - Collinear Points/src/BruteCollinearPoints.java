public class BruteCollinearPoints {

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (!validPoints(points)) throw new IllegalArgumentException("Called constructor with " +
                "either a null array, null point or a repeated point");
    }

    private boolean validPoints(Point[] points) {
        // Return false if array is null
        if (points == null) {
            return false;
        }

        // Return false if a point is null
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) return false;
        }

        // Return false if a point is repeated.
        // Decision List:
        /*
         1. First sort them stably via Top-bottom Merge; This will cost ~NlgN (Better than N^2/2
         in selection sort)
            a. That is correct but then you will need to visit the entire array again which costs N
        */
        // First, Sort (mergesort)
        // TODO!!
        sort(points, new Point[points.length], 0, points.length - 1);

        // Second search in merged result
        // TODO!!

        return true;
    }

    private static void sort(Point[] points, Point[] aux, int lo, int hi) {
        if (lo >= hi) return;
        sort(points, aux, lo, hi / 2);
        sort(points, aux, hi / 2 + 1, hi);
        merge(points, aux, lo, (hi - 1) / 2, hi);
    }

    private static void merge(Point[] points, Point[] aux, int lo, int mid, int hi) {
        assert isSorted(points, lo, hi / 2);
        assert isSorted(points, hi / 2 + 1, hi);

        for (int i = 0; i < points.length; i++) aux[i] = points[i]; // Copying Array

        // int i, j = 0; // Might cause checkstyle error, to be checked later
        int i = 0;
        int j = 0;
        for (int k = 0; k < points.length; k++) { // Merging two halves of aux into points
            if (i >= mid) points[k] = aux[j++]; // Append remaining right half cuz left is empty
            else if (j >= hi) points[k] = aux[i++]; // Append remaining left half cuz right is empty
            else if (less(aux[i], aux[j])) points[k] = aux[i++];
            else points[k] = aux[j++];
        }

        assert isSorted(points, lo, hi);
    }

    private static boolean less(Point aux, Point aux1) {
        // TODO
        return true;
    }

    private static boolean isSorted(Point[] points, int lo, int hi) {
        // TODO
        return true;
    }

    // the number of line segments
    public int numberOfSegments() {
        // TODO
        return 0;
    }

    // the line segments
    public LineSegment[] segments() {
        // TODO
        return new LineSegment[0];
    }

    public static void main(String[] args) {

    }
}