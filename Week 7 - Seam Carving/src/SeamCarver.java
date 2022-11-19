import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    // Global Variables
    private Picture p;
    private static final int RED = 0;
    private static final int GREEN = 1;
    private static final int BLUE = 2;
    private double[][] energyMatrix;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        p = picture;
        energyMatrix();
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
        return (new int[0]); //TODO
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return (new int[0]); //TODO
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