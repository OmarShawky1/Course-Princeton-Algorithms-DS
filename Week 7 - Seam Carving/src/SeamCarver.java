import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {

    // Global Variables
    private final Picture p;
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
        return p;
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
        return energyMatrix[y][x];
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        return (new int[0]); //TODO
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        return (new int[0]); //TODO
    }

    // calculate Energy Matrix
    private void energyMatrix() {
        double dx, dy;
        energyMatrix = new double[p.height()][p.width()];

        // width is column is x; height is row is y
        for (int row = 0; row < p.height(); row++) {
            energyMatrix[row][0] = 1000;
            energyMatrix[row][p.width() - 1] = 1000;
        }

        for (int col = 1; col < p.width() - 1; col++) {
            // most left pixel
            energyMatrix[0][col] = 1000;
            for (int row = 1; row < p.height() - 1; row++) {
                // compute energy of pixel
                Color rightPixel = p.get(col, row + 1);
                Color leftPixel = p.get(col, row - 1);
                dx = colorGrad(rightPixel, leftPixel);

                Color lowerPixel = p.get(col + 1, row);
                Color upperPixel = p.get(col - 1, row);
                dy = colorGrad(lowerPixel, upperPixel);

                energyMatrix[row][col] = Math.sqrt(dx + dy);
            }
            // most left pixel
            energyMatrix[p.height() - 1][col] = 1000;
        }
    }

    // calculate color gradient between two single pixels
    private static double colorGrad(Color highPixel, Color lowPixel) {
        double red = highPixel.getRed() - lowPixel.getRed();
        double green = highPixel.getGreen() - lowPixel.getGreen();
        double blue = highPixel.getBlue() - lowPixel.getBlue();
        return (red * red) + (green * green) + (blue * blue);
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