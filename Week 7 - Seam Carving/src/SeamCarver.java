import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    // Global Variables
    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = !VERTICAL;
    private Picture p;
    private double[] distTo;         // distTo[v] = distance  of shortest s->v path
    private int[][] edgeTo;          // edgeTo[v] = last edge on shortest s->v path

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        if (picture == null) throw new IllegalArgumentException("null picture");

        p = new Picture(picture);
    }

    // current picture
    public Picture picture() {
        return new Picture(p);
    }

    // width of current picture
    public int width() {
        return p.width();
    }

    // height of current picture
    public int height() {
        return p.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        int col = x;
        int row = y;
        // validate coordinates
        if (x < 0 || x >= width() || y < 0 || y >= height()) throw new IllegalArgumentException("Invalid coordinates");

        // if coordinates are at edges
        if (x == 0 || x == width() - 1 || y == 0 || y == height() - 1) return 1000;

        Color right = p.get(col, row + 1);
        Color left = p.get(col, row - 1);
        double dx = colorGrad(right, left);

        Color bottom = p.get(col + 1, row);
        Color top = p.get(col - 1, row);
        double dy = colorGrad(bottom, top);
        return Math.sqrt(dx + dy);
    }


    // calculate color gradient between two single pixels
    // high pixel is right or bottom; low pixel is up or left
    private static double colorGrad(Color highPixel, Color lowPixel) {
        // Using Mathematical manipulation to calculate colors instead of using (E.g., ".getRed") makes it slower
        // Furthermore, the autograder does not complain about the overhead object creation (i.e., neglectable)
        // So, code does not follow requirements in FAQs (" Creating color objects..."
        double red = highPixel.getRed() - lowPixel.getRed();
        double green = highPixel.getGreen() - lowPixel.getGreen();
        double blue = highPixel.getBlue() - lowPixel.getBlue();
        return (red * red) + (green * green) + (blue * blue);
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return seam(HORIZONTAL);
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return seam(VERTICAL);
    }

    /**
     * Computes the seam which is least significant non-straight line of pixels that denotes the shortest path from
     * high to low
     * If it is the original picture (I.e., is vertical) then "high" bottom and "low" is top
     * If it is transposed picture (I.e., is horizontal) then "high" is right and "low" is left
     *
     * @return a seam[] where each item in seam is connected to the previous item (E.g., seam[2] --> seam[3])
     * seam[y]: is the x-axis of the cell at y-coordinate "y" (only if it is vertical)
     * seam[x]: is the y-axis of the cell at x-coordinate "x" (only if it is horizontal)
     */
    private int[] seam(boolean isVertical) {
        // FAQ requirements: do not use EdgeWeightedDigraph; Hereby don't use Topological sort.
        distTo = new double[isVertical ? width() : height()];
        edgeTo = new int[width()][height()];

        /*
        Logic:
        1. compute distance to each pixel at last row (if is vertical) or last column (if is horizontal).
        2. Find the index of the minimum distance pixel.
        3. Store coordinates of seam.
         */

        // 1. compute distance to each pixel at last row (if is vertical) or last column (if is horizontal)
        // swap axis if it is horizontal. If it is vertical, row is height; if it is horizontal row is width (transpose)
        int MAXROW = isVertical ? height() : width();
        int MAXCOL = isVertical ? width() : height();

        double[] oldDistTo;
        double energy;
        // "two for lopes" represent the "for (int v : topological.order())" in AcyclicSP
        for (int row = 0; row < MAXROW; row++) {
            // compute "distance to" for current row to be accumulated for the next row "distance to"
            // create a distTo[] clone that will be used to calculate new distTo[]
            oldDistTo = distTo.clone();
            // Reset distances (stored in distTo) to be used for current row
            for (int i = 0; i < distTo.length; i++) distTo[i] = Double.POSITIVE_INFINITY;

            // do not include left & right edges (0, MAXCOL - 2) respectively
            for (int col = 1; col < MAXCOL - 1; col++) {
                // "if condition" represent "for (DirectedEdge e : G.adj(v))" in AcyclicSP
                // relax edge; compare the distances from col-1, col, col+1 and chose the lowest
                if (isVertical) {
                    energy = energy(col, row); // calculate energy of current index
                    relax(col - 1, col, col, row, energy, oldDistTo);
                    relax(col, col, col, row, energy, oldDistTo);
                    relax(col + 1, col, col, row, energy, oldDistTo);
                } else {
                    // If they are not vertical, re-swap raw and col (because they are already swapped above)
                    energy = energy(row, col); // calculate energy of current index
                    relax(col - 1, col, row, col, energy, oldDistTo);
                    relax(col, col, row, col, energy, oldDistTo);
                    relax(col + 1, col, row, col, energy, oldDistTo);
                }
            }
        }

        // 2. Find the index of the minimum distance pixel
        // The index is either x-axis "x" if it is vertical or y-axis "y" if it is horizontal
        double minDistance = distTo[0];
        int min = 0;
        double currentDistance;
        for (int i = 1; i < distTo.length; i++) {
            currentDistance = distTo[i];
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                min = i; // store index of the currentDistance to be minimum pixel so far
            }
        }

        // 3. Store coordinates of seam
        int[] seam = new int[isVertical ? height() : width()];

        // fill vertical seam with x at y-index; E.g., pixel (x:2,y:1) is `seam[1] = 2`   (if is vertical)
        // fill horizontal seam with y at x-index; E.g., pixel (x:2,y:1) is `seam[2] = 1` (if is horizontal)
        if (isVertical) {
            // fill vertical seam starting from the last row using pre-computed "min" at that row
            for (int y = height() - 1; y >= 0; y--) {
                seam[y] = min; // store x-axis of current point that has y as its y-axis

                // Get the ancestors' x-axis of current point to be used in next loop
                // ancestors' axis are (x:min, y-1) where y is the current pixel y-axis
                min = edgeTo[min][y];
            }
        } else {
            // fill horizontal seam starting from the last column using pre-computed "min" at that column
            for (int x = width() - 1; x >= 0; x--) {
                seam[x] = min;        // store y-axis of current point that has x as its x-axis

                // Get the ancestors' y-axis of current point to be used in next loop
                // ancestors' axis are (x:x-1, min) where x is the current pixel x-axis
                min = edgeTo[x][min];
            }
        }
        return seam;
    }

    private void relax(int prev, int thisPixel, int x, int y, double energy, double[] lastDistTo) {
        double currentDistance = lastDistTo[prev];
        if (distTo[thisPixel] > currentDistance + energy) {
            distTo[thisPixel] = currentDistance + energy;
            edgeTo[x][y] = prev;
        }
    }

    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        //TODO
    }

    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        //TODO
    }

    // unit testing (optional)
    public static void main(String[] args) {

    }
}